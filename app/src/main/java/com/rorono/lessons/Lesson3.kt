package com.rorono.lessons

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Lesson3 : AppCompatActivity() {

    private companion object {
        const val INITIAL = 0
        const val PROGRESS = 1
        const val SUCCESS = 2
        const val FAILED = 3
        private var state = INITIAL
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson3)
        Log.d("TEST", "onCreste ${savedInstanceState == null}")
        savedInstanceState?.let {
            state = it.getInt("screenState")
        }
        Log.d("TEST", "state is ${state}")


        val textInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout)
        val textInputEditText = textInputLayout.editText as TextInputEditText
        val checkBox = findViewById<CheckBox>(R.id.checkbox)
        val agree = getString(R.string.i_agree)
        val fullText = getString(R.string.agreement_full_texts)
        val spannebleString = SpannableString(fullText)
        checkBox.text = spannebleString


        val confidentialClickable = MyClickableSpan() {
            Snackbar.make(it, "Go to link", Snackbar.LENGTH_SHORT).show()
        }
        spannebleString.setSpan(
            confidentialClickable,
            fullText.indexOf(agree),
            fullText.indexOf(agree) + agree.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        checkBox.run {
            text = spannebleString
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.BLUE
        }


        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.isEnabled = false
        checkBox.setOnClickListener {
            loginButton.isEnabled = true
        }

        textInputEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()
                textInputLayout.isErrorEnabled = !valid
                val error = if (valid) "" else getString(R.string.invalid_email_message)
                textInputLayout.error = error
                if (valid) Toast.makeText(
                    this@Lesson3,
                    "Правильный e-mail адрес",
                    Toast.LENGTH_LONG
                ).show()
                if (input.endsWith("@g")) {
                    val fullMail = "${input}mail.com"
                    textInputEditText.setTextCorrectly(fullMail)
                }
            }
        })
        val contentLayout = findViewById<ViewGroup>(R.id.contentLayout)

        when (state) {
            FAILED -> showDailog(contentLayout = contentLayout)
            SUCCESS -> {
                Snackbar.make(contentLayout,"Success",Snackbar.LENGTH_SHORT).show()
            }
        }

        val progressBar = findViewById<View>(R.id.progressBar)
        loginButton.setOnClickListener {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(textInputEditText.text.toString())
                    .matches()
            ) {
                hideMyKeyBoard()
                loginButton.isEnabled = false

                contentLayout.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                state = PROGRESS
                Log.d("TEST", "stateProgress ${state}")

                Handler(Looper.myLooper()!!).postDelayed({
                    state = FAILED
                    contentLayout.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE


                }, 3000)
            }
        }
        /*  loginButton.setOnClickListener {
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
          }*/
        textInputEditText.listenChanges {
            textInputLayout.isErrorEnabled = false

        }

        /*textInputEditText.addTextChangedListener(object : SimpleTextWatcher() {
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


    }

    private fun showDailog(contentLayout: ViewGroup) {
        val dialog = Dialog(this)
        val view =
            LayoutInflater.from(this).inflate(R.layout.dialog, contentLayout, false)
        dialog.setCancelable(false)
        view.findViewById<View>(R.id.closeButton).setOnClickListener {
            state = INITIAL
            dialog.dismiss()
        }
        dialog.setContentView(view)
        dialog.show()

    }

    fun TextInputEditText.listenChanges(block: (text: String) -> Unit) {
        addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(p0: Editable?) {
                block.invoke(p0.toString())
            }
        })
    }

    fun hideMyKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val hide = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hide.hideSoftInputFromWindow(view.windowToken, 0)
        } else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }

    fun TextInputEditText.setTextCorrectly(text: CharSequence) {
        setText(text)
        setSelection(text.length)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("screenState", state)
    }

}