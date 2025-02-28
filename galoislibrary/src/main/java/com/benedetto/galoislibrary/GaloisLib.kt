package com.benedetto.galoislibrary


object GaloisLib {

    @Synchronized
    fun generateKeyPair(): KeyPair {
        return Galois.generateKeyPair()
    }

    @Synchronized
    fun computeSharedKey(senderPublicKey: ByteArray, receiverPrivateKey: ByteArray): ByteArray {
        return Galois.computeSharedKey(senderPublicKey, receiverPrivateKey)
    }

    @Synchronized
    fun deriveKey(sharedSecret: ByteArray): ByteArray {
        return Galois.deriveKey(sharedSecret)
    }

    @Synchronized
    fun generateIv(size: Int): ByteArray {
        return Galois.generateIv(size)
    }

    @Synchronized
    fun encrypt(iv: ByteArray, plainText: String, derivedKey: ByteArray): EncryptedText {
        val originalText = plainText.toByteArray(Charsets.UTF_8)
        val encryptedText = Galois.encrypt(iv, originalText, derivedKey)
        val ciphertext = getCipher(encryptedText, originalText)
        val tag = getTag(ciphertext, encryptedText)
        return EncryptedText(iv, ciphertext, tag)
    }

    @Synchronized
    fun decrypt(
        cipherText: ByteArray,
        derivedKey: ByteArray,
        iv: ByteArray,
        tag: ByteArray
    ): String {
        val decryptedByteArray = Galois.decrypt(cipherText, derivedKey, iv, tag)
        return String(decryptedByteArray, Charsets.UTF_8)
    }

    private fun getCipher(encryptedText: ByteArray, originalText: ByteArray): ByteArray {
        return encryptedText.copyOfRange(0, originalText.size)
    }

    private fun getTag(cipherText: ByteArray, encryptedText: ByteArray): ByteArray {
        return encryptedText.copyOfRange(cipherText.size, encryptedText.size)
    }
}
