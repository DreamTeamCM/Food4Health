package com.food4health.presentation.SetRecipe.View

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.bumptech.glide.Glide
import com.food4health.Food4Health
import com.food4health.base.BaseActivity
import com.food4health.data.DataBase.FoodForHealth_Storage
import com.food4health.data.Model.Recipe
import com.food4health.presentation.Catalog.View.ItemCatalogActivity
import com.food4health.presentation.MainMenu.View.MainMenuActivity
import com.food4health.presentation.SetRecipe.Model.SetRecipeViewModelImpl
import com.food4health.presentation.SetRecipe.Presenter.SetRecipePresenter
import com.food4health.presentation.SetRecipe.SetRecipeContract
import com.sinergia.food4health.R
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_set_recipe.*
import kotlinx.android.synthetic.main.layout_headder_bar.*
import java.time.LocalDateTime

class SetRecipeActivity : BaseActivity(), SetRecipeContract.SetRecipeView {

    lateinit var setRecipePresenter: SetRecipeContract.SetRecipePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        headder_bar_pageTitle.text = getPageTitle()
        headder_bar_menu_btn.setOnClickListener { startActivity(Intent(this, MainMenuActivity::class.java)) }

        setRecipePresenter = SetRecipePresenter(SetRecipeViewModelImpl())
        setRecipePresenter.attachView(this)

        setRecipe_addIngredient.setOnClickListener { addIngredient() }
        setRecipe_addStep.setOnClickListener { addStep() }

        setRecipe_recipe_image.setOnClickListener { uploadGalleryImage() }
        setRecipe_button.setOnClickListener { setRecipe() }

        initSetRecipeContent()

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
        setRecipe_name.setText(Food4Health.currentRecipe.name)
        setRecipe_description.setText(Food4Health.currentRecipe.description)
        setRecipe_principalIngredient
        setRecipe_principalStep
        setRecipe_suggestons.setText(Food4Health.currentRecipe.suggestions)

        if(Food4Health.currentRecipe.image != "noImage"){
            Glide
                .with(this)
                .load(Uri.parse(Food4Health.currentRecipe.image))
                .fitCenter()
                .centerCrop()
                .into(setRecipe_recipe_image)
        }

        val currentRecipeIngredients = Food4Health.currentRecipe.ingredients
        val currentRecipeSteps = Food4Health.currentRecipe.preparation


        for(recipeIngredient in currentRecipeIngredients){

            if(currentRecipeIngredients.indexOf(recipeIngredient) == 0){
                setRecipe_principalIngredient.setText(recipeIngredient)
            } else {
                addIngredient(recipeIngredient)
            }

        }

