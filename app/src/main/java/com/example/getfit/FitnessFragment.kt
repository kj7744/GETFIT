package com.example.getfit


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_equipment.view.*
import kotlinx.android.synthetic.main.fragment_fitness.view.*

class FitnessFragment : Fragment() {
    lateinit var db: DatabaseReference
    lateinit var list:ArrayList<Any>
    lateinit var adapter: cardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_fitness, container, false)
        db= FirebaseDatabase.getInstance().reference
        list= ArrayList()
        var bottom_sheet= view.findViewById<RelativeLayout>(R.id.bottom_sheet)
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
//        list.add(trainermodel("","","","",""))
        adapter= cardAdapter(modelnames.fitm,1)
        adapter.set(list,context!!)
        adapter.setsheet(sheetBehavior, bottom_sheet)
        view.fitmrec.layoutManager= GridLayoutManager(context,2)
        view.fitmrec.adapter=adapter
        db.child("Admin").child("fitnessM").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("e","error")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    if(p0.hasChildren()){
                        for (ds in p0.children){
                            var t=fitnessM(
                                ds.child("name").value.toString(),
                                ds.child("url").value.toString()
                            )
                            list.add(t)
                        }
                        adapter.update(list)
                    }
                }
            }

        })
        return view
    }


}
