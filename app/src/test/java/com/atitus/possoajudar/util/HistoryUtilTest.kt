package com.atitus.possoajudar.util

import org.junit.Test
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

internal class HistoryUtilTest {

    @Test
    fun createNewHistory() {
        //create new history
        val history = createNewHistory("Teste", "Teste")

        //call your funcition
        history.like = 1

        assertThat(history.like, `is`(1))
    }
}