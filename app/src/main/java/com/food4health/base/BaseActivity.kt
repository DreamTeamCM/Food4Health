package com.food4health.base

import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.beardedhen.androidbootstrap.TypefaceProvider

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        TypefaceProvider.registerDefaultIconSets();
    }

    @LayoutRes
    abstract fun getLayout(): Int
    abstract fun getPageTitle(): String

    fun toastS(context: Context, message: String?){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun toastL(context: Context, message: String?){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}