#include <jni.h>
#include <stdlib.h>
#include <string.h>
#include <wolfssl/wolfcrypt/types.h>
#include <wolfssl/wolfcrypt/aes.h>
#include <wolfssl/wolfcrypt/random.h>
#include <wolfssl/wolfcrypt/ecc.h>
#include <wolfssl/wolfcrypt/error-crypt.h>
#include <wolfssl/wolfcrypt/kdf.h>
#include <wolfssl/wolfcrypt/sha256.h>

// Generate an ECC Key Pair
JNIEXPORT jobject JNICALL
Java_com_benedetto_galoislibrary_Galois_generateKeyPair(JNIEnv *env,
                                                        jobject thiz) {
    ecc_key keyPair;
    WC_RNG rng;

    // Initialize RNG and ECC Key Pair
    wc_InitRng(&rng);
    wc_ecc_init(&keyPair);

    // Generate ECC Key Pair
    if (wc_ecc_make_key(&rng, 32, &keyPair) != 0) {
        return NULL; // Handle error
    }

    // Export Private Key
    byte privateKey[256];
    word32 privateKeyLen = sizeof(privateKey);
    wc_ecc_export_private_only(&keyPair, privateKey, &privateKeyLen);

    // Export Public Key
    byte publicKey[256];
    word32 publicKeyLen = sizeof(publicKey);
    wc_ecc_export_x963(&keyPair, publicKey, &publicKeyLen);
//Java_com_benedetto_galoislibrary_Galois
    // Clean up
    wc_FreeRng(&rng);
    wc_ecc_free(&keyPair);
    // Return the key pair as a Java object (KeyPair)
    jclass keyPairClass = (*env)->FindClass(env, "com/benedetto/galoislibrary/KeyPair");
    jmethodID keyPairConstructor = (*env)->GetMethodID(env, keyPairClass, "<init>", "([B[B)V");

    // Convert privateKey and publicKey to Java byte arrays
    jbyteArray privateKeyArray = (*env)->NewByteArray(env, privateKeyLen);
    (*env)->SetByteArrayRegion(env, privateKeyArray, 0, privateKeyLen, (jbyte *) privateKey);

    jbyteArray publicKeyArray = (*env)->NewByteArray(env, publicKeyLen);
    (*env)->SetByteArrayRegion(env, publicKeyArray, 0, publicKeyLen, (jbyte *) publicKey);

    // Create KeyPair Java object
    return (*env)->NewObject(env, keyPairClass, keyPairConstructor, privateKeyArray,
                             publicKeyArray);
}

//Compute shared secret
JNIEXPORT jbyteArray JNICALL
Java_com_benedetto_galoislibrary_Galois_computeSharedKey(JNIEnv *env,
                                                         jobject thiz,
                                                         jbyteArray publicKey,
                                                         jbyteArray privateKey) {
    ecc_key publicKeyStruct, privateKeyStruct;
    byte sharedSecret[32];
    word32 sharedSecretLen = sizeof(sharedSecret);

    // Initialize keys
    wc_ecc_init(&publicKeyStruct);
    wc_ecc_init(&privateKeyStruct);

    // Convert Java byte arrays to native C arrays
    jbyte *pubKeyBytes = (*env)->GetByteArrayElements(env, publicKey, NULL);
    jsize pubKeyLen = (*env)->GetArrayLength(env, publicKey);
    wc_ecc_import_x963((byte *) pubKeyBytes, pubKeyLen, &publicKeyStruct);

    jbyte *privKeyBytes = (*env)->GetByteArrayElements(env, privateKey, NULL);
    jsize privKeyLen = (*env)->GetArrayLength(env, privateKey);
    wc_ecc_import_private_key((byte *) privKeyBytes, privKeyLen, NULL, 0, &privateKeyStruct);

    // Compute the shared secret
    wc_ecc_shared_secret(&privateKeyStruct, &publicKeyStruct, sharedSecret, &sharedSecretLen);

    // Clean up
    wc_ecc_free(&publicKeyStruct);
    wc_ecc_free(&privateKeyStruct);

    // Return shared secret as a byte array
    jbyteArray sharedSecretArray = (*env)->NewByteArray(env, sharedSecretLen);
    (*env)->SetByteArrayRegion(env, sharedSecretArray, 0, sharedSecretLen, (jbyte *) sharedSecret);
    return sharedSecretArray;
}

