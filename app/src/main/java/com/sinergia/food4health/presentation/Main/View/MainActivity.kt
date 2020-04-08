package com.sinergia.food4health.presentation.Main.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sinergia.food4health.R
import com.sinergia.food4health.base.BaseActivity

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
        return "PÁGINA PRINCIPAL"
    }


}
