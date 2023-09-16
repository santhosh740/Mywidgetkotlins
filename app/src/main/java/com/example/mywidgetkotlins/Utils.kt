package com.example.mywidgetkotlins

import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar

object Utils {
    var mProgress: ProgressDialog? = null
    fun mProgress(context: Context?, txt: String?, aBoolean: Boolean?): ProgressDialog {
        com.example.mywidgetkotlins.Utils.mProgress = ProgressDialog(context)
        com.example.mywidgetkotlins.Utils.mProgress!!.setMessage(txt)
        if (aBoolean != null) {
            com.example.mywidgetkotlins.Utils.mProgress!!.setCancelable(aBoolean)
        }
        return com.example.mywidgetkotlins.Utils.mProgress!!
    }

    fun versioncode_get(context: Context): Int {
        var pInfo: PackageInfo? = null
        try {
            pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return pInfo!!.versionCode
    }

    fun currentDate(): String {
        val todayDate = Calendar.getInstance().time
        val formatter1 = SimpleDateFormat("dd-MM-yyyy")
        return formatter1.format(todayDate)
    }

    fun versionname_get(context: Context): String {
        var pInfo: PackageInfo? = null
        try {
            pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return pInfo!!.versionName
    }

    fun toast_normal(context: Context?, str: String) {
        Toast.makeText(context, "" + str, Toast.LENGTH_SHORT).show()
    }

    fun toast_center(context: Context?, str: String) {
        val toast = Toast.makeText(context, "" + str, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun pad(str: String): String {
        var str = str
        if (str.length == 1) {
            str = "0$str"
        }
        return str
    }

    val deviceName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            val Brand = Build.BRAND
            val Product = Build.PRODUCT
            return "$manufacturer-$model-$Brand-$Product"
        }

    fun isNetworkAvailable(context: Context): Boolean {
        val connec = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connec.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun android_id(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}