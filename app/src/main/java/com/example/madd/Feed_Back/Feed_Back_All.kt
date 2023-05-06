package com.example.madd.Feed_Back



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madd.Admin_Agent.Admin_Agent_Add
import com.example.madd.Client_Agent.ClientAgents_data
import com.example.madd.R
import com.example.madd.databinding.ActivityFeedBackAllBinding
import com.google.firebase.database.*


class Feed_Back_All : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Feed_Back_Data>
    private lateinit var binding: ActivityFeedBackAllBinding
    private lateinit var myAdapter: Feed_Back_MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBackAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRecyclerView = binding.HintFeedList
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        myAdapter = Feed_Back_MyAdapter(userArrayList)
        userRecyclerView.adapter = myAdapter
        getFeedBackData()

        val feedBackAddNew = findViewById<Button>(R.id.add_feeddback)
        feedBackAddNew.setOnClickListener {
            val intent = Intent(this, FeedBackaAdd::class.java)
            startActivity(intent)
        }
    }

    private fun getFeedBackData() {
        dbRef = FirebaseDatabase.getInstance().getReference("FeedBacks")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    userArrayList.clear()
                    for (feedbackSnapshot in snapshot.children) {
                        val feedback = feedbackSnapshot.getValue(Feed_Back_Data::class.java)
                        feedback?.key = feedbackSnapshot.key
                        userArrayList.add(feedback!!)
                    }
                    myAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        myAdapter.setViewClientClickListener(object : Feed_Back_MyAdapter.OnViewClientClickListener {
            override fun onViewClientClick(feedbackData: Feed_Back_Data, feedbackKey: String?) {
                val intent = Intent(this@Feed_Back_All, Feed_Back_Update::class.java)

                intent.putExtra("email", feedbackData.email as? String)
                intent.putExtra("name", feedbackData.name as? String)
                intent.putExtra("message", feedbackData.message as? String)
                intent.putExtra("key",feedbackKey)
                Log.d("FeedbackKey", "key: ${feedbackKey}")
                startActivity(intent)
            }

        })
        myAdapter.setOnDeleteClientClickListener(object : Feed_Back_MyAdapter.OnDeleteClientClickListener {
            override fun onDeleteClientClick(feedbackData: Feed_Back_Data, feedbackKey: String?) {
                if (feedbackKey != null) {
                    val dialogBuilder = AlertDialog.Builder(this@Feed_Back_All)
                    dialogBuilder.setTitle("Delete Feedback")
                    dialogBuilder.setMessage("Are you sure you want to delete this feedback?")
                    dialogBuilder.setPositiveButton("Yes") { dialog, which ->
                        dbRef.child(feedbackKey).removeValue()
                        Toast.makeText(
                            this@Feed_Back_All,
                            "Feedback deleted successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    dialogBuilder.setNegativeButton("No") { dialog, which -> }
                    val dialog = dialogBuilder.create()
                    dialog.show()
                }
            }
        })
    }


}




