package com.example.getfit

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*


class signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        signUp_user.setOnClickListener {
            signupUser()
        }

    }

    private fun signupUser() {
        if (username.text.toString().isEmpty()) {
            username.error = "user name cannot be  empty"
            username.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            email.error = " Invalid email"
            email.requestFocus()
            return
        }
        if (pass.text.toString().isEmpty() || pass.text.length < 6) {
            pass.error = "password cannot be empty and less than 6 digit"
            pass.requestFocus()
            return
        }
//        val password: String = passwordText.getText().toString()
//        if (password.isEmpty() || password.length < 6) {
//            passwordText.setError("Password cannot be less than 6 characters!")
//        } else {
//            passwordText.setError(null)
//            startActivity(Intent(this@RegistrationActivity, MainActivity::class.java))
//        }

        auth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Sigup successfull.",
                        Toast.LENGTH_SHORT).show()
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

