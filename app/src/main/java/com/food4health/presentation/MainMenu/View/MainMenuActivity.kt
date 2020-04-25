package com.food4health.presentation.MainMenu.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.food4health.base.BaseActivity
import com.sinergia.food4health.R
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.layout_headder_bar.*

class MainMenuActivity : BaseActivity() {

    // BASE ACTIVITY IMPLEMENTS METHODS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        headder_bar_pageTitle.text = getPageTitle()
        menu_catalog.setOnClickListener { toastL(this, getString(R.string.SYS_DEVELOP)) }
        menu_favourites.setOnClickListener { toastL(this, getString(R.string.SYS_DEVELOP)) }
        menu_uploadRecipe.setOnClickListener { toastL(this, getString(R.string.SYS_DEVELOP)) }
        menu_account.setOnClickListener { toastL(this, getString(R.string.SYS_DEVELOP)) }
    }

    override fun getLayout(): Int {
        return R.layout.activity_main_menu
    }

    override fun getPageTitle(): String {
        return getString(R.string.PG_MAINMENU)
    }
}
