package com.example.madd.Admin_Agent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madd.Client_Agent.ClientAgents_data
import com.example.madd.Client_Agent.Client_Agent_MyAdapter
import com.example.madd.Client_Agent.Client_Agent_single
import com.example.madd.R

import com.example.madd.databinding.ActivityAdminAllAgentsBinding
import com.google.firebase.database.*

class AdminAllAgents : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<AdminAgents>
    private lateinit var binding: ActivityAdminAllAgentsBinding
    private lateinit var myAdapter: AdminMyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAllAgentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRecyclerView = binding.AdminAgentList
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        myAdapter = AdminMyAdapter(userArrayList)
        userRecyclerView.adapter = myAdapter
        getAgentData()


        val agentsAddNew = findViewById<Button>(R.id.add_agent)
        agentsAddNew.setOnClickListener {
            val intent = Intent(this, Admin_Agent_Add::class.java)
            startActivity(intent)
        }


    }

    private fun getAgentData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Agents")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userArrayList.clear()
                    for (agentSnapshot in snapshot.children) {
                        val agent = agentSnapshot.getValue(AdminAgents::class.java)
                        agent!!.key = agentSnapshot.key // set the key here
                        userArrayList.add(agent)
                    }
                    myAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })


        myAdapter.setViewAdminClickListener(object : AdminMyAdapter.OnViewAdminClickListener {

            override fun onViewAdminClick(clientData: AdminAgents, key: String?) {
                val intent = Intent(this@AdminAllAgents, Admin_Agent_single::class.java)

                intent.putExtra("email", clientData.email as? String)
                intent.putExtra("phone", clientData.phone as? String)
                intent.putExtra("img", clientData.img as? String)
                intent.putExtra("name", clientData.name as? String)
                intent.putExtra("description", clientData.description as? String)
                intent.putExtra("key", key ?: "") // set key to empty string if it's null
                Log.d("Agentkey", "key: ${key}")
                startActivity(intent)
            }

        })
    }
}