package com.example.madd.Client_Agent

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
import com.example.madd.Client_Agent.ClientAgents_data
import com.example.madd.R

class Client_Agent_MyAdapter(private val userList: ArrayList<ClientAgents_data>) :
    RecyclerView.Adapter<Client_Agent_MyAdapter.MyViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var viewClientClickListener: OnViewClientClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.client_agent_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = userList[position]
        Glide.with(holder.itemView)
            .load(currentItem.img)
            .into(holder.img)
        holder.Nname.text = holder.itemView.context.getString(R.string.agents_name, currentItem.name)
        holder.email.text = holder.itemView.context.getString(R.string.agents_email, currentItem.email)
        holder.phone.text = holder.itemView.context.getString(R.string.agents_phone, currentItem.phone)

        // set click listener on viewAgent button
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
        fun onViewClientClick(clientData: ClientAgents_data, key: String?)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        onItemClickListener = listener
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.agents_img)
        val Nname: TextView = itemView.findViewById(R.id.agents_name)
        val email: TextView = itemView.findViewById(R.id.agents_email)
        val phone: TextView = itemView.findViewById(R.id.agents_phone)
        // val description: TextView = itemView.findViewById(R.id.agents_Description)
        val viewClient: Button = itemView.findViewById(R.id.viewClientAgent)
    }
}
