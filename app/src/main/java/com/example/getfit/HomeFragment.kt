package com.example.getfit


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment


class HomeFragment : Fragment() {
    lateinit var viewFlipper: ViewFlipper
    val images = intArrayOf(R.drawable.gympic, R.drawable.gympic1, R.drawable.gympic2)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.homefragment_activity, container, false)
        viewFlipper=view.findViewById(R.id.v_flipper)
        for (i in images.indices) { // This will create dynamic image views and add them to the ViewFlipper.
            setFlipperImage(images[i])
        }
        return view


    }
 private fun setFlipperImage(res: Int) {
        Log.i("Set Filpper Called", res.toString() + "")
        val image = ImageView(context)
        image.setBackgroundResource(res)
        viewFlipper!!.addView(image)
        viewFlipper!!.flipInterval = 1600
        viewFlipper!!.isAutoStart = true
    }

}


