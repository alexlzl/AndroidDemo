package com.example.yanxu.todaysteps.activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.yanxu.todaysteps.R;
import com.example.yanxu.todaysteps.util.StepUtil;
import com.example.yanxu.todaysteps.util.StepService;

public class MainActivity extends AppCompatActivity {

    private TextView mTvSteps;
    private Button mBtRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 1);
            } else {
                Toast toast = Toast.makeText(this, "已授权", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                initStepService();
                initView();
                initData();
            }
        }

    }

    /**
     * 初始化计步服务
     * 注：因初始化需要过程，正常项目中，初始化应该放在进入到主界面之前的activity中，比如闪屏页中进行初始化
     * 因此本demo在第一次安装时会提示"手机暂不支持计步功能"，杀死进程再次打开即可正常显示
     */
    private void initStepService() {
//        Intent nfIntent = new Intent(this, MainActivity.class);
//        Notification.Builder builder = new Notification.Builder(this.getApplicationContext())
//                .setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
//                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
//                .setContentTitle(getResources().getString(R.string.app_name))
//                .setContentText("正在上传...") // 设置上下文内容
//                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间


        Intent intent = new Intent(this, StepService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel = new NotificationChannel("CHANNEL_ONE_ID", "CHANNEL_ONE_NAME", NotificationManager.IMPORTANCE_MIN);
//            notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
//            notificationChannel.setShowBadge(false);//是否显示角标
//            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            manager.createNotificationChannel(notificationChannel);
//            builder.setChannelId("CHANNEL_ONE_ID");
//            Notification notification = builder.build(); // 获取构建好的Notification
//            notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    /**
     * 初始化view
     */
    private void initView() {
        mTvSteps = findViewById(R.id.tv_steps);
        mBtRefresh = findViewById(R.id.bt_refresh);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (!StepUtil.isSupportStep(this)) {
            mTvSteps.setText("手机暂不支持计步功能");
            return;
        }
        mBtRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String steps = StepUtil.getTodayStep(MainActivity.this) + "步";
                mTvSteps.setText(steps);
            }
        });
    }

}
