package com.rorono.lessons.unittests

import android.util.Log

class Doer(private val logging: Logging) {
    private var mainThingDone = false


    fun doMain() {
        if (!mainThingDone) {
            logging.log("main thing done")
            mainThingDone = true
        }
    }


}

class LoggingTool:Logging {
    override fun log(message: String) {
        Log.d(javaClass.canonicalName, message)
    }


}

interface Logging {
    fun log(message: String)
}