package com.food4health.presentation.Main.View

import android.os.Bundle
import com.sinergia.food4health.R
import com.food4health.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    // CONSTRUCTOR
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    //BASE ACTIVITY IMPLEMENT METHODS
    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun getPageTitle(): String {
        return getString(R.string.PG_MAINPAGE)
    }


}
