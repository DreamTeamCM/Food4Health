package com.food4health.presentation.Main.View

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.food4health.Food4Health
import com.sinergia.food4health.R
import com.food4health.base.BaseActivity
import com.food4health.presentation.Login.View.LoginActivity
import com.food4health.presentation.Main.MainContract
import com.food4health.presentation.Register.View.RegisterActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainContract.MainView {

    // CONSTRUCTOR
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        main_login_button.setOnClickListener { navigateToLogin() }
        main_register_button.setOnClickListener { navigateToRegister() }


        Food4Health.storagePermissionGranted = (
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)

    }

    //BASE ACTIVITY IMPLEMENT METHODS
    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun getPageTitle(): String {
        return getString(R.string.PG_MAINPAGE)
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}
