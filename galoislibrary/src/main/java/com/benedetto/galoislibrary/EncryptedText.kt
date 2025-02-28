package com.benedetto.galoislibrary

data class EncryptedText(val iv: ByteArray, val cipherText: ByteArray, val tag: ByteArray)