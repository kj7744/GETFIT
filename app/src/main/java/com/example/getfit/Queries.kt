package com.example.getfit

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_queries.*
import java.util.*

class Queries : AppCompatActivity() {
    lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queries)
        db = FirebaseDatabase.getInstance().reference
        qsubmit.setOnClickListener {
            validateMenuItem()
        }
    }

    private fun validateMenuItem() {
        var qtext = qtext.text.toString().trim()
        if (qtext.isNotEmpty()) {
            var a = AlertDialog.Builder(this)
                .setTitle("Add query")
                .setMessage(" Are you sure you want to add this")
                .setPositiveButton(
                    "Yes"
                ) { dialog, which ->
                    uploadMenuItem(qtext)
                    //  Toast.makeText(this," Query added",Toast.LENGTH_SHORT)
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.cancel()
                }
                .create()
                .show()

        } else {
            Toast.makeText(applicationContext, "Please enter valid info ", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun uploadMenuItem(qtext: String) {
        db.child("User").child(FirebaseAuth.getInstance().currentUser!!.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    var hashMap = hashMapOf(
                        "qtext" to qtext,
                        "email" to FirebaseAuth.getInstance().currentUser!!.email.toString(),
                        "name" to p0.child("username").value.toString()
                    )
                    db.child("Admin")
                        .child("Queries")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid.toString())
                        .setValue(hashMap)
                        .addOnSuccessListener {
                            Toast.makeText(
                                applicationContext,
                                "Query is added successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                }

            })

    }

}
