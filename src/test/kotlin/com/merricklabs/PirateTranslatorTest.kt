package com.merricklabs

import kotlin.test.Test
import kotlin.test.assertEquals

class PirateTranslatorTest {
    // 1
    @Test
    fun testPirateTranslator() {
        // 2
        val classUnderTest : PirateTranslator = DefaultPirateTranslator()

        // 3
        assertEquals("Ahoy!, I am Captain Jack Sparrow", classUnderTest.translate("Hello, I am Captain Jack Sparrow"))
        // 3
        assertEquals("Aye!", classUnderTest.translate("Yes"))
    }
}