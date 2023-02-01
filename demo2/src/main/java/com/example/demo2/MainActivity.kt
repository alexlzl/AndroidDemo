package com.example.demo2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textViewOne = findViewById<TextView>(R.id.show_time_tv_one)
        val textViewTwo = findViewById<TextView>(R.id.show_time_tv_two)
        val textViewThree = findViewById<TextView>(R.id.show_time_tv_three)
        val textViewFour = findViewById<TextView>(R.id.show_time_tv_four)
        val textViewFive = findViewById<TextView>(R.id.show_time_tv_five)
        val textViewSix = findViewById<TextView>(R.id.show_time_tv_six)
        val textViewSeven = findViewById<TextView>(R.id.show_time_tv_seven)
        val showTimeOne = TimeTransformUtil.transform(1000 * 60 * 60)//60分钟
        val showTimeTwo = TimeTransformUtil.transform(1000 * 60 * 60+1000*60)//61分钟
        val showTimeThree = TimeTransformUtil.transform(1000 * 60 * 59)//59分钟
        val showTimeFour = TimeTransformUtil.transform(1000 * 60 * 59+1000 * 30)//59.5分钟
        val showTimeFive = TimeTransformUtil.transform(1000 * 60 * 120)//120分钟
        val showTimeSix = TimeTransformUtil.transform(1000 * 60 * 65+500)//65.5分钟
        val showTimeSeven = TimeTransformUtil.transform(1000 * 60 * 60+1000 * 60 * 59+1000 * 30)//1小时59.5分钟
        textViewOne.text = "$showTimeOne(60分钟)"
        textViewTwo.text = "$showTimeTwo(61分钟)"
        textViewThree.text = "$showTimeThree(59分钟)"
        textViewFour.text = "$showTimeFour(59.5分钟)"
        textViewFive.text = "$showTimeFive(120分钟)"
        textViewSix.text = "$showTimeSix(65.5分钟)"
        textViewSeven.text = "$showTimeSeven(1小时59.5分钟)"
    }
}