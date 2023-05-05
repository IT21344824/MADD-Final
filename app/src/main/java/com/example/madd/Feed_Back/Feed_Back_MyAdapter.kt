package com.example.madd.Feed_Back


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madd.R


class Feed_Back_MyAdapter(private val context: ArrayList<Feed_Back_Data>) :

    RecyclerView.Adapter<Feed_Back_MyAdapter.MyViewHolder>() {


    private lateinit var deleteClientClickListener: Feed_Back_MyAdapter.OnDeleteClientClickListener
    private var onItemClickListener: OnItemClickListener? = null
    private var viewClientClickListener: OnViewClientClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.feed_back_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = context[position]

        holder.name.text = holder.itemView.context.getString(R.string.agents_name, currentItem.name)
        holder.email.text = holder.itemView.context.getString(R.string.agents_email, currentItem.email)
        holder.message.text = "message:{currentItem.message}"


        // set click listener on viewAgent button
        holder.viewClient.setOnClickListener {
            onItemClickListener?.onItemClick(position)
            viewClientClickListener?.onViewClientClick(currentItem, currentItem.key)
        }

    }
    interface OnDeleteClientClickListener {
        fun onDeleteClientClick(feedbackData: Feed_Back_Data, feedbackKey: String?)
    }

    fun setOnDeleteClientClickListener(listener: OnDeleteClientClickListener) {
        this.deleteClientClickListener = listener
    }


    fun setViewClientClickListener(listener: OnViewClientClickListener?) {
        viewClientClickListener = listener
    }

    override fun getItemCount(): Int {
        return context.size
    }
    interface OnViewClientClickListener {
        fun onViewClientClick(feedbackData: Feed_Back_Data, feedbackKey: String?)
    }



    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        onItemClickListener = listener
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val name: TextView = itemView.findViewById(R.id.feedb_name)
        val email: TextView = itemView.findViewById(R.id.feedb_email)
        val message: TextView = itemView.findViewById(R.id.feedb_message)
        // val description: TextView = itemView.findViewById(R.id.agents_Description)
        val viewClient: Button = itemView.findViewById(R.id.viewClientFeedBack)
    }
}


