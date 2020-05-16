package com.food4health.presentation.Favourite.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.food4health.Food4Health
import com.food4health.base.BaseActivity
import com.food4health.base.Utils.CreateCards
import com.food4health.data.Model.Recipe
import com.food4health.presentation.Catalog.View.ItemCatalogActivity
import com.food4health.presentation.Favourite.FavouriteContract
import com.food4health.presentation.Favourite.Model.FavouriteViewModelImpl
import com.food4health.presentation.Favourite.Presenter.FavouritePresenter
import com.food4health.presentation.MainMenu.View.MainMenuActivity
import com.sinergia.food4health.R
import kotlinx.android.synthetic.main.activity_favourite.*
import kotlinx.android.synthetic.main.layout_headder_bar.*

class FavouriteActivity : BaseActivity(), FavouriteContract.FavouriteView {

    val createCards: CreateCards = CreateCards()

    lateinit var favouritePresenter: FavouriteContract.FavouritePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        headder_bar_pageTitle.text = getPageTitle()
        headder_bar_menu_btn.setOnClickListener { startActivity(Intent(this, MainMenuActivity::class.java)) }



        favouritePresenter = FavouritePresenter(FavouriteViewModelImpl())
        favouritePresenter.attachView(this)

        getFavouriteRecipes()

    }

    override fun getLayout(): Int {
        return R.layout.activity_favourite
    }

    override fun getPageTitle(): String {
        return "Recetas Favoritas"
    }

    // VIEW METHODS
    override fun showError(error: Int) {
        toastL( this, getString(error))
    }

    override fun showMessage(message: Int) {
        toastL(this, getString(message))
    }

    override fun showFavouriteProgressBar() {
        favourite_progressBar.visibility = View.VISIBLE
    }

    override fun hideFavouriteProgressBar() {
        favourite_progressBar.visibility = View.GONE
    }

    override fun showFavouriteContent() {
        favourite_content.visibility = View.VISIBLE
    }

    override fun hideFavouriteContent() {
        favourite_content.visibility = View.GONE
    }

    override fun getFavouriteRecipes() {
        favouritePresenter.getFavouriteRecipes()
    }

    override fun initFavouriteContent(recipes: List<Recipe>) {
        for(recipe in recipes){
            val recipeCard = createCards.createRecipeCard(this, recipe)
            recipeCard.setOnClickListener {
                Food4Health.currentRecipe = recipe
                startActivity(Intent(this, ItemCatalogActivity::class.java))
            }
            favourite_content.addView(recipeCard)
        }

    }
}


