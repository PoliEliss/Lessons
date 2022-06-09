package com.rorono.lessons.unittests

import org.junit.Assert.*
import org.junit.Test

class DoerTest {

    private val logger = TestLogger()
    private val doer = Doer(logger)

    @Test
    fun test_one_time_case() {
        doer.doMain()
        val actual = logger.logCallsCount
        val expected = 1
        assertEquals(expected, actual)

    }

    private inner class TestLogger : Logging {
        var logCallsCount = 0
        override fun log(message: String) {
            logCallsCount++
        }

    }
}
