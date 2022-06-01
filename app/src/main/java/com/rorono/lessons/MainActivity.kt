package com.rorono.lessons

import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val string = "This is title from code!"
        titleTextView.text = string
        titleTextView.setColor(R.color.black)

        val agreementTextView: TextView = findViewById(R.id.agreementTextView)
        val fullText = getString(R.string.agreement_full_text)
        val confidential = getString(R.string.confidential_info)
        val policy = getString(R.string.privacy_policy)

        val spannableString = SpannableString(fullText) //Создаем SpannableString


      /*  val confidentialClickable = object : ClickableSpan() {
            override fun onClick(p0: View) {
                Snackbar.make(p0, "Go to Link", Snackbar.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) { //второй метод абстрактного класса ClickableSpan()
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = Color.parseColor("#FF0000")

            }
        }
        val policyClickable = object : ClickableSpan() {
            override fun onClick(p0: View) {
                Snackbar.make(p0, "Go to Link2", Snackbar.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = Color.parseColor("#FF0000")
            }

        }*/

        val confidentialClickable = MyClickableSpan(){
            Snackbar.make(it,"Go to link", Snackbar.LENGTH_SHORT).show()
        }
        val policyClickable = MyClickableSpan{
            Snackbar.make(it,"Go to link2",Snackbar.LENGTH_SHORT).show()
        }

        spannableString.setSpan(
            confidentialClickable,
            fullText.indexOf(confidential),
            fullText.indexOf(confidential) + confidential.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            policyClickable,
            fullText.indexOf(policy),
            fullText.indexOf(policy) + policy.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        agreementTextView.run {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }

    }
}



fun TextView.setColor(@ColorRes colorResId: Int, theme: Resources.Theme? = null) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        setTextColor(resources.getColor(R.color.black, theme))
    else
        setTextColor(resources.getColor(R.color.black))
}