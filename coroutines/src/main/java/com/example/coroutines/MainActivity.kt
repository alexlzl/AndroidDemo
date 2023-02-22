package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine

fun main() {
    GlobalScope.launch(context = Dispatchers.IO) {
        //延时一秒
        delay(1000)
        log("launch")
    }
    //主动休眠两秒，防止 JVM 过快退出
    Thread.sleep(2000)
    log("end")
}

private fun log(msg: Any?) = println("=======[${Thread.currentThread().name}] $msg")
class MainActivity : AppCompatActivity(),CoroutineScope {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = Job()
        loadDataFromUI()
    }
    lateinit var job: Job
    // CoroutineScope 的实现
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job



    override fun onDestroy() {
        super.onDestroy()
        // 当 Activity 销毁的时候取消该 Scope 管理的 job。
        // 这样在该 Scope 内创建的子 Coroutine 都会被自动的取消。
        job.cancel()
    }

    /*
     * 注意 coroutine builder 的 scope， 如果 activity 被销毁了或者该函数内创建的 Coroutine
     * 抛出异常了，则所有子 Coroutines 都会被自动取消。不需要手工去取消。
     */
    private fun loadDataFromUI() = launch { // <- 自动继承当前 activity 的 scope context，所以在 UI 线程执行
        val ioData = async(Dispatchers.IO) { // <- launch scope 的扩展函数，指定了 IO dispatcher，所以在 IO 线程运行
            // 在这里执行阻塞的 I/O 耗时操作
        }
        // 和上面的并非 I/O 同时执行的其他操作
        val data = ioData.await() // 等待阻塞 I/O 操作的返回结果
//        draw(data) // 在 UI 线程显示执行的结果
    }
    fun main() {
        // 开启一个主线程作用域的协程
        CoroutineScope(Dispatchers.Main).launch {
            // getUserInfo 是一个 suspend 函数，且在 IO 线程中
            val userInfo = getUserInfo()
            // 网络请求以同步的方式返回了，又回到了主线程，这里操作了 UI
//            tvName.text = userInfo.name
        }
    }

    // withContext 运行在 IO 线程
    suspend fun getUserInfo() = withContext(Dispatchers.IO){
        // 这里的网络请求结果在 callback 中
        // 所以借助 suspendCoroutine 函数同步使用 callback 返回值
        // 返回值为 UserInfo 对象
        // 还有 suspendCancellableCoroutine 函数也可以了解下
        suspendCoroutine<UserInfo> {
            // 发起网络请求
//            HttpRequest().url("/test/getUserInfo").callback(object : HttpCallback<UserInfo?>() {
//                override fun onError(request: Request?, throwable: Throwable) {
//                    it.resumeWithException(throwable)
//                }
//
//                override fun onResponse(userInfo: UserInfo?) {
//                    it.resume(userInfo)
//                }
//            })
        }
    }
    class UserInfo{

    }
}