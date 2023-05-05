package com.example.madd.Client_Agent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madd.R
import com.example.madd.Client_Agent.Client_Agent_MyAdapter
import com.example.madd.databinding.ActivityClientAgentAllBinding
import com.google.firebase.database.*

class Client_Agent_All : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<ClientAgents_data>
    private lateinit var binding: ActivityClientAgentAllBinding
    private lateinit var myAdapter: Client_Agent_MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientAgentAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRecyclerView = binding.agentList
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        myAdapter = Client_Agent_MyAdapter(userArrayList)
        userRecyclerView.adapter = myAdapter
        getAgentData()
    }

    private fun getAgentData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Agents")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    userArrayList.clear()
                    for (agentSnapshot in snapshot.children) {
                        val agent = agentSnapshot.getValue(ClientAgents_data::class.java)
                        agent?.key = agentSnapshot.key
                        userArrayList.add(agent!!)
                    }
                    myAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        myAdapter.setViewClientClickListener(object : Client_Agent_MyAdapter.OnViewClientClickListener {
            override fun onViewClientClick(clientData: ClientAgents_data, key: String?) {
                val intent = Intent(this@Client_Agent_All, Client_Agent_single::class.java)

                intent.putExtra("email", clientData.email as? String)
                intent.putExtra("phone", clientData.phone as? String)
                intent.putExtra("img", clientData.img as? String)
                intent.putExtra("name", clientData.name as? String)
                intent.putExtra("description", clientData.description as? String)
                intent.putExtra("key", key)
                Log.d("Client-Agentkey", "key: ${key}")
                startActivity(intent)
            }
        })
    }


}