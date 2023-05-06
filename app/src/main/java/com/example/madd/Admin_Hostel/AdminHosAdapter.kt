package com.example.madd.Admin_Hostel

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.madd.Admin_Agent.AdminAgents
import com.example.madd.R
import com.google.firebase.database.FirebaseDatabase

class AdminHosAdapter( private val userList: ArrayList<AdminHostels>):
RecyclerView.Adapter<AdminHosAdapter.MyViewHolder>(){

    private var onItemClickListener: OnItemClickListener? = null
    private var viewClientClickListener: OnViewAdminClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_hostel_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = userList[position]
        Glide.with(holder.itemView)
            .load(currentItem.img)
            .into(holder.img)
        holder.Name.text = holder.itemView.context.getString(R.string.agents_name, currentItem.name)
        holder.phone.text = holder.itemView.context.getString(R.string.agents_phone, currentItem.phone)

        // set click listener on viewClient button
        holder.viewHost.setOnClickListener {
            onItemClickListener?.onItemClick(position)
            viewClientClickListener?.onViewAdminClick(currentItem, currentItem.key)
        }

        holder.Delete_Hostel.setOnClickListener {
            val key = currentItem.key
            if (key != null) {
                val alertDialog = AlertDialog.Builder(holder.itemView.context)
                alertDialog.setTitle("Delete Hostel")
                alertDialog.setMessage("Are you sure you want to delete this Hostel?")
                alertDialog.setPositiveButton("Yes") { _, _ ->
                    val dbRef = FirebaseDatabase.getInstance().getReference("Hostels").child(key)
                    dbRef.removeValue()
                    Toast.makeText(holder.itemView.context, "Hostels deleted successfully", Toast.LENGTH_SHORT).show()
                }
                alertDialog.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }
        }

    }


    fun setViewAdminClickListener(listener: OnViewAdminClickListener?) {
        viewClientClickListener = listener
    }


    override fun getItemCount(): Int {
        return userList.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnViewAdminClickListener {
        fun onViewAdminClick(clientData: AdminHostels, key: String?)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        onItemClickListener = listener
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val img: ImageView = itemView.findViewById(R.id.Admin_Hoss_img)
        val Name: TextView = itemView.findViewById(R.id.Admin_Hoss_name)
        val phone: TextView = itemView.findViewById(R.id.Admin_Hoss_phone)
        // val description: TextView = itemView.findViewById(R.id.agents_Description)
        val viewHost: Button = itemView.findViewById(R.id.Admin_viewHos)
        val Delete_Hostel: Button = itemView.findViewById(R.id.Hos_Delete)
    }

}