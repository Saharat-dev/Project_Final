package com.example.firebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONArray
import org.json.JSONObject


class ShowData : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_show_data, container, false)
        val mRootRef = FirebaseDatabase.getInstance().reference
        val mMessagesRef = mRootRef.child("recipes")

        mMessagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val data = JSONArray()
                val recyclerView: RecyclerView = view.findViewById(R.id.recyLayout)
                val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!.baseContext)
                recyclerView.layoutManager = layoutManager
                for (ds in dataSnapshot.children) {

                    val jObject = JSONObject()

                    val title = ds.child("title").getValue(String::class.java)!!

                    val description = ds.child("description").getValue(String::class.java)!!
                    val image = ds.child("image").getValue(String::class.java)!!

                    jObject.put("key",ds.key)
                    jObject.put("title",title)
                    jObject.put("description",description)
                    jObject.put("image",image)

                    data.put(jObject)

                }

                val adapter = MyRecyclerAdapter(activity!!.baseContext,data)

                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return view
    }


}
