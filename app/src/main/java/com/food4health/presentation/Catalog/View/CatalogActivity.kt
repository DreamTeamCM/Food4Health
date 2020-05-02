package com.food4health.presentation.Catalog.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.food4health.base.BaseActivity
import com.food4health.base.Utils.CreateCards
import com.food4health.data.Model.Recipe
import com.food4health.presentation.Catalog.CatalogContract
import com.food4health.presentation.Catalog.Model.CatalogViewModelImpl
import com.food4health.presentation.Catalog.Presenter.CatalogPresenter
import com.food4health.presentation.MainMenu.View.MainMenuActivity
import com.sinergia.food4health.R
import kotlinx.android.synthetic.main.activity_catalog.*
import kotlinx.android.synthetic.main.layout_headder_bar.*

class CatalogActivity : BaseActivity(), CatalogContract.CatalogView {

    val createCards: CreateCards = CreateCards()

    lateinit var catalogPresenter: CatalogContract.CatalogPresenter

    // BASE ACTIVITY METHODS
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        headder_bar_pageTitle.text = getPageTitle()
        headder_bar_menu_btn.setOnClickListener { startActivity(Intent(this, MainMenuActivity::class.java)) }

        catalogPresenter = CatalogPresenter(CatalogViewModelImpl())
        catalogPresenter.attachView(this)

        getAllRecipes()

    }

    override fun getLayout(): Int {
        return R.layout.activity_catalog
    }

    override fun getPageTitle(): String {
        return getString(R.string.PG_CATALOG)
    }

    // VIEW MOETHODS
    override fun showError(error: Int) {
        toastL(this, getString(error))
    }

    override fun showMessage(message: Int) {
        toastL(this, getString(message))
    }

    override fun showCatalogProgressBar() {
        catalog_progressBar.visibility = View.VISIBLE
    }

    override fun hideCatalogProgressBar() {
        catalog_progressBar.visibility = View.GONE
    }

    override fun showCatalogContent() {
        catalog_content.visibility = View.VISIBLE
    }

    override fun hideCatalogContent() {
        catalog_content.visibility = View.GONE
    }

    override fun getAllRecipes() {
        catalogPresenter.getAllRecipes()
    }

    override fun initCatalogContent(recipes: List<Recipe>) {

        for(recipe in recipes){
            val recipeCard = createCards.createRecipeCard(this, recipe)
            catalog_content.addView(recipeCard)
        }

    }
}
