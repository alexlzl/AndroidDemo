package com.example.demo9

import android.database.Cursor
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      Toast.makeText(this,  com.example.mylibrary.Test.test(),Toast.LENGTH_SHORT).show()
        val isSupport = FeatureParser.getBoolean("support_steps_provider", false)
        val name = findViewById<TextView>(R.id.name)
        if (isSupport) {
            name.text = "support_steps_provider"
        } else {
            name.text = "not_support_steps_provider"
        }
        val p = Steps.BEGIN_TIME +
                ">'1675872000000' AND " +
                Steps.END_TIME +
                "< '1675958400000' "
        val start = Steps.BEGIN_TIME + "=20230206" + "," + Steps.END_TIME + "=20230207"
        name.setOnClickListener {
            getAllSteps(p, arrayOf("1675872000000", "1675958400000"))
        }
    }

    private fun getAllSteps(selection: String?, args: Array<String?>?): LinkedList<Step>? {
        val startTime = 20230206
        val endTime = 20230207
        val projection = arrayOf(
            Steps.ID,
            Steps.BEGIN_TIME,
            Steps.END_TIME,
            Steps.MODE,
            Steps.STEPS
        )
        val resolver = contentResolver
        val steps: LinkedList<Step>? = LinkedList<Step>()
        val cursor: Cursor? = resolver.query(
            Steps.CONTENT_URI, projection, selection, null,
            Steps.DEFAULT_SORT_ORDER
        )
        if (cursor?.moveToFirst() == true) {
            do {
                val s = Step(
                    cursor?.getInt(0), cursor.getLong(1), cursor.getLong(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
                )
                steps?.add(s)
            } while (cursor.moveToNext())
        }
        return steps
    }

}