package com.example.demo10

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.TouchDelegate
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.tv)
        setTouchDelegate(textView, 200)
        textView.setOnClickListener() {
            Toast.makeText(this@MainActivity, "test", Toast.LENGTH_SHORT).show()
            //使用兼容库就无需判断系统版本
            val hasWriteStoragePermission: Int = ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
                //拥有权限，执行操作
            } else {
                //没有权限，向用户请求权限
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1008
                )
            }
        }
    }

    fun setTouchDelegate(view: View, expandTouchWidth: Int) {
        val parentView: View = view.parent as View
        parentView.post(Runnable {
            val rect = Rect()
            view.getHitRect(rect) // view构建完成后才能获取，所以放在post中执行
            // 4个方向增加矩形区域
            rect.top -= expandTouchWidth
            rect.bottom += expandTouchWidth
            rect.left -= expandTouchWidth
            rect.right += expandTouchWidth
            parentView.touchDelegate = TouchDelegate(rect, view)
        })
    }

    /**
     * 一个或多个权限请求结果回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var hasAllGranted = true
        for (i in grantResults.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false
                //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        permissions[i]!!
                    )
                ) {
                    //解释原因，并且引导用户至设置页手动授权
                    AlertDialog.Builder(this)
                        .setMessage(
                            """
                        【用户选择了不在提示按钮，或者系统默认不在提示（如MIUI）。引导用户到应用设置页去手动授权,注意提示用户具体需要哪些权限】
                        获取相关权限失败:xxxxxx,将导致部分功能无法正常使用，需要到设置页面手动授权
                        """.trimIndent()
                        )
                        .setPositiveButton("去授权",
                            DialogInterface.OnClickListener { dialog, which -> //引导用户至设置页手动授权
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri: Uri =
                                    Uri.fromParts("package", applicationContext.packageName, null)
                                intent.data = uri
                                startActivity(intent)
                            })
                        .setNegativeButton("取消",
                            DialogInterface.OnClickListener { dialog, which ->
                                //引导用户手动授权，权限请求失败
                            }).setOnCancelListener(DialogInterface.OnCancelListener {
                            //引导用户手动授权，权限请求失败
                        }).show()
                } else {
                    //权限请求失败，但未选中“不再提示”选项
                    Toast.makeText(this,"权限失败",Toast.LENGTH_SHORT).show()
                }
                break
            }
        }
        if (hasAllGranted) {
            //权限请求成功
            Toast.makeText(this,"权限请求成功",Toast.LENGTH_SHORT).show()
        }
    }
}