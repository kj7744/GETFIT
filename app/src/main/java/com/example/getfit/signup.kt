package com.example.getfit

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.email
import kotlinx.android.synthetic.main.activity_signup.pass
import kotlinx.android.synthetic.main.login_activity.*

class signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        signUp_form.setOnClickListener {
            signupUser()
        }

    }

    private fun signupUser() {
        if (username.text.toString().isEmpty()) {
            username.error = "user name is empty"
            username.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            email.error = "user name is empty"
            email.requestFocus()
            return
        }
        if (pass.text.toString().isEmpty()) {
            username.error = "user name is empty"
            username.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "SignUp failed,try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                    // ...
                }

            }
    }
}

