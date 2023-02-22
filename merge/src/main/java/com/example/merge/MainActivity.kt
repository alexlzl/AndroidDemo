package com.example.merge

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text=findViewById<TextView>(R.id.one)
        text.text="textadd"
//        val rl=findViewById<LinearLayout>(R.id.pp)
        val layout: View = layoutInflater.inflate(R.layout.include_layout, null)
        val head = layout.findViewById<LinearLayout>(R.id.root)
//设置背景图片
        head.setBackgroundColor(resources.getColor(android.R.color.holo_red_dark))
    }
}