        for(recipeStep in currentRecipeSteps){

            if(recipeStep.key == "1"){
                setRecipe_principalIngredient.setText(recipeStep.value)
            } else {
                addStep(recipeStep.value)
            }

        }
    }

    override fun addIngredient() {
        val ingredient = EditText(this)
        ingredient.width = LinearLayout.LayoutParams.MATCH_PARENT
        ingredient.height = setRecipe_principalIngredient.height
        ingredient.hint = getString(R.string.FRH_INGREDIENTS)
        ingredient.inputType = setRecipe_principalIngredient.inputType
        ingredient.setTextColor(setRecipe_principalIngredient.currentTextColor)
        setRecipe_ingredients.addView(ingredient)
    }

    override fun addIngredient(recipeIngredient: String) {
        val ingredient = EditText(this)
        ingredient.width = LinearLayout.LayoutParams.MATCH_PARENT
        ingredient.height = setRecipe_principalIngredient.height
        ingredient.hint = getString(R.string.FRH_INGREDIENTS)
        ingredient.inputType = setRecipe_principalIngredient.inputType
        ingredient.setTextColor(setRecipe_principalIngredient.currentTextColor)
        ingredient.setText(recipeIngredient)
        setRecipe_ingredients.addView(ingredient)
    }

    override fun addStep() {
        val step = EditText(this)
        step.width = LinearLayout.LayoutParams.MATCH_PARENT
        step.height = setRecipe_principalStep.height
        step.hint = getString(R.string.FRH_PREPARATION)
        step.inputType = setRecipe_principalStep.inputType
        step.setTextColor(setRecipe_principalStep.currentTextColor)
        setRecipe_preparation.addView(step)
    }

    override fun addStep(recipeStep: String) {
        val step = EditText(this)
        step.width = LinearLayout.LayoutParams.MATCH_PARENT
        step.height = setRecipe_principalStep.height
        step.hint = getString(R.string.FRH_PREPARATION)
        step.inputType = setRecipe_principalStep.inputType
        step.setTextColor(setRecipe_principalStep.currentTextColor)
        step.setText(recipeStep)
        setRecipe_preparation.addView(step)
    }

    override fun setRecipe() {

        val recipeName: String = setRecipe_name.text.toString()
        val recipeDescription: String = setRecipe_description.text.toString()
        val recipeIngredients = setRecipe_ingredients
        val recipePreparation = setRecipe_preparation
        var recipeSuggestions: String = setRecipe_suggestons.text.toString()

        var recipeIngredientsList: ArrayList<String> = arrayListOf()
        for(ingredient in recipeIngredients.children){
            if(ingredient is EditText){
                val inputIngredient = ingredient as EditText
                if(!inputIngredient.text.toString().isNullOrEmpty()) recipeIngredientsList.add(inputIngredient.text.toString())
            }
        }

        var recipePreparationMap: MutableMap<String, String> = mutableMapOf()
        for(index in 0 until recipePreparation.childCount){
            if(recipePreparation.getChildAt(index) is EditText){
                val inputStep = recipePreparation.getChildAt(index) as EditText
                if(!inputStep.text.toString().isNullOrEmpty()) recipePreparationMap[(index+1).toString()] = inputStep.text.toString()
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
            "${Food4Health.currentUser.name} ${Food4Health.currentUser.firstLastName}",
            Food4Health.currentRecipe.uploadDate,
            Food4Health.currentRecipe.image,
            Food4Health.currentRecipe.likes,
            Food4Health.currentRecipe.id
        )

        if(setRecipePresenter.checkEmptyFields(recipeName, recipeDescription, recipeIngredientsList, recipePreparationMap, recipeSuggestions)){

            if(setRecipePresenter.checkEmptySetRecipeName(recipeName)){
                setRecipe_name.error = "¡Cuidado! Te has dejado vacío el campo 'Nombre'."
            }
            if(setRecipePresenter.checkEmptySetRecipeDescription(recipeDescription)){
                setRecipe_description.error = "¡Cuidado! Te has dejado vacío el campo 'Descripción'."
            }
            if(setRecipePresenter.checkEmptySetRecipeIngredients(recipeIngredientsList)){
                toastS(this, "Debes indicar los ingredientes")
            }
            if(setRecipePresenter.checkEmptySetRecipePreparation(recipePreparationMap)){
                toastS(this, "Debes indicar los pasos para la preparación.")
            }
            if(setRecipePresenter.checkEmptySetRecipeSuggestions(recipeSuggestions)){
                setRecipe_suggestons.error = "¡Cuidado! Te has dejado vacío el campo 'Sugerencias'."
            }

        } else {

            setRecipePresenter.setRecipe(newRecipe)

        }

    }

    override fun navigateToRecipe(id: String) {
        startActivity(Intent(this, ItemCatalogActivity::class.java))
    }

    // GALLERY METHODS
    override fun checkAndSetGalleryPermissions() {
        val permissionStatusRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if(permissionStatusRead == PackageManager.PERMISSION_GRANTED) {
            Food4Health.storagePermissionGranted = true
        } else {
            if (permissionStatusRead != PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE), Food4Health.READ_STORAGE_PERMISSIONS_CODE)
        }
    }

    override fun uploadGalleryImage() {
        if(Food4Health.storagePermissionGranted){
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, Food4Health.GALLERY_INTENT_CODE)
        } else {
            toastL(this, getString(R.string.MSG_PERMISSION_GALLERY_REQUEST))
            checkAndSetGalleryPermissions()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageData: Intent?) {

        super.onActivityResult(requestCode, resultCode, imageData)

        if(requestCode == Food4Health.GALLERY_INTENT_CODE && resultCode == Activity.RESULT_OK){

            var imageURI: Uri = imageData?.data!!
            setRecipePresenter.uploadRecipeImage(imageURI)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            Food4Health.READ_STORAGE_PERMISSIONS_CODE ->
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Food4Health.storagePermissionGranted = (
                            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                } else {
                    toastL(this, getString(R.string.ERR_PERMISSION_GALLERY))
                }
        }
    }

}
