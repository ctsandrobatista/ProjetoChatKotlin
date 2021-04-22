package com.sbproducao.projetochatkotlin.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sbproducao.projetochatkotlin.R



class ChatActivity : AppCompatActivity() {
    private var sairChat: ImageButton? = null
    private var listView: ListView? = null
    private var arrayAdapter: ArrayAdapter<String?>? = null
    private val list_of_rooms = ArrayList<String?>()
    private var name: String? = null
    private val root = FirebaseDatabase.getInstance().reference.root
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        sairChat = findViewById(R.id.btn_sair_chat)
        listView = findViewById(R.id.listViewChat)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list_of_rooms)
        listView!!.adapter = arrayAdapter
        request_user_name()

        root.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val set: MutableSet<String?> = HashSet()
                val i: Iterator<*> = dataSnapshot.children.iterator()
                while (i.hasNext()) {
                    set.add((i.next() as DataSnapshot).key)
                }
                list_of_rooms.clear()
                list_of_rooms.addAll(set)
                list_of_rooms.size
                arrayAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        listView!!.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val intent = Intent(applicationContext, RoomActivity::class.java)
                intent.putExtra("room_name", (view as TextView).text.toString())
                intent.putExtra("user_name", name)
                startActivity(intent)
            }
    }

    private fun request_user_name() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Digite seu nome!")
        val input_field = EditText(this)
        builder.setView(input_field)
        builder.setPositiveButton("OK") { dialogInterface, i -> name = input_field.text.toString() }
        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            dialogInterface.cancel()
            request_user_name()
        }
        builder.show()
    }

    fun sairChat(view: View?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}