package com.benedetto.modernandroid.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benedetto.core.model.User
import com.benedetto.core.usecase.GetUserUseCase
import com.benedetto.galoislibrary.GaloisLib
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) : ViewModel() {

    private val _usersList: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    val usersList: StateFlow<List<User>> = _usersList

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            encryptFun()
            getUserUseCase()
                .catch { e ->
                    e.localizedMessage?.let {
                        Log.d("UserViewModel", it)
                    }
                }
                .buffer()
                .collect { users ->
                    _usersList.value = users
                }
        }
    }
}

private suspend fun encryptFun() = withContext(Dispatchers.IO){
    try{
        val senderKeyPair = GaloisLib.generateKeyPair()
        val receiverKeyPair = GaloisLib.generateKeyPair()

        val sharedSecret = GaloisLib.computeSharedKey(senderKeyPair.publicKey, receiverKeyPair.privateKey)
        val derivedKey = GaloisLib.deriveKey(sharedSecret)
        val iv = GaloisLib.generateIv(12)

        val originalText = "Hello Secure World!"
        Log.d("Galois", "Original text: $originalText")

        val encryptedText = GaloisLib.encrypt(iv, "Hello Secure World!", derivedKey)
        Log.d("Galois", "Encrypted text IV: ${encryptedText.iv.joinToString { "%02x".format(it) }}")
        Log.d("Galois", "Encrypted text CIPHER: ${encryptedText.cipherText.joinToString { "%02x".format(it) }}")
        Log.d("Galois", "Encrypted text TAG: ${encryptedText.tag.joinToString { "%02x".format(it) }}")

        val decryptedText = GaloisLib.decrypt(encryptedText.cipherText, derivedKey, encryptedText.iv, encryptedText.tag)
        Log.d("Galois", "Decrypted text: $decryptedText")

    }catch (e: RuntimeException){
        Log.e("Galois", "Error deriving key: ${e.message}")
    }
}
