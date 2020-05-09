package com.food4health.presentation.Account

import android.net.Uri
import com.food4health.data.Model.User

interface AccountContract {

    interface AccountView{

        fun showError(error: Int)
        fun showMessage(message: Int)
        fun showProgressBar()
        fun hideProgressBar()
        fun enableAllButtons()
        fun disableAllButtons()
        fun initAccountContent()

        fun logOut()
        fun updateAccount()
        fun deleteAccount()

        fun navigateToMainPage()

        fun checkAndSetGalleryPermissions()
        fun uploadGalleryImage()

    }

    interface AccountPresenter{

        fun attachView(view: AccountView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttach(): Boolean

        fun logOut()
        fun updateAccount(newUserAccount: User)
        fun deleteAccount(user: User)

        fun uploadUserImage(imageURI: Uri)

    }
}