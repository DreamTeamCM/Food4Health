package com.food4health.presentation.Catalog.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.food4health.Food4Health
import com.food4health.base.BaseActivity
import com.food4health.data.Model.Recipe
import com.food4health.presentation.Catalog.CatalogContract
import com.food4health.presentation.Catalog.Model.ItemCatalogViewModelImpl
import com.food4health.presentation.Catalog.Presenter.ItemCatalogPresenter
import com.food4health.presentation.MainMenu.View.MainMenuActivity
import com.sinergia.food4health.R
import kotlinx.android.synthetic.main.activity_item_catalog.*
import kotlinx.android.synthetic.main.layout_headder_bar.*

class ItemCatalogActivity : BaseActivity(), CatalogContract.ItemCatalogView {

    lateinit var itemCatalogPresenter: CatalogContract.ItemCatalogPresenter

    // BASE ACTIVITY METHODS
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        headder_bar_pageTitle.text = getPageTitle()
        headder_bar_menu_btn.setOnClickListener { startActivity(Intent(this, MainMenuActivity::class.java)) }

        itemCatalogPresenter = ItemCatalogPresenter(ItemCatalogViewModelImpl())
        itemCatalogPresenter.attachView(this)

        getRecipe()

    }

    override fun getLayout(): Int {
        return R.layout.activity_item_catalog
    }

    override fun getPageTitle(): String {
        return Food4Health.currentRecipe.name
    }

    // VIEW METHODS
    override fun showError(error: Int) {
        toastL(this, getString(error))
    }

    override fun showMessage(message: Int) {
        toastL(this, getString(message))
    }

    override fun showItemCatalogProgressBar() {
        item_catalog_progressBar.visibility = View.VISIBLE
    }

    override fun hideItemCatalogProgressBar() {
        item_catalog_progressBar.visibility = View.GONE
    }

    override fun showItemCatalogContent() {
        item_catalog_recipeContent.visibility = View.VISIBLE
    }

    override fun hideItemCatalogContent() {
        item_catalog_recipeContent.visibility = View.GONE
    }

    override fun showItemCatalogButtons() {
        item_catalog_recipeButtons.visibility = View.VISIBLE
    }

    override fun hideItemCatalogContentButtons() {
        item_catalog_recipeButtons.visibility = View.GONE
    }

    override fun getRecipe() {
        itemCatalogPresenter.getItemCatalog(Food4Health.currentRecipe.id)
    }

    override fun initInitCatalogContent(recipe: Recipe) {

        item_catalog_recipeContent_name.text = recipe.name
        item_catalog_recipeContent_description.text = recipe.description
        item_catalog_recipeContent_suggestions.text = recipe.suggestions
        item_catalog_recipeContent_owner.text = recipe.ownerName
        item_catalog_recipeContent_publicationDate.text = recipe.uploadDate

        var index = 1
        var ingredientsTxt: String = ""
        for(ingredient in recipe.ingredients){
            ingredientsTxt += "\t $index. $ingredient. \n"
            index ++
        }

        var preparationTxt: String = ""
        for(stepKey in recipe.preparation.keys){
            preparationTxt += "\t $stepKey. ${recipe.preparation[stepKey]}. \n"
            index ++
        }
        item_catalog_recipeContent_ingredients.text = ingredientsTxt
        item_catalog_recipeContent_preparation.text = preparationTxt

    }
}
