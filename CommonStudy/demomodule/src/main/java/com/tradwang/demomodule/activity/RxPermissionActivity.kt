package com.tradwang.demomodule.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.telephony.TelephonyManager
import com.jakewharton.rxbinding2.view.RxView
import com.tbruyelle.rxpermissions2.RxPermissions
import com.tradwang.common.base.BaseActivity
import com.tradwang.demomodule.R
import kotlinx.android.synthetic.main.demo_activity_rx_permission.*

class RxPermissionActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_rx_permission)

        val rxPermissions = RxPermissions(this)

        rxPermissions.setLogging(true)

        RxView.clicks(btn_request_phone_state).compose(rxPermissions.ensure(Manifest.permission.READ_PHONE_STATE)).subscribe { granted ->
            if (granted) {
                showToast("phone device id  = ${readPhoneState()}")
            } else {
                showToast("获取权限失败！")
            }
        }

        RxView.clicks(btn_open_overlay_permission).subscribe {
            //6.0 直接打开悬浮窗授权页面
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    showToast("获取系统悬浮窗权限成功！")
                } else {
                    val intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_OVERLAY_PERMISSION
                    intent.data = Uri.fromParts("package", packageName, null);
                    startActivity(intent)
                }
            } else {
                //打开应用设置页面
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.data = Uri.fromParts("package", packageName, null);
                startActivity(intent)
            }
        }

        RxView.clicks(btn_open_camera).compose(rxPermissions.ensure(Manifest.permission.CAMERA)).subscribe { granted ->
            if (granted) {
                showToast("获取相机权限成功，可以打开相机！")
            } else {
                showToast("获取相机权限失败，跳转设置页面！")
                //打开应用设置页面
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.data = Uri.fromParts("package", packageName, null);
                startActivity(intent)
            }
        }
    }

    @SuppressLint("HardwareIds")
    private fun readPhoneState(): String? {
        return if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyManager.deviceId
        } else {
            null
        }
    }
}
