package com.example.getfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_membership_form.*
import java.util.*

class Membership_form : AppCompatActivity() {
    lateinit var db: DatabaseReference
    var price=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_membership_form)
        db = FirebaseDatabase.getInstance().reference
        price=intent.extras!!.getString("price")!!
        sendm.setOnClickListener {
            validate()
        }
    }

    fun validate() {
        val name = full_name.text.toString().trim()
        val address = address.text.toString().trim()
        val dob = dob.text.toString().trim()
        val email = email.text.toString().trim()
        val phn = phn_no.text.toString().trim()
        val bloodgrp = bloodgrp.text.toString().trim()
        val gender = findViewById<RadioButton>(radiog.checkedRadioButtonId).text.toString().trim()
        if (name.isNotEmpty() and address.isNotEmpty() and dob.isNotEmpty() and email.isNotEmpty() and phn.isNotEmpty() and bloodgrp.isNotEmpty() and gender.isNotEmpty()) {

            var a = AlertDialog.Builder(this)
                .setTitle("Add Membership Details")
                .setMessage(" Are you sure you want to add this")
                .setPositiveButton(
                    "Yes"
                ) { dialog, which ->
                    uploadMenuItem(name, address, dob, email, phn, bloodgrp, gender)
                    progm.visibility = View.VISIBLE
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.cancel()
                }
                .create()
                .show()
        } else {
            Toast.makeText(applicationContext, "Please enter valid details ", Toast.LENGTH_SHORT)
                .show()
        }
    }

    //ctrl alt l
    private fun uploadMenuItem(
        name: String,
        address: String,
        dob: String,
        email: String,
        phn: String,
        bloodgrp: String,
        gen: String
    ) {
        //compress the image
//                var bitmap=BitmapFactory.decodeStream(inputStream)

        var hashMap = hashMapOf(

            "name" to name,
            "address" to address,
            "dob" to dob,
            "email" to email,
            "bloodgrp" to bloodgrp,
            "phn" to phn,
            "gen" to gen
        )
        db.child("User")
            .child(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .child("memdetails")
            .setValue(hashMap)
            .addOnSuccessListener {
                progm.visibility = View.GONE
                if(price.isNotEmpty()){
                    Toast.makeText(applicationContext, "Details added successfully", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(applicationContext,payment::class.java)
                        .apply {
                            putExtra("price",price)
                        })
                }
                else{
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }
    }
}
