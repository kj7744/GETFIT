package com.example.getfit

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_queries_fragement.*


/**
 * A simple [Fragment] subclass.
 */
class QueriesFragement : Fragment() {
    lateinit var db: DatabaseReference
    private var btn:Button?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_queries_fragement, container, false)
        btn=view!!.findViewById(R.id.qsubmit) as Button
        db = FirebaseDatabase.getInstance().reference
        btn!!.setOnClickListener {
            validateMenuItem()
        }
        return view
    }
    private fun validateMenuItem() {
        var qtext = qtext.text.toString().trim()
        if (qtext.isNotEmpty()) {
            var a = AlertDialog.Builder(context)
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
            Toast.makeText(context, "Please enter valid info ", Toast.LENGTH_SHORT)
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
                            Toast.makeText(context,
                                "Query is added successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                }

            })

    }


}
