package com.trickyandroid.mvvmdataloading.ui.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.trickyandroid.mvvmdataloading.R
import com.trickyandroid.mvvmdataloading.utils.AnimationUtils
import com.trickyandroid.mvvmdataloading.data.models.DataModel
import java.lang.Exception

class DataAdapter(var context: Context, var dataList: ArrayList<DataModel>) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    internal var view: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.data_adapter, parent, false)
        return ViewHolder(view!!)
    }


    override fun onBindViewHolder(holder: DataAdapter.ViewHolder, position: Int) {
        val model: DataModel = dataList[position]
        try {
            holder.tvTitle?.text = model.title.toString()
            holder.tvDesc?.text = model.desc.toString()
            if (!model.imgUrl?.toString().equals("null")) {
                Picasso.get().load(model.imgUrl.toString())
                    .into(holder?.ivThumbnail)
            } else if (model.imgUrl?.toString().equals("null")) {
                holder?.ivThumbnail?.setColorFilter(Color.parseColor("#FF8080"))
            }

            holder.cardView?.setOnClickListener { v ->
                Toast.makeText(context, "" + model.title.toString(), Toast.LENGTH_SHORT).show()
            }

            AnimationUtils.animateSunblind(holder, true)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


    override fun getItemCount(): Int = dataList.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var tvTitle: TextView? = null
        internal var tvDesc: TextView? = null
        internal var ivThumbnail: ImageView? = null
        internal var cardView: CardView? = null
        internal var lineCode: View? = null

        init {
            tvTitle = itemView.findViewById<View>(R.id.tvTitle) as TextView
            tvDesc = itemView.findViewById<View>(R.id.tvDesc) as TextView
            ivThumbnail = itemView.findViewById<View>(R.id.ivThumbnail) as ImageView
            cardView = itemView?.findViewById<View>(R.id.cardView) as CardView
            cardView?.setBackgroundResource(R.drawable.card_bg)
            lineCode = itemView?.findViewById<View>(R.id.lineCode) as View
            lineCode?.setBackgroundColor(Color.TRANSPARENT)
        }

    }


}