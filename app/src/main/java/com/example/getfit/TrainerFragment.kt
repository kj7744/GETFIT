package com.example.getfit


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_trainer.view.*
import kotlinx.android.synthetic.main.show_more_dialog.*

class TrainerFragment : Fragment() {
    lateinit var db:DatabaseReference
    lateinit var list:ArrayList<Any>
    lateinit var adapter: cardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this
        var view= inflater.inflate(R.layout.fragment_trainer, container, false)
        db=FirebaseDatabase.getInstance().reference
        list= ArrayList()
        var bottom_sheet=view.findViewById(R.id.bottom_sheet)  as RelativeLayout
        var sheetBehavior=BottomSheetBehavior.from(bottom_sheet)
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                var bmimg=bottomSheet.findViewById(R.id.bmimg) as ImageView
                bmimg.minimumHeight=(200*slideOffset*resources.displayMetrics.density).toInt()
                bmimg.minimumWidth=(200*slideOffset*resources.displayMetrics.density).toInt()
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {

                    }

                }
            }

        })
//        list.add(trainermodel("","","","",""))
        adapter= cardAdapter(modelnames.trainer,1)
        adapter.set(list,context!!)
        adapter.setsheet(sheetBehavior,bottom_sheet)
        view.trainerrec.layoutManager=LinearLayoutManager(context)
        view.trainerrec.adapter=adapter
        db.child("Admin").child("Trainer").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("e","error")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    if(p0.hasChildren()){
                        for (ds in p0.children){
                            var t=trainermodel(
                                ds.child("name").value.toString(),
                                ds.child("desc").value.toString(),
                                ds.child("exp").value.toString(),
                                ds.child("gen").value.toString(),
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
