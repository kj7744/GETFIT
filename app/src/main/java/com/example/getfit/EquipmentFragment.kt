package com.example.getfit


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getfit.cardAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_equipment.view.*
import kotlinx.android.synthetic.main.fragment_trainer.view.*

/**
 * A simple [Fragment] subclass.
 */
class EquipmentFragment : Fragment() {
    lateinit var db: DatabaseReference
    lateinit var list:ArrayList<Any>
    lateinit var adapter: cardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_equipment, container, false)
        db= FirebaseDatabase.getInstance().reference
        list= ArrayList()
//        list.add(trainermodel("","","","",""))
        var bottom_sheet=view.findViewById(R.id.bottom_sheet)  as RelativeLayout
        var sheetBehavior= BottomSheetBehavior.from(bottom_sheet)
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                var bmimg= bottomSheet.findViewById(R.id.bmimg) as ImageView
                bmimg.minimumHeight=(200*slideOffset*resources.displayMetrics.density).toInt()
                bmimg.minimumWidth=(200*slideOffset*resources.displayMetrics.density).toInt()
            }

            override fun onStateChanged(bottomSheet: View, newState: Int){
            }

        })
        adapter= cardAdapter(modelnames.eqip,1)
        adapter.setsheet(sheetBehavior,bottom_sheet)
        adapter.set(list,context!!)
        adapter.setsheet(sheetBehavior,bottom_sheet)
        view.equiprec.layoutManager= LinearLayoutManager(context)
        view.equiprec.adapter=adapter
        db.child("Admin").child("Equipment").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("e","error")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    if(p0.hasChildren()){
                        for (ds in p0.children){
                            var t=equipment(
                                ds.child("name").value.toString(),
                                ds.child("desc").value.toString(),
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


