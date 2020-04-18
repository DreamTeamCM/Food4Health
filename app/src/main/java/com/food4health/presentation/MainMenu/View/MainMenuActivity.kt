package com.food4health.presentation.MainMenu.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.food4health.base.BaseActivity
import com.sinergia.food4health.R

class MainMenuActivity : BaseActivity() {

    // BASE ACTIVITY IMPLEMENTS METHODS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
    }

    override fun getLayout(): Int {
        return R.layout.activity_main_menu
    }

    override fun getPageTitle(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
