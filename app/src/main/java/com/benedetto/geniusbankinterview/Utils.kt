package com.benedetto.geniusbankinterview

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// Function that takes another function as a parameter
fun applyOperation(x: Int, y: Int, operation: (Int, Int) -> Int): Int {
    return operation(x, y)
}

// Simulate a network call that emits values over time
fun getNumbers(): Flow<Int> = flow {
    for (i in 1..5) {
        delay(1000)
        emit(i) // Emits numbers 1 to 5, one per second
    }
}

interface Logger {
    fun log(message: String):Boolean = false
}

class ConsoleLogger : Logger {
    override fun log(message: String):Boolean {
        println("Log: $message")
        return true //return true when called to verify that log is called
    }
}

// Delegation in action
class AppLogger(private val logger: Logger) : Logger by logger

































