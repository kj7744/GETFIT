package com.example.getfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_fitnessitem.*
import kotlinx.android.synthetic.main.fragment_equipment.view.*

class fitnessitem : AppCompatActivity() {
    lateinit var db: DatabaseReference
    lateinit var list:ArrayList<Any>
    lateinit var adapter: cardAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitnessitem)
        var name=intent.extras?.getString("name")
        db= FirebaseDatabase.getInstance().reference
        var bottom_sheet= findViewById<RelativeLayout>(R.id.bottom_sheet)
        var sheetBehavior= BottomSheetBehavior.from(bottom_sheet)
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                var bmimg=bottomSheet.findViewById(R.id.bmimg) as ImageView
                bmimg.minimumHeight=(200*slideOffset*resources.displayMetrics.density).toInt()
                bmimg.minimumWidth=(200*slideOffset*resources.displayMetrics.density).toInt()
            }

            override fun onStateChanged(bottomSheet: View, newState: Int){
            }

        })
        list= ArrayList()
//        list.add(trainermodel("","","","",""))
        adapter= cardAdapter(modelnames.fitmitem,1)
        adapter.set(list,applicationContext)
        adapter.setsheet(sheetBehavior, bottom_sheet)
        fititemrec.layoutManager= LinearLayoutManager(applicationContext)
        fititemrec.adapter=adapter
        db.child("Admin").child("fitnessM").child(name!!).child("items").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("e","error")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    if(p0.hasChildren()){
                        for (ds in p0.children){
                            var t=fitnessitems(
                                ds.child("name").value.toString(),
                                ds.child("price").value.toString(),
                                ds.child("desc").value.toString(),
                                ds.child("url").value.toString()
                            )
                            list.add(t)
                        }
                        adapter.update(list)
                    }
                }
                else{
                    Toast.makeText(applicationContext,"There is no product in this category",Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}
