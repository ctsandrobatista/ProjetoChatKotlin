package com.sbproducao.projetochatkotlin.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sbproducao.projetochatkotlin.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    fun entrarChat(view: View){
        startActivity(Intent(this, ChatActivity::class.java))
        finish()
    }
}