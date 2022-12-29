package com.example.androiddemo

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val et=findViewById<EditText>(R.id.et)
        //输入总长度15位，小数2位
        //输入总长度15位，小数2位
        et.addTextChangedListener(DecimalInputTextWatcher(et, Int.MAX_VALUE, 1))

    }
}