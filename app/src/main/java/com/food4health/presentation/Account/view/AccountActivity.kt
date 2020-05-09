package com.food4health.presentation.Account.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.food4health.Food4Health
import com.food4health.base.BaseActivity
import com.food4health.data.Model.User
import com.food4health.presentation.Account.AccountContract
import com.food4health.presentation.Account.model.AccountViewModel
import com.food4health.presentation.Account.model.AccountViewModelImpl
import com.food4health.presentation.Account.presenter.AccountPresenter
import com.food4health.presentation.Main.View.MainActivity
import com.food4health.presentation.MainMenu.View.MainMenuActivity
import com.sinergia.eLibrary.domain.interactors.AccountInteractor.AccountInteractorImpl
import com.sinergia.food4health.R
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.layout_headder_bar.*

class AccountActivity : BaseActivity(), AccountContract.AccountView {

    var TAG = "[ACCOUNT_ACTIVITY]"

    lateinit var accountPresenter: AccountContract.AccountPresenter
    lateinit var accountViewModel: AccountViewModel

    // BASE ACTIVITY METHODS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accountPresenter = AccountPresenter(AccountViewModelImpl(), AccountInteractorImpl())
        accountPresenter.attachView(this)
        accountViewModel = AccountViewModelImpl()

        headder_bar_pageTitle.text = getPageTitle()
        headder_bar_menu_btn.setOnClickListener { startActivity(Intent(this, MainMenuActivity::class.java)) }

    }

    override fun getLayout(): Int {
        return R.layout.activity_account
    }

    override fun getPageTitle(): String {
        return getString(R.string.PG_ACCOUNT)
    }

    //VEW METHODS
    override fun showError(error: Int) {
        toastL(this, getString(error))
    }

    override fun showMessage(message: Int) {
        toastL(this, getString(message))
    }

    override fun showProgressBar() {
        account_progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        account_progressBar.visibility = View.GONE
    }

    override fun enableAllButtons() {
        account_update_btn.isClickable = true
        account_update_btn.isEnabled = true
        account_delete_btn.isClickable = true
        account_delete_btn.isEnabled = true
        account_userAvatar.isClickable = true
    }

    override fun disableAllButtons() {
        account_update_btn.isClickable = false
        account_update_btn.isEnabled = false
        account_delete_btn.isClickable = false
        account_delete_btn.isEnabled = false
        account_userAvatar.isClickable = false
    }

    override fun initAccountContent() {

        val currentUser = Food4Health.currentUser

        if(currentUser.avatar != "SinAvatar"){
            Glide
                .with(this)
                .load(Uri.parse(currentUser.avatar))
                .fitCenter()
                .centerCrop()
                .into(account_userAvatar)
        }

        account_nameHead.text = "${currentUser.name} ${Food4Health.currentUser.firstLastName}"
        account_mailHead.text = currentUser.email

        account_userName.setText(currentUser.name)
        account_userLastName1.setText(currentUser.firstLastName)
        account_userLastName2.setText(currentUser.secondLastName)
        account_userMail.setText(currentUser.email)
        account_userNIF.setText(currentUser.nif)

    }

    override fun logOut() {
        accountPresenter.logOut()
    }

    override fun updateAccount() {
        var newName = account_userName.text.toString()
        var newLastName1 = account_userLastName1.text.toString()
        var newLastName2 = account_userLastName2.text.toString()
        var newEmail = account_userMail.text.toString()
        var newNIF = account_userNIF.text.toString()

        var newUserAccount = User(
            newName,
            newLastName1,
            newLastName2,
            newNIF,
            newEmail,
            Food4Health.currentUser.avatar
        )

        accountPresenter.updateAccount(newUserAccount)
    }

    override fun deleteAccount() {

        accountPresenter.deleteAccount(Food4Health.currentUser)

    }

    override fun navigateToMainPage() {
        val intentMainPage = Intent(this, MainActivity::class.java)
        intentMainPage.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentMainPage)
    }

    // GALLERY METHODS
    override fun checkAndSetGalleryPermissions() {
        val permissionStatusRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        val permissionStatusWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(permissionStatusRead == PackageManager.PERMISSION_GRANTED && permissionStatusWrite == PackageManager.PERMISSION_GRANTED) {
            Food4Health.storagePermissionGranted = true
        } else {
            if (permissionStatusRead != PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE), Food4Health.READ_STORAGE_PERMISSIONS_CODE)
            if (permissionStatusWrite != PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE), Food4Health.WRITE_STORAGE_PERMISSIONS_CODE)
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
            accountPresenter.uploadUserImage(imageURI)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            Food4Health.READ_STORAGE_PERMISSIONS_CODE ->
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Food4Health.storagePermissionGranted = (
                            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            )
                } else {
                    toastL(this, getString(R.string.ERR_PERMISSION_GALLERY))
                }
            Food4Health.WRITE_STORAGE_PERMISSIONS_CODE ->
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Food4Health.storagePermissionGranted = (
                            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            )
                } else {
                    toastL(this, getString(R.string.ERR_PERMISSION_GALLERY))
                }
        }
    }
}
