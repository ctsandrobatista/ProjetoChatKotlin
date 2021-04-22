package com.sbproducao.projetochatkotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.*
import com.sbproducao.projetochatkotlin.R


class RoomActivity : AppCompatActivity() {
    private lateinit var btn_send_msg: Button
    private lateinit var input_msg: EditText
    private lateinit var chat_conversation: TextView
    private lateinit var user_name: String
    private lateinit var room_name: String
    private lateinit var root: DatabaseReference
    private lateinit var temp_key: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        btn_send_msg = findViewById(R.id.btn_send)
        input_msg = findViewById(R.id.input_msg)
        chat_conversation = findViewById(R.id.tv_api)
        user_name = intent.extras!!["user_name"].toString()
        room_name = intent.extras!!["room_name"].toString()
        title = " Room - $room_name"



        root = FirebaseDatabase.getInstance().reference.child(room_name!!)
        btn_send_msg!!.setOnClickListener {
            val map: Map<String, Any> =
                HashMap()
            temp_key = root!!.push().key.toString()
            root!!.updateChildren(map)
            val message_root = root!!.child(temp_key!!)
            val map2: MutableMap<String, Any> =
                HashMap()
            map2["name"] = user_name!!
            map2["msg"] = input_msg!!.text.toString()
            input_msg.text.clear()
            message_root.updateChildren(map2)


        }
        root!!.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                append_chat_conversation(dataSnapshot)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                append_chat_conversation(dataSnapshot)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private var chat_msg: String? = null
    private var chat_user_name: String? = null
    private fun append_chat_conversation(dataSnapshot: DataSnapshot) {
        val i: Iterator<*> = dataSnapshot.children.iterator()
        while (i.hasNext()) {
            chat_msg = (i.next() as DataSnapshot).value as String?
            chat_user_name = (i.next() as DataSnapshot).value as String?
            chat_conversation!!.append("$chat_user_name : $chat_msg \n")
        }
    }
    fun backMsg(view: View){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}