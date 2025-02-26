package com.benedetto.modernandroid


import com.benedetto.modernandroid.utils.applyOperation
import com.benedetto.modernandroid.utils.getNumbers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsUnitTest {

    //Write a test that collects Flow values using runTest (runBlockingTest is deprecated)
    @Test
    fun collectFlowValues() = runTest {
        val _values = MutableStateFlow<List<Int>>(emptyList())
        val values: StateFlow<List<Int>> = _values.asStateFlow()
        launch {
            getNumbers().collect { result ->
                _values.value += result
            }
        }
        delay(7000)
        assertEquals(listOf(1, 2, 3, 4, 5), values.value)

    }

    //verify apply operations work with different functions
    @Test
    fun verifyApplyOperations() {

        val addOperation = fun(a: Int, b: Int) = a + b
        val addOperationResult = applyOperation(1, 1, addOperation)

        assertEquals(2, addOperationResult)

        val multiplyOperation = fun(a: Int, b: Int) = a * b
        val multiplyOperationResult = applyOperation(2, 2, multiplyOperation)

        assertEquals(4, multiplyOperationResult)
    }


}



















