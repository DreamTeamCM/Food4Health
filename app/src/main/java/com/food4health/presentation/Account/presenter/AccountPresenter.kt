package com.food4health.presentation.Account.presenter

import android.net.Uri
import android.util.Log
import com.food4health.Food4Health
import com.food4health.base.Exceptions.FirebaseAddUserException
import com.food4health.base.Exceptions.FirebaseDeleteUserException
import com.food4health.base.Exceptions.FirebaseSetUserException
import com.food4health.base.Exceptions.FirebaseStorageUploadImageException
import com.food4health.data.Model.User
import com.food4health.presentation.Account.AccountContract
import com.food4health.presentation.Account.model.AccountViewModel
import com.sinergia.eLibrary.domain.interactors.AccountInteractor.AccountInteractor
import com.sinergia.food4health.R
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AccountPresenter(accountViewModel: AccountViewModel, accountInteractor: AccountInteractor): AccountContract.AccountPresenter, CoroutineScope {

    val TAG = "[ACCOUNT_ACTIVITY]"

    var view: AccountContract.AccountView ?= null
    var accountViewModel: AccountViewModel ?= null
    var accountInteractor: AccountInteractor ?= null

    private val accountJob = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + accountJob

    init{
        this.accountViewModel = accountViewModel
        this.accountInteractor = accountInteractor
    }

    override fun attachView(view: AccountContract.AccountView) {
        this.view = view
    }

    override fun dettachView() {
        this.view = null
    }

    override fun dettachJob() {
        coroutineContext.cancel()
    }

    override fun isViewAttach(): Boolean {
        return this.view !== null
    }

    override fun logOut() {
        launch {
            accountInteractor?.logOut()
            if (isViewAttach()) view?.navigateToMainPage()
        }
    }

    override fun updateAccount(newUserAccount: User) {

        if(isViewAttach()){
            view?.disableAllButtons()
            view?.showProgressBar()
        }

        launch{

            Log.d(TAG, "Trying to update account with email ${newUserAccount.email}.")

            Food4Health.currentUser = newUserAccount

            try{

                accountInteractor?.updateAccount(newUserAccount)
                accountViewModel?.deleteUserForUpdate(newUserAccount)
                accountViewModel?.addUserForUpdate(newUserAccount)

                if(isViewAttach()){
                    view?.showMessage(R.string.MSG_UPDATE_ACCOUNT)
                    view?.hideProgressBar()
                    view?.enableAllButtons()
                    view?.initAccountContent()
                }

                Log.d(TAG, "Succesfully update account with email ${newUserAccount.email}.")

            } catch (error: FirebaseSetUserException){

                val errorMsg = error.message
                if(isViewAttach()){
                    view?.showError(R.string.ERR_UPDATEACCOUNT_FAILURE)
                    view?.hideProgressBar()
                    view?.enableAllButtons()
                    view?.initAccountContent()
                }

                Log.d(TAG, "ERROR: Cannot update account with email ${newUserAccount.email} --> $errorMsg.")

            } catch(error: FirebaseAddUserException){

                val errorMsg = error.message
                if(isViewAttach()){
                    view?.showError(R.string.ERR_UPDATEACCOUNT_FAILURE)
                    view?.hideProgressBar()
                    view?.enableAllButtons()
                    view?.initAccountContent()
                }


                Log.d(TAG, "ERROR: Cannot update account with email ${newUserAccount.email} --> $errorMsg.")

            }

        }


    }

    override fun deleteAccount(user: User) {

        if(isViewAttach()){
            view?.disableAllButtons()
            view?.showProgressBar()
        }

        launch{

            Log.d(TAG, "Trying to delete account with email ${user.email}.")

            try{

                accountInteractor?.deleteUser(user)
                accountViewModel?.deleteAccount(user)

                if(isViewAttach()){
                    view?.showMessage(R.string.MSG_DELETE_ACCOUNT)
                    view?.hideProgressBar()
                    view?.enableAllButtons()
                    view?.navigateToMainPage()
                }

                Log.d(TAG, "Succesfully delete account with email ${user.email}.")

            } catch(error: FirebaseDeleteUserException){

                val errorMsg = error.message
                if(isViewAttach()){
                    view?.showError(R.string.ERR_DELETEACCOUNT_FAILURE)
                    view?.hideProgressBar()
                    view?.enableAllButtons()
                }

                Log.d(TAG, "ERROR: Cannot delete account with email ${user.email} --> $errorMsg.")

            }

        }
    }

    override fun uploadUserImage(imageURI: Uri) {

        if(isViewAttach()){
            view?.disableAllButtons()
            view?.showProgressBar()
        }

        launch{

            Log.d(TAG, "Trying to update avatar image to account with email ${Food4Health.currentUser.email}.")

            try{

                val newUserAvatar = accountViewModel?.uploadUserImage(Food4Health.currentUser.email, imageURI)!!
                Food4Health.currentUser.avatar = newUserAvatar.toString()
                accountViewModel?.setUserForUpdate(Food4Health.currentUser)

                if(isViewAttach()){
                    view?.showMessage(R.string.MSG_UPDATE_AVATAR)
                    view?.hideProgressBar()
                    view?.enableAllButtons()
                    view?.initAccountContent()
                }

            } catch (error: FirebaseStorageUploadImageException){

                val errorMsg = error.message
                if(isViewAttach()){
                    view?.showError(R.string.ERR_UPDATEAVATAR_FAILURE)
                    view?.hideProgressBar()
                    view?.enableAllButtons()
                }

                Log.d(TAG, "Cannot update avatar image to account with email ${Food4Health.currentUser.email} --> $errorMsg.")

            } catch (error: FirebaseSetUserException){

                val errorMsg = error.message
                if(isViewAttach()){
                    view?.showError(R.string.ERR_UPDATEAVATAR_FAILURE)
                    view?.hideProgressBar()
                    view?.enableAllButtons()
                }

                Log.d(TAG, "Cannot update avatar image to account with email ${Food4Health.currentUser.email} --> $errorMsg.")

            }


        }
    }
}