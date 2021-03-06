package com.food4health.presentation.MainMenu.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.food4health.base.BaseActivity
import com.food4health.presentation.Account.view.AccountActivity
import com.food4health.presentation.AddRecipe.View.AddRecipeActivity
import com.food4health.presentation.Catalog.View.CatalogActivity
import com.food4health.presentation.Favourite.View.FavouriteActivity
import com.food4health.presentation.MainMenu.MainMenuContract
import com.sinergia.food4health.R
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.layout_headder_bar.*

class MainMenuActivity : BaseActivity(), MainMenuContract.MainMenuView {

    // BASE ACTIVITY IMPLEMENTS METHODS
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        headder_bar_pageTitle.text = getPageTitle()
        headder_bar_menu_btn.setOnClickListener { finish() }

        menu_catalog.setOnClickListener { navigateToCatalog() }
        menu_favourites.setOnClickListener { navigateToFavourites() }
        menu_uploadRecipe.setOnClickListener { navigateToAddRecipe() }
        menu_account.setOnClickListener { navigateToAccount() }
    }

    override fun getLayout(): Int {
        return R.layout.activity_main_menu
    }

    override fun getPageTitle(): String {
        return getString(R.string.PG_MAINMENU)
    }

    override fun navigateToCatalog() {
        startActivity(Intent(this, CatalogActivity::class.java))
    }

    override fun navigateToAddRecipe() {
        startActivity(Intent(this, AddRecipeActivity::class.java))
    }

    override fun navigateToAccount() {
        startActivity(Intent(this, AccountActivity::class.java))
    }

    override fun navigateToFavourites() {
        startActivity(Intent(this, FavouriteActivity::class.java))
    }
}
