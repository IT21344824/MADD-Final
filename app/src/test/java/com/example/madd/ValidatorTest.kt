package com.example.madd


import com.google.common.truth.Truth.assertThat
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ValidatorTest{

    @Test
    fun whenInputIsValid(){
        val Email= "empty"
        val name="umar"
        val message="fight"
        val result = Validator.validateinput(Email,name,message)

        assertThat(result).isEqualTo(true)
    }

    @Test
    fun whenInputIsInValid(){
        val Email= ""
        val name=""
        val message=""
        val result = Validator.validateinput(Email,name,message)

        assertThat(result).isEqualTo(false)
    }
}