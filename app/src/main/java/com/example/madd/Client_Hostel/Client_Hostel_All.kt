package com.example.madd.Client_Hostel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madd.Client_Agent.ClientAgents_data
import com.example.madd.Client_Agent.Client_Agent_MyAdapter
import com.example.madd.Client_Agent.Client_Agent_single
import com.example.madd.R
import com.example.madd.databinding.ActivityClientAgentAllBinding
import com.example.madd.databinding.ActivityClientHostelAllBinding
import com.google.firebase.database.*

class Client_Hostel_All : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference // Firebase Realtime Database location
    private lateinit var userRecyclerView: RecyclerView //display a list of data.
    private lateinit var userArrayList: ArrayList<ClientHostel_data> //hold the data to be displayed in the RecyclerView
    private lateinit var binding: ActivityClientHostelAllBinding //bind views in the activity
    private lateinit var myAdapter: Client_Hostel_MyAdapter //handle the creation of the views

      //sets up the UI of the activity and prepares it to display data from Firebase.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientHostelAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRecyclerView = binding.HosList
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        myAdapter = Client_Hostel_MyAdapter(userArrayList)
        userRecyclerView.adapter = myAdapter
        getHosData()
    }
         //etrieve data from the Firebase Realtime Database and update the UI with the new data
    private fun getHosData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Hostels")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    //retrieve data from the database and update the list of hostels displayed in the RecyclerView
                    userArrayList.clear()
                    for (HosSnapshot in snapshot.children) {
                        val Hostels = HosSnapshot.getValue(ClientHostel_data::class.java)
                        Hostels?.key = HosSnapshot.key
                        userArrayList.add(Hostels!!)
                    }
                    myAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
                    //The Intent starts the Client_Hostel_singel activity and passes the data to it
        myAdapter.setViewClientClickListener(object : Client_Hostel_MyAdapter.OnViewClientClickListener {
            override fun onViewClientClick(clientData: ClientHostel_data, key: String?) {
                val intent = Intent(this@Client_Hostel_All, Client_Hostel_singel::class.java)

                intent.putExtra("phone", clientData.phone as? String)
                intent.putExtra("img", clientData.img as? String)
                intent.putExtra("name", clientData.name as? String)
                intent.putExtra("description", clientData.description as? String)
                intent.putExtra("key", key)
                Log.d("Client-Hoskey", "key: ${key}")
                startActivity(intent)
            }
        })
    }


}