//Derive keys from sharedSecret for encryption
JNIEXPORT jbyteArray JNICALL
Java_com_benedetto_galoislibrary_Galois_deriveKey(JNIEnv *env, jobject thiz,
                                                  jbyteArray sharedSecret) {
    // Convert Java byte array to C array
    jsize secretLen = (*env)->GetArrayLength(env, sharedSecret);
    jbyte *secretBytes = (*env)->GetByteArrayElements(env, sharedSecret, NULL);

    // Define salt (optional) and info
    const byte salt[] = {0x00, 0x01, 0x02, 0x03};  // Customize as needed
    const int saltLen = sizeof(salt);
    const byte info[] = {0x04, 0x05, 0x06, 0x07};  // Customize as needed
    const int infoLen = sizeof(info);

    // Buffer for derived key
    byte derivedKey[32];  // 32 bytes for AES-256
    memset(derivedKey, 0, sizeof(derivedKey));

    // Perform HKDF key derivation
    int ret = wc_HKDF(SHA256, salt, saltLen, (byte *) secretBytes, secretLen, info, infoLen,
                      derivedKey, sizeof(derivedKey));

    // Release the shared secret memory
    (*env)->ReleaseByteArrayElements(env, sharedSecret, secretBytes, JNI_ABORT);

    // Handle errors
    if (ret != 0) {
        // Throw a Java exception with the error code
        jclass exceptionClass = (*env)->FindClass(env, "java/lang/RuntimeException");
        if (exceptionClass != NULL) {
            char errorMessage[128];
            snprintf(errorMessage, sizeof(errorMessage), "HKDF failed with error code: %d", ret);
            (*env)->ThrowNew(env, exceptionClass, errorMessage);
        }
        return NULL;
    }

    // Convert derived key to Java byte array
    jbyteArray result = (*env)->NewByteArray(env, sizeof(derivedKey));
    (*env)->SetByteArrayRegion(env, result, 0, sizeof(derivedKey), (const jbyte *) derivedKey);

    return result;
}

// Encrypt data using AES-GCM
JNIEXPORT jbyteArray JNICALL
Java_com_benedetto_galoislibrary_Galois_encrypt(JNIEnv *env, jobject thiz,
                                                jbyteArray iv,
                                                jbyteArray plaintext,
                                                jbyteArray key) {
    // Get the plaintext and key from Java
    jsize plaintextLen = (*env)->GetArrayLength(env, plaintext);
    jbyte *plaintextBytes = (*env)->GetByteArrayElements(env, plaintext, NULL);
    jsize keyLen = (*env)->GetArrayLength(env, key);
    jbyte *keyBytes = (*env)->GetByteArrayElements(env, key, NULL);
    jsize ivLen = (*env)->GetArrayLength(env, iv);
    jbyte *ivBytes = (*env)->GetByteArrayElements(env, iv, NULL);

    // Prepare AES-GCM context
    Aes aes;
    int ret = wc_AesGcmSetKey(&aes, (const byte *) keyBytes, keyLen);
    if (ret != 0) {
        // Handle AES key setup error
        jclass exceptionClass = (*env)->FindClass(env, "java/lang/RuntimeException");
        if (exceptionClass != NULL) {
            char errorMessage[128];
            snprintf(errorMessage, sizeof(errorMessage), "AES key setup failed with error code: %d",
                     ret);
            (*env)->ThrowNew(env, exceptionClass, errorMessage);
        }
        (*env)->ReleaseByteArrayElements(env, plaintext, plaintextBytes, JNI_ABORT);
        (*env)->ReleaseByteArrayElements(env, key, keyBytes, JNI_ABORT);
        return NULL;
    }

    // Allocate buffer for ciphertext (same length as plaintext) and authentication tag (16 bytes)
    byte *ciphertext = malloc(plaintextLen);
    if (ciphertext == NULL) {
        // Handle memory allocation failure
        (*env)->ReleaseByteArrayElements(env, plaintext, plaintextBytes, JNI_ABORT);
        (*env)->ReleaseByteArrayElements(env, key, keyBytes, JNI_ABORT);
        return NULL;
    }
    byte tag[16];

    // Perform AES-GCM encryption
    ret = wc_AesGcmEncrypt(&aes, ciphertext, (const byte *) plaintextBytes, plaintextLen,
                           (const byte *) ivBytes, ivLen, tag, sizeof(tag), NULL, 0);


    if (ret != 0) {
        // Handle encryption error
        free(ciphertext);
        jclass exceptionClass = (*env)->FindClass(env, "java/lang/RuntimeException");
        if (exceptionClass != NULL) {
            char errorMessage[128];
            snprintf(errorMessage, sizeof(errorMessage),
                     "AES encryption failed with error code: %d", ret);
            (*env)->ThrowNew(env, exceptionClass, errorMessage);
        }
        (*env)->ReleaseByteArrayElements(env, plaintext, plaintextBytes, JNI_ABORT);
        (*env)->ReleaseByteArrayElements(env, key, keyBytes, JNI_ABORT);
        return NULL;
    }

    // Allocate result array (ciphertext + tag)
    jbyteArray result = (*env)->NewByteArray(env, plaintextLen + sizeof(tag));
    if (result == NULL) {
        free(ciphertext);
        (*env)->ReleaseByteArrayElements(env, plaintext, plaintextBytes, JNI_ABORT);
        (*env)->ReleaseByteArrayElements(env, key, keyBytes, JNI_ABORT);
        return NULL;
    }

    // Copy ciphertext and tag to result array
    (*env)->SetByteArrayRegion(env, result, 0, plaintextLen, (const jbyte *) ciphertext);
    (*env)->SetByteArrayRegion(env, result, plaintextLen, sizeof(tag), (const jbyte *) tag);

    // Cleanup
    free(ciphertext);
    (*env)->ReleaseByteArrayElements(env, plaintext, plaintextBytes, JNI_ABORT);
    (*env)->ReleaseByteArrayElements(env, key, keyBytes, JNI_ABORT);
    (*env)->ReleaseByteArrayElements(env, iv, ivBytes, JNI_ABORT);

    return result;
}

