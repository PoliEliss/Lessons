package com.rorono.lessons

import android.util.Log

class ViewModel(private val model: Model) {
    private var textObservable: TextObservable? = null
    private val textCallback = object : TextCallback {
        override fun updateText(str: String) {
            textObservable?.postValue(str)
        }
    }

    fun init(textObservable: TextObservable) {
        this.textObservable = textObservable

    }

    fun clear() {
        textObservable = null
    }

    fun resumeCounting() {
        model.start(textCallback)
    }

    fun pauseCounting() {
        model.stop()
    }

}

class TextObservable {
    private lateinit var callback: TextCallback
    fun observe(callback: TextCallback) {
        Log.d("TEST", "observe in TEXTOBSERVABLE")
        this.callback = callback
    }

    fun postValue(text: String) {
        Log.d("TEST", "postValue in TEXTOBSERVABLE")
        callback.updateText(text)

    }
}



interface TextCallback {
    fun updateText(str: String)
}