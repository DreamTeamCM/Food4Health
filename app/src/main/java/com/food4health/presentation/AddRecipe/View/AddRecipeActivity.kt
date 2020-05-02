package com.food4health.presentation.AddRecipe.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.view.children
import androidx.core.view.iterator
import com.food4health.Food4Health
import com.food4health.base.BaseActivity
import com.food4health.data.Model.Recipe
import com.food4health.presentation.AddRecipe.AddRecipeContract
import com.food4health.presentation.AddRecipe.Model.AddRecipeViewModelImpl
import com.food4health.presentation.AddRecipe.Presenter.AddRecipePresenter
import com.sinergia.food4health.R
import kotlinx.android.synthetic.main.activity_add_recipe.*
import java.time.LocalDateTime

class AddRecipeActivity : BaseActivity(), AddRecipeContract.AddRecipeView {

    lateinit var addRecipePresenter: AddRecipeContract.AddRecipePresenter

    // BASE ACTIVITY METHODS
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        addRecipePresenter = AddRecipePresenter(AddRecipeViewModelImpl())
        addRecipePresenter.attachView(this)

        addRecipe_button.setOnClickListener { addRecipe() }

    }

    override fun getLayout(): Int {
        return R.layout.activity_add_recipe
    }

    override fun getPageTitle(): String {
        return getString(R.string.PG_ADDRECIPE)
    }

    // VIEW METHODS
    override fun showError(error: Int) {
        toastL(this, getString(error))
    }

    override fun showMessage(message: Int) {
        toastL(this, getString(message))
    }

    override fun showAddRecipeProgressBar() {
        addRecipe_progressBar.visibility = View.VISIBLE
    }

    override fun hideAddRecipeProgressBar() {
        addRecipe_progressBar.visibility = View.INVISIBLE
    }

    override fun enableAddRecipeButton() {
        addRecipe_button.isEnabled = true
        addRecipe_button.isClickable = true
    }

    override fun disableAddRecipeButton() {
        addRecipe_button.isEnabled = false
        addRecipe_button.isClickable = false
    }

    override fun addRecipe() {

        val recipeName: String = addRecipe_name.text.toString()
        val recipeDescription: String = addRecipe_description.text.toString()
        val recipeIngredients = addRecipe_ingredients
        val recipePreparation = addRecipe_preparation
        var recipeSuggestions: String = addRecipe_suggestons.text.toString()

        var recipeIngredientsList: ArrayList<String> = arrayListOf()
        for(ingredient in recipeIngredients.children){
            if(ingredient is EditText){
                val inputIngredient = ingredient as EditText
                if(!inputIngredient.text.toString().isNullOrEmpty()) recipeIngredientsList.add(inputIngredient.text.toString())
            }
        }

        var recipePreparationMap: MutableMap<Integer, String> = mutableMapOf()
        for(index in 0 until recipePreparation.childCount){
            if(recipePreparation.getChildAt(index) is EditText){
                val inputStep = recipePreparation.getChildAt(index) as EditText
                if(!inputStep.text.toString().isNullOrEmpty()) recipePreparationMap[Integer(index)] = inputStep.text.toString()
            }
        }
        recipePreparationMap.toMap()

        val newRecipe = Recipe(
            recipeName,
            recipeDescription,
            recipeIngredientsList,
            recipePreparationMap,
            recipeSuggestions,
            Food4Health.currentUser.email,
            LocalDateTime.now().toString()
        )

        if(addRecipePresenter.checkEmptyFields(recipeName, recipeDescription, recipeIngredientsList, recipePreparationMap, recipeSuggestions)){

            if(addRecipePresenter.checkEmptyAddRecipeName(recipeName)){
                addRecipe_name.error = "¡Cuidado! Te has dejado vacío el campo 'Nombre'."
            }
            if(addRecipePresenter.checkEmptyAddRecipeDescription(recipeDescription)){
                addRecipe_description.error = "¡Cuidado! Te has dejado vacío el campo 'Descripción'."
            }
            if(addRecipePresenter.checkEmptyAddRecipeIngredients(recipeIngredientsList)){
                toastS(this, "Debes indicar los ingredientes")
            }
            if(addRecipePresenter.checkEmptyAddRecipePreparation(recipePreparationMap)){
                toastS(this, "Debes indicar los pasos para la preparación.")
            }
            if(addRecipePresenter.checkEmptyAddRecipeSuggestions(recipeSuggestions)){
                addRecipe_suggestons.error = "¡Cuidado! Te has dejado vacío el campo 'Sugerencias'."
            }

        } else {

            addRecipePresenter.addRecipe(newRecipe)

        }

    }

    override fun navigateToRecipe(id: String) {
        // TODO: Implement ItemCatalogActivity before descoment this function.
        /*val loginIntent = Intent(this, ItemCatalogActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(loginIntent)*/
    }

}
