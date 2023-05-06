package com.example.madd


import com.google.common.truth.Truth.assertThat
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ValidatorAgentsTest{

    @Test
    fun whenInputIsValid(){
        val email= "not empty"
        val name="not empty"
        val phone="not empty"
        val description="not empty"
        val img="not empty"
        val result = AgentsValidator.validateinput(email,name,phone,description,img )

        assertThat(result).isEqualTo(true)
    }

    @Test
    fun whenInputIsInValid(){
        val email= ""
        val name=""
        val phone=""
        val description=""
        val img=""
        val result = AgentsValidator.validateinput(email,name,phone,description,img)

        assertThat(result).isEqualTo(false)
    }
}