package com.example.demo8

import android.os.Build
import java.util.*


/**
 * @ Description:
 *
 * @ Author: LiuZhouLiang
 *
 * @ Time：2023/2/6 5:25 下午
 *
 */
const val XIAOMI = "xiaomi"
const val HUAWEI = "huawei"
const val OPPO = "oppo"
const val VIVO = "vivo"
fun isXiaomi(): Boolean {
    return Build.BRAND != null && Build.BRAND.lowercase(Locale.getDefault()) == XIAOMI
}

fun isHuawei(): Boolean {
    return if (Build.BRAND == null) {
        false
    } else {
        Build.BRAND.lowercase(Locale.getDefault()) == HUAWEI
    }
}

fun isOPPO(): Boolean {
    return Build.BRAND != null && Build.BRAND.lowercase(Locale.getDefault()) == OPPO
}

fun isVIVO(): Boolean {
    return Build.BRAND != null && Build.BRAND.lowercase(Locale.getDefault()) == VIVO
}