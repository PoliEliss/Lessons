package com.rorono.lessons

import java.time.chrono.ChronoPeriod
import javax.security.auth.callback.Callback

interface TimeTicker {
    fun start(callback: Callback, period: Long = 1000)
    fun stop()
    interface Callback {
        fun tick()
    }

}