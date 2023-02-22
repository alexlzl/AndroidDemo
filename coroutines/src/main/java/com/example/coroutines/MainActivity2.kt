package com.example.coroutines



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*

class MainActivity2 : AppCompatActivity() {

    /**
     * 协程作用域
     * 该 作用域仅在 Activty 中 , 如果 Activity 被销毁 ,
     * 则 在 onDestory 生命周期函数中取消协程任务 ;
     */
    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainScope.launch {
            // 协程作用域, 在该代码块中执行协程任务
            // Dispatchers.IO 是协程任务调度器, 用于执行耗时操作
            withContext(Dispatchers.IO){
                Log.i("MainActivity", "withContext : 协程中执行耗时操作")
            }

            try{
                // 挂起函数, 可以不使用协程调度器
                delay(20000)
            }catch (e : Exception){
                Log.i("MainActivity", "中断挂起函数任务, 报异常:")
                e.printStackTrace()
            }

            // 主线程更新 UI
            Log.i("MainActivity", "GlobalScope : 主线程更新 UI")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 在 Activity 销毁前取消协程
        mainScope.cancel()
    }
}
