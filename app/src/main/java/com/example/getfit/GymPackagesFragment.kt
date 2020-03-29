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
import kotlinx.android.synthetic.main.fragment_fitness.view.*
import kotlinx.android.synthetic.main.fragment_gym_packages.view.*
import kotlinx.android.synthetic.main.trainercard.view.*


class GymPackagesFragment : Fragment() {
    lateinit var db: DatabaseReference
    lateinit var list:ArrayList<Any>
    lateinit var adapter: cardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_gym_packages, container, false)
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
        var colorlist= ArrayList<String>()
        colorlist.add("#0000FF")
        colorlist.add("#FF0000")
        colorlist.add("#FF6600")
        colorlist.add("#6600FF")
        colorlist.add("#179C52")
        colorlist.add("#C70039")
        colorlist.add("#900C3F")

        adapter= cardAdapter(modelnames.gympack,2)
        adapter.set(list,context!!)
        adapter.setcolorlist(colorlist)
        adapter.setsheet(sheetBehavior,bottom_sheet)
        view.prec.layoutManager= LinearLayoutManager(context)
        view.prec.adapter=adapter
        db.child("Admin").child("Package").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("e","error")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    if(p0.hasChildren()){
                        for (ds in p0.children){
                            var t=gympackage(
                                ds.child("name").value.toString(),
                                ds.child("price").value.toString(),
                                ds.child("desc").value.toString()
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
