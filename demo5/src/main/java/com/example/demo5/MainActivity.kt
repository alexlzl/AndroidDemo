package com.example.demo5


import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    //不实现SensorEventListener接口在真机上会闪退
    var mSensorManager //管理器实例
            : SensorManager? = null
    var stepCounter //传感器
            : Sensor? = null
    var mSteps //截止当天0点时步数/重启手机到当前为止的步数
            = 0f
    var steps //显示步数
            : TextView? = null
    var time //显示时间
            : TextView? = null
    private var dbOpenHelper //定义DBOpenHelper
            : DBOpenHelper? = null
    var btn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getoperation()
        // 获取SensorManager管理器实例
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        dbOpenHelper = DBOpenHelper(this@MainActivity, "db_step", null, 1)
        steps = findViewById<View>(R.id.steps) as TextView
//        time = findViewById<View>(R.id.time) as TextView
        btn = findViewById(R.id.btn)
        // 获取计步器sensor
        stepCounter = mSensorManager!!.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepCounter != null) {
            // 如果sensor找到，则注册监听器
            mSensorManager!!.registerListener(
                this as SensorEventListener,
                stepCounter,
                SensorManager.SENSOR_DELAY_GAME
            )
        } else {
            Log.e("hemeiwolong", "no step counter sensor found")
        }
        btn?.setOnClickListener {
            getoperation()
        }
    }

    //步数变化时
    override fun onSensorChanged(event: SensorEvent) {
        val cursor =
            dbOpenHelper!!.readableDatabase.query("tb_step", null, null, null, null, null, null)

        //获取今天的日期，统一格式为yyyy/MM/dd
        Toast.makeText(this,"回调：${event.values[0]}",Toast.LENGTH_SHORT).show()
        val strdate = SimpleDateFormat("yyyy/MM/dd").format(Date())
        if (cursor.count == 0) {   //如果计步表为空，则把现在为止的总步数加入数据库
            insertData(dbOpenHelper!!.readableDatabase, strdate, event.values[0].toString())
            mSteps = event.values[0]
        } else {
            if (cursor.moveToLast()) {
                if (event.values[0] < cursor.getString(2).toFloat()) {  //说明手机重启过
                    //删除旧的所有步数记录，把现在的总步数加入数据库，注意表名打错的话会闪退
                    dbOpenHelper!!.readableDatabase.delete("tb_step", null, null)
                    insertData(dbOpenHelper!!.readableDatabase, strdate, event.values[0].toString())
                    if (cursor.moveToLast()) {
                        mSteps = cursor.getString(2).toFloat()
                    }
                } else {    //如果今天没重启过，则取最新的数据作为今天计步的起始值
                    mSteps = cursor.getString(2).toFloat()
                }
            }
        }

        //今天所走步数 = 目前的总步数 - 今天0点为止的总步数，如果今天重启过，只记录重启到现在所走的步数
        val showSteps = event.values[0] - mSteps
        steps!!.text = "你已经走了" + event.values[0] + "步"

        //到了0点，把到目前为止所走的总步数加入数据库
        val time = "00:00:00"
        if (time == SimpleDateFormat("HH:mm:ss").format(Date())) {
            insertData(dbOpenHelper!!.readableDatabase, strdate, event.values[0].toString())
            mSteps = event.values[0]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    private fun getoperation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Log.d("TAG", "[权限]" + "ACTIVITY_RECOGNITION 未获得")
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "未获取权限",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("TAG", "未获取权限=======")
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACTIVITY_RECOGNITION
                    )
                ) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                    Log.e("TAG", "[权限]" + "ACTIVITY_RECOGNITION 以拒绝，需要进入设置权限界面打开")
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                        1
                    )
                    Log.e("TAG", "[权限]" + "ACTIVITY_RECOGNITION 未彻底拒绝拒绝，请求用户同意")
                }
                //                return;
            } else {
                Log.e("TAG", "已经获取权限=======")
                Toast.makeText(
                    this@MainActivity,
                    "[权限]\" + \"ACTIVITY_RECOGNITION ready",
                    Toast.LENGTH_SHORT
                ).show()
//                register()
            }
        } else {
        }
    }

    private fun register(){
        if (stepCounter != null) {
            // 如果sensor找到，则注册监听器
            mSensorManager!!.registerListener(
                this as SensorEventListener,
                stepCounter,
                SensorManager.SENSOR_DELAY_GAME
            )
        } else {
            Log.e("hemeiwolong", "no step counter sensor found")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 申请成功
                    Log.d("TAG", "[权限]" + "ACTIVITY_RECOGNITION 申请成功")
                    Toast.makeText(
                        this@MainActivity,
                        "[权限]\" + \"ACTIVITY_RECOGNITION 申请成功",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // 申请失败
                    Log.d("TAG", "[权限]" + "ACTIVITY_RECOGNITION 申请失败")
                    Toast.makeText(
                        this@MainActivity,
                        "[权限]\" + \"ACTIVITY_RECOGNITION 申请失败",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    //向数据库添加记录
    private fun insertData(sqLiteDatabase: SQLiteDatabase, day: String, steps: String) {
        val contentValues = ContentValues()
        contentValues.put("day", day)
        contentValues.put("steps", steps)
        sqLiteDatabase.insert("tb_step", null, contentValues)
    }
}