//Generate random Iv
JNIEXPORT jbyteArray JNICALL
Java_com_benedetto_galoislibrary_Galois_generateIv(JNIEnv *env,
                                                   jobject thiz,
                                                   jint ivSize) {
    //Allocate memory for the IV
    byte *iv = (byte *) malloc(ivSize);

    //Init RNG
    WC_RNG rng;
    wc_InitRng(&rng);

    //Generate random IV
    wc_RNG_GenerateBlock(&rng, iv, ivSize);

    //Create Java byte array
    jbyteArray ivArray = (*env)->NewByteArray(env, ivSize);
    (*env)->SetByteArrayRegion(env, ivArray, 0, ivSize, (jbyte *) iv);

    //Cleanup
    wc_FreeRng(&rng);
    free(iv);

    return ivArray;
}

// Decrypt data using AES-GCM
JNIEXPORT jbyteArray JNICALL
Java_com_benedetto_galoislibrary_Galois_decrypt(JNIEnv *env, jobject thiz,
                                                jbyteArray ciphertext,
                                                jbyteArray key,
                                                jbyteArray iv,
                                                jbyteArray tag) {
    // Get the ciphertext, key, IV, and tag from Java
    jsize ciphertextLen = (*env)->GetArrayLength(env, ciphertext);
    jbyte *ciphertextBytes = (*env)->GetByteArrayElements(env, ciphertext, NULL);
    jsize keyLen = (*env)->GetArrayLength(env, key);
    jbyte *keyBytes = (*env)->GetByteArrayElements(env, key, NULL);
    jsize ivLen = (*env)->GetArrayLength(env, iv);
    jbyte *ivBytes = (*env)->GetByteArrayElements(env, iv, NULL);
    jsize tagLen = (*env)->GetArrayLength(env, tag);
    jbyte *tagBytes = (*env)->GetByteArrayElements(env, tag, NULL);

    // Ensure IV is 12 bytes (96 bits), as expected by AES-GCM
    if (ivLen != 12) {
        jclass exceptionClass = (*env)->FindClass(env, "java/lang/IllegalArgumentException");
        if (exceptionClass != NULL) {
            (*env)->ThrowNew(env, exceptionClass,
                             "Invalid IV length. Must be 12 bytes for AES-GCM.");
        }
        return NULL;
    }

    // Prepare buffer for plaintext (same size as ciphertext)
    byte *plaintext = malloc(ciphertextLen);
    if (plaintext == NULL) {
        jclass exceptionClass = (*env)->FindClass(env, "java/lang/OutOfMemoryError");
        if (exceptionClass != NULL) {
            (*env)->ThrowNew(env, exceptionClass, "Failed to allocate memory for plaintext.");
        }
        return NULL;
    }

    // Initialize AES-GCM context
    Aes aes;
    int ret = wc_AesGcmSetKey(&aes, (const byte *) keyBytes, keyLen);
    if (ret != 0) {
        free(plaintext);
        jclass exceptionClass = (*env)->FindClass(env, "java/lang/RuntimeException");
        if (exceptionClass != NULL) {
            char errorMessage[128];
            snprintf(errorMessage, sizeof(errorMessage), "AES key setup failed with error code: %d",
                     ret);
            (*env)->ThrowNew(env, exceptionClass, errorMessage);
        }
        return NULL;
    }

    // Perform AES-GCM decryption
    ret = wc_AesGcmDecrypt(&aes, plaintext, (const byte *) ciphertextBytes, ciphertextLen,
                           (const byte *) ivBytes, ivLen, (const byte *) tagBytes, tagLen, NULL, 0);
    if (ret != 0) {
        free(plaintext);
        jclass exceptionClass = (*env)->FindClass(env, "java/lang/RuntimeException");
        if (exceptionClass != NULL) {
            char errorMessage[128];
            snprintf(errorMessage, sizeof(errorMessage),
                     "AES decryption failed with error code: %d", ret);
            (*env)->ThrowNew(env, exceptionClass, errorMessage);
        }
        return NULL;
    }

    // Convert plaintext to a Java byte array
    jbyteArray result = (*env)->NewByteArray(env, ciphertextLen);
    (*env)->SetByteArrayRegion(env, result, 0, ciphertextLen, (jbyte *) plaintext);

    // Cleanup
    free(plaintext);
    (*env)->ReleaseByteArrayElements(env, ciphertext, ciphertextBytes, JNI_ABORT);
    (*env)->ReleaseByteArrayElements(env, key, keyBytes, JNI_ABORT);
    (*env)->ReleaseByteArrayElements(env, iv, ivBytes, JNI_ABORT);
    (*env)->ReleaseByteArrayElements(env, tag, tagBytes, JNI_ABORT);

    return result;
}