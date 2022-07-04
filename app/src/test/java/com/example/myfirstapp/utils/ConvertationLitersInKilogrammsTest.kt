package com.example.myfirstapp.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ConversionLitersInKilogramTest {
    private val coefficient = 0.83
    private val negativeAnswer = "-"

    @Test
    fun conversation_correctData_NullCheck(){
        assertNotNull(setDataDieselFuelInKilograms(coefficient, 10.0))
    }

    @Test
    fun conversation_NullData_NullCheck(){
        assertNotNull(setDataDieselFuelInKilograms(coefficient, null))
    }

    @Test
    fun conversation_incorrectData_WrongResult(){
        assertNotEquals(setDataDieselFuelInKilograms(coefficient, 0.0), "0")
    }

    @Test
    fun conversation_correctData_WrongResult(){
        assertNotEquals(setDataDieselFuelInKilograms(coefficient, 4.0), negativeAnswer)
    }

    @Test
    fun conversation_InputNull(){
        assertEquals(setDataDieselFuelInKilograms(coefficient, null), negativeAnswer)
    }

    @Test
    fun conversation_InputZero(){
        assertEquals(setDataDieselFuelInKilograms(coefficient, 0.0), negativeAnswer)
    }

    @Test
    fun conversation_InputNegativeNumber(){
        assertEquals(setDataDieselFuelInKilograms(coefficient, -10.0), negativeAnswer)
    }

    @Test
    fun conversation_correctData_InputInteger(){
        assertEquals(setDataDieselFuelInKilograms(coefficient, 10.0), "8,30")
    }
    @Test
    fun conversation_correctData_InputDouble(){
        assertEquals(setDataDieselFuelInKilograms(coefficient, 5.5), "4,56")
    }
}