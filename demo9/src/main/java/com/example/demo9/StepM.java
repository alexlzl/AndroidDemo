package com.example.demo9;

/**
 * @ Description:
 * @ Author: LiuZhouLiang
 * @ Time：2023/2/7 9:48 上午
 */
public class StepM {
    private int mId; // 记录在sqlite的id
    private long mBeginTime; // 计步开始时间
    private long mEndTime; // 计步结束时间
    private int mMode; // 计步模式: 0:不支持模式, 1:静止, 2:走路, 3:跑步, 11:骑车, 12:交通工具
    private int mSteps; // 总步数
}

