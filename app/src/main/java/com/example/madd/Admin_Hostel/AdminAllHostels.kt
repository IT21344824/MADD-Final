package com.example.madd.Admin_Hostel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madd.Admin_Agent.AdminAgents
import com.example.madd.R
import com.example.madd.databinding.ActivityAdminAllHostelsBinding
import com.google.firebase.database.*

class AdminAllHostels : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<AdminHostels>
    private lateinit var binding: ActivityAdminAllHostelsBinding
    private lateinit var myAdapter: AdminHosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAllHostelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRecyclerView = binding.AdminHosList
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        myAdapter = AdminHosAdapter(userArrayList)
        userRecyclerView.adapter = myAdapter
        getHostData()

        val HosAddNew = findViewById<Button>(R.id.add_Hos)
        HosAddNew.setOnClickListener {
            val intent = Intent(this, Admin_Hostel_Add::class.java)
            startActivity(intent)
        }
    }
    private fun getHostData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Hostels")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userArrayList.clear()
                    for (HosSnapshot in snapshot.children) {
                        val Hostels = HosSnapshot.getValue(AdminHostels::class.java)
                        Hostels!!.key = HosSnapshot.key // set the key here
                        userArrayList.add(Hostels)
                    }
                    myAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })


        myAdapter.setViewAdminClickListener(object : AdminHosAdapter.OnViewAdminClickListener {


            override fun onViewAdminClick(clientData: AdminHostels, key: String?) {
                val intent = Intent(this@AdminAllHostels, Admin_Hostel_single::class.java)

                intent.putExtra("phone", clientData.phone as? String)
                intent.putExtra("img", clientData.img as? String)
                intent.putExtra("name", clientData.name as? String)
                intent.putExtra("description", clientData.description as? String)
                intent.putExtra("key", key ?: "") // set key to empty string if it's null
                Log.d("Hoskey", "key: ${key}")
                startActivity(intent)
            }


        })
    }
}
