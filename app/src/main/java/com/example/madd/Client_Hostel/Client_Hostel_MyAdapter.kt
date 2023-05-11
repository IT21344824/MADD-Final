package com.example.madd.Client_Hostel

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madd.Client_Hostel.ClientHostel_data
import com.example.madd.R

class Client_Hostel_MyAdapter(private val userList: ArrayList<ClientHostel_data>) :
    RecyclerView.Adapter<Client_Hostel_MyAdapter.MyViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var viewClientClickListener: OnViewClientClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.client_hostel_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = userList[position]
        Glide.with(holder.itemView)
            .load(currentItem.img)
            .into(holder.img)
        holder.Nname.text = holder.itemView.context.getString(R.string.agents_name, currentItem.name)
        holder.phone.text = holder.itemView.context.getString(R.string.agents_phone, currentItem.phone)

        // set click listener on viewHostel button
        holder.viewClient.setOnClickListener {
            onItemClickListener?.onItemClick(position)
            viewClientClickListener?.onViewClientClick(currentItem, currentItem.key)
        }

    }


    fun setViewClientClickListener(listener: OnViewClientClickListener?) {
        viewClientClickListener = listener
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    interface OnViewClientClickListener {
        fun onViewClientClick(clientData: ClientHostel_data, key: String?)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        onItemClickListener = listener
    }
   //MyViewHolder class is an inner class that holds references to the views in the client_hostel_item layout
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.Hos_img)
        val Nname: TextView = itemView.findViewById(R.id.Hos_name)
        val phone: TextView = itemView.findViewById(R.id.Hos_phone)
        val viewClient: Button = itemView.findViewById(R.id.viewClientHos)
    }
}
