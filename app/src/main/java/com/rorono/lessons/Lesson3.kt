package com.rorono.lessons

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Lesson3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson3)

        val textInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout)
        val textInputEditText = textInputLayout.editText as TextInputEditText

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(textInputEditText.text.toString())
                    .matches()
            ) {
                loginButton.isEnabled = false
                hideMyKeyBoard()

                Snackbar.make(loginButton, "Go to postLogin", Snackbar.LENGTH_SHORT).show()

            } else {
                textInputLayout.isErrorEnabled = true
                textInputLayout.error = getString(R.string.invalid_email_message)
            }
        }
        textInputEditText.listenChanges {
            textInputLayout.isErrorEnabled = false

        }

        /*  textInputEditText.addTextChangedListener(object : SimpleTextWatcher() {
              override fun afterTextChanged(s: Editable?) {
                  val input = s.toString()
                  val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()
                  textInputLayout.isErrorEnabled = !valid
                  val error = if (valid)"" else getString(R.string.invalid_email_message)
                  textInputLayout.error = error
                  if (valid) Toast.makeText(
                      this@Lesson3,
                      "Правильный e-mail адрес",
                      Toast.LENGTH_LONG
                  ).show()
                  if (input.endsWith("@g")){
                      val fullMail = "${input}mail.com"
                      textInputEditText.setTextCorrectly(fullMail)
                  }

              }
          })
      }*/

        fun TextInputEditText.setTextCorrectly(text: CharSequence) {
            setText(text)
            setSelection(text.length)
        }


    }

    fun TextInputEditText.listenChanges(block: (text: String) -> Unit) {
        addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(p0: Editable?) {
                block.invoke(p0.toString())
            }
        })
    }

    fun hideMyKeyBoard(){
        val view = this.currentFocus
        if(view != null){
            val hide = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hide.hideSoftInputFromWindow(view.windowToken,0)
        } else{
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }
}