package com.example.getfit

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.gymcard.view.*
import kotlinx.android.synthetic.main.trainercard.view.*
import java.util.*
import kotlin.collections.ArrayList

class cardAdapter(var type:String,var cardtype:Int):RecyclerView.Adapter<cardViewHolder>(){
    lateinit var context: Context
    lateinit var list:ArrayList<Any>
    lateinit var sheetview:View
    lateinit var sheetBehavior:BottomSheetBehavior<RelativeLayout>
    var item_normal_card_view=1
    var item_gym_card_view=2
    var colorlist=ArrayList<String>()
    fun set(ob: ArrayList<Any>, context: Context){
        this.list=ob
        this.context=context
    }
    fun setcolorlist(colorlist:ArrayList<String>){
        this.colorlist=colorlist
    }
    fun setsheet(sheetBehavior: BottomSheetBehavior<RelativeLayout>,sheetview:View){
        this.sheetBehavior=sheetBehavior
        this.sheetview=sheetview
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardViewHolder {
         var view: View? =null
        if(viewType==item_normal_card_view) {
            view = LayoutInflater.from(context).inflate(R.layout.trainercard, parent, false)!!
        }
        else{
            view = LayoutInflater.from(context).inflate(R.layout.gymcard, parent, false)!!

        }
        return cardViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int{
        when(cardtype){
            item_normal_card_view->{
                return item_normal_card_view
            }
            else->{
                return item_gym_card_view
            }
        }

    }
    override fun getItemCount(): Int {
        return list.size
    }
    fun update(list:ArrayList<Any>){
        this.list=list
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: cardViewHolder, position: Int) {
        var bmimg=sheetview.findViewById(R.id.bmimg) as ImageView
        var bmname=sheetview.findViewById(R.id.bmname) as TextView
        var bmdesc=sheetview.findViewById(R.id.bmdesc) as TextView
        var bmprice=sheetview.findViewById(R.id.bmprice) as TextView
        var bmexp=sheetview.findViewById(R.id.bmexp) as TextView
        var bmgen=sheetview.findViewById(R.id.bmgen) as TextView
        var bmbtn=sheetview.findViewById(R.id.bmbtn) as Button
        when(type){
            modelnames.trainer->{
                var item=list[position] as trainermodel
                holder.itemView.tname.setText(item.name)
                Glide.with(context).load(item.imgurl).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.itemView.prog.visibility=View.GONE
                        return false

                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.itemView.prog.visibility=View.GONE
                        return false
                    }

                }).into(holder.itemView.timg)

                holder.itemView.tcard.setOnClickListener{
                    if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                sheetBehavior.peekHeight = 200*resources.displayMetrics.density.toInt()
                        Log.d("slidekey",item.name)
                        bmname.text = "Name: ${item.name}"
                        bmdesc.text = "Description: ${item.desc}"
                        bmgen.text = "Gender: ${item.gen}"
                        bmexp.text="Experience:${item.exp}"
                        bmprice.visibility=View.GONE
//                        bmprice.text="Price: ${item.price}"
                        bmbtn.setOnClickListener(imgclick)
                        Glide.with(context).load(item.imgurl).into(bmimg)
                    } else {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }

            }
            modelnames.eqip->{
                var item=list[position] as equipment
                holder.itemView.tname.setText(item.name)
                Glide.with(context).load(item.imgurl).listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.itemView.prog.visibility=View.GONE
                        return false

                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.itemView.prog.visibility=View.GONE
                        return false
                    }

                }).into(holder.itemView.timg)
                holder.itemView.tcard.setOnClickListener{
                    if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                sheetBehavior.peekHeight = 200*resources.displayMetrics.density.toInt()
                        Log.d("slidekey",item.name)
                        bmname.text = "Name: ${item.name}"
                        bmdesc.text = "Description: ${item.desc}"
//                        bmgen.text = "Gender: ${item.gen}"
//                        bmexp.text="Experience:${item.exp}"
                        bmgen.visibility=View.GONE
                        bmprice.visibility=View.GONE
                        bmgen.visibility=View.GONE
//                        bmprice.text="Price: ${item.price}"
                        bmbtn.setOnClickListener(imgclick)
                        Glide.with(context).load(item.imgurl).into(bmimg)
                    } else {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            }
            modelnames.fitm->{

                var item=list[position] as fitnessM
                holder.itemView.tname.setText(item.name)
                holder.itemView.timg.minimumHeight=(120*context.resources.displayMetrics.density).toInt()
                Glide.with(context).load(item.imgurl)
                    .listener(object :RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            holder.itemView.prog.visibility=View.GONE
                            return false

                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            holder.itemView.prog.visibility=View.GONE
                            return false
                        }

                    })
                    .into(holder.itemView.timg)
                    holder.itemView.tcard.setOnClickListener {
                        context.startActivity(Intent(context,fitnessitem::class.java).apply {
                            putExtra("name",item.name)
                        })
                    }
            }
            modelnames.fitmitem->{
                var item=list[position] as fitnessitems
                holder.itemView.tname.setText(item.name)
                Glide.with(context).load(item.imgurl).into(holder.itemView.timg)
                holder.itemView.tcard.setOnClickListener{
                    if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                sheetBehavior.peekHeight = 200*resources.displayMetrics.density.toInt()
                        Log.d("slidekey",item.name)
                        bmname.text = "Name: ${item.name}"
                        bmdesc.text = "Description: ${item.desc}"
//                        bmgen.text = "Gender: ${item.gen}"
//                        bmexp.text="Experience:${item.exp}"
                        bmgen.visibility=View.GONE
                        bmprice.visibility=View.GONE
//                        bmprice.visibility=View.GONE
                        bmprice.text="Price: ${item.price}"
                        bmbtn.setOnClickListener(imgclick)
                        Glide.with(context).load(item.imgurl).into(bmimg)
                    } else {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            }
            modelnames.gympack->{
                var item=list[position] as gympackage
                holder.itemView.gpname.setText(item.name)
//                holder.itemView.gprice.setText(item.price)
                val rnd = Random()
                var color=Color.parseColor(colorlist.get(rnd.nextInt(colorlist.size)))
//                 color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                holder.itemView.gpcard.setBackgroundColor(color)
                holder.itemView.gpcard.setOnClickListener{
                    if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                sheetBehavior.peekHeight = 200*resources.displayMetrics.density.toInt()
                        Log.d("slidekey",item.name)
                        bmname.text = "Name: ${item.name}"
                        bmdesc.text = "Description: ${item.desc}"
//                        bmgen.text = "Gender: ${item.gen}"
//                        bmexp.text="Experience:${item.exp}"
                        bmgen.visibility=View.GONE
                        bmprice.visibility=View.GONE
//                        bmprice.visibility=View.GONE
                        bmimg.visibility=View.GONE
                        bmprice.text="Price: ${item.price}"
                        bmbtn.setOnClickListener(imgclick)
//                        Glide.with(context).load(item.imgurl).into(bmimg)
                    } else {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
                //                Glide.with(context).load(item.imgurl).into(holder.itemView.timg
            }

        }
    }
    var imgclick= View.OnClickListener {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                sheetBehavior.peekHeight = 200*resources.displayMetrics.density.toInt()
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

}
class cardViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){

}