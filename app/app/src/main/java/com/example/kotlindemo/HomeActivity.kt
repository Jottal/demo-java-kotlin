package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class HomeActivity : AppCompatActivity() {
    private lateinit var param: String
    private lateinit var tvId: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        param = intent.extras!!.getString("id").toString()
        tvId = findViewById(R.id.tvId)

        tvId.setText(param)
    }
}