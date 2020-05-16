package com.food4health.presentation.SetRecipe.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.food4health.Food4Health
import com.food4health.base.BaseActivity
import com.food4health.presentation.SetRecipe.SetRecipeContract
import com.sinergia.food4health.R
import kotlinx.android.synthetic.main.activity_set_recipe.*

class SetRecipeActivity : BaseActivity(), SetRecipeContract.SetRecipeView {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_recipe)
    }

    override fun getLayout(): Int {
        return R.layout.activity_set_recipe
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

    override fun showSetRecipeProgressBar() {
        setRecipe_progressBar.visibility = View.VISIBLE
    }

    override fun hideSetRecipeProgressBar() {
        setRecipe_progressBar.visibility = View.GONE
    }

    override fun enableSetRecipeButton() {
        setRecipe_button.isEnabled = true
        setRecipe_button.isClickable = true
    }

    override fun disableSetRecipeButton() {
        setRecipe_button.isEnabled = false
        setRecipe_button.isClickable = false
    }

    override fun initSetRecipeContent() {
        setRecipe_name.setText(Food4Health.currentRecipe.name.toString())
        setRecipe_description.setText(Food4Health.currentRecipe.description.toString())
        setRecipe_principalIngredient
        setRecipe_principalStep
        setRecipe_suggestons.setText(Food4Health.currentRecipe.suggestions.toString())
    }

    override fun addIngredient() {
        val ingredient = EditText(this)
        ingredient.width = LinearLayout.LayoutParams.MATCH_PARENT
        ingredient.height = setRecipe_principalIngredient.height
        ingredient.hint = getString(R.string.FRH_INGREDIENTS)
        ingredient.inputType = setRecipe_principalIngredient.inputType
        setRecipe_ingredients.addView(ingredient)
    }

    override fun addStep() {
        val step = EditText(this)
        step.width = LinearLayout.LayoutParams.MATCH_PARENT
        step.height = setRecipe_principalStep.height
        step.hint = getString(R.string.FRH_PREPARATION)
        step.inputType = setRecipe_principalStep.inputType
        setRecipe_preparation.addView(step)
    }

    override fun setRecipe() {
        TODO("Not yet implemented")
    }

    override fun navigateToRecipe(id: String) {
        TODO("Not yet implemented")
    }
}
