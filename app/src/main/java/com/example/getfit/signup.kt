package com.example.getfit

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_membership_form.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.email
import java.util.*
import kotlin.math.sign


class signup : AppCompatActivity() {
    lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().reference
        signUp_user.setOnClickListener {
            signupUser()
        }

    }

    private fun signupUser() {
        val susername = username.text.toString().trim()
        val semail = email.text.toString().trim()
        val spass = pass.text.toString().trim()
        val phno = ph.text.toString().trim()

        if (susername.isEmpty()) {
            username.error = "user name cannot be  empty"
            username.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {
            email.error = " Invalid email"
            email.requestFocus()
            return
        }
        if (spass.isEmpty() || spass.length < 6) {
            pass.error = "password cannot be empty and less than 6 digit"
            pass.requestFocus()
            return
        }

        if (susername.isNotEmpty() and semail.isNotEmpty() and phno.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(semail, spass)
                .addOnCompleteListener(this) {
                    val hashMap = hashMapOf(
                        "username" to susername,
                        "email" to semail,
                        "phno" to phno
                    )
                    db.child("User")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid.toString())
                        .setValue(hashMap)
                        .addOnSuccessListener {
                            Toast.makeText(
                                baseContext, "Sigup successfull.",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }

                }
                .addOnFailureListener {
                    Toast.makeText(
                        baseContext, "SignUp failed,try again later",
                        Toast.LENGTH_SHORT
                    ).show()

                }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}

