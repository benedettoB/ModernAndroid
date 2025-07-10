package com.benedetto.domain

import com.benedetto.domain.utils.removeNewLines
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.Assert.assertTrue

class StringUtilsTest {

    @Test
    fun removeNewLinesTest(){
        val stringContainsNewLine = "String with a new line \n"
        assertTrue(stringContainsNewLine.contains("\n"))
        //remove new lines
        val newLinesRemovedString = stringContainsNewLine.removeNewLines()
        assertFalse(newLinesRemovedString.contains("\n"))

    }
}