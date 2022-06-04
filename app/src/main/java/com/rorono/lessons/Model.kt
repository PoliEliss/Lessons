package com.rorono.lessons

import android.util.Log
import java.util.*

class Model(private val dataSource: DataSource) {


    private var timer: Timer? = null
    private val timerTask = object : TimerTask() {
        override fun run() {
            count++
            callback?.updateText(count.toString())
        }
    }
    private var callback: TextCallback? = null
    private var count = -1


    fun start(textCallback: TextCallback) {
        callback = textCallback
        if (count < 0)
            count = dataSource.getInt(COUNTER_KEY)
        timer = Timer()
        timer?.scheduleAtFixedRate(timerTask, 1000, 1000)
    }

    fun stop() {
        timer = null
        dataSource.saveInt(COUNTER_KEY, count)
        timer?.cancel()
        timer = null
    }

    companion object {
        private const val COUNTER_KEY = "counterKey"
        private const val TAG = "uniqueCounterTag"
    }
}
