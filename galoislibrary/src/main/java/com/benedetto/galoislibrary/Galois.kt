package com.benedetto.galoislibrary



internal object Galois {
    init {
        System.loadLibrary("galoislibrary")
    }

    external fun generateKeyPair(): KeyPair
    external fun computeSharedKey(
        senderPublicKey: ByteArray,
        receiverPrivateKey: ByteArray
    ): ByteArray

    external fun deriveKey(sharedSecret: ByteArray): ByteArray
    external fun generateIv(size: Int): ByteArray
    external fun encrypt(iv: ByteArray, plainText: ByteArray, derivedKey: ByteArray): ByteArray
    external fun decrypt(
        cipherText: ByteArray,
        derivedKey: ByteArray,
        iv: ByteArray,
        tag: ByteArray
    ): ByteArray
}