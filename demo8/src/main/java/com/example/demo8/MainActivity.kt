package com.example.demo8

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this,  com.example.mylibrary.Test.test(), Toast.LENGTH_SHORT).show()
         val name=findViewById<TextView>(R.id.name)
        if(isXiaomi()){
            name.text=XIAOMI

        }else if(isHuawei()){
            name.text= HUAWEI
        }
        name.setOnClickListener{
            startService(Intent(this@MainActivity,ForegroundCoreService::class.java))
        }
    }
}