package com.food4health.presentation.Login.Presenter

import android.util.Log
import com.food4health.base.Exceptions.FirebaseLoginException
import com.food4health.domain.Interactors.Login.LoginInteractor
import com.food4health.presentation.Login.LoginContract
import com.sinergia.food4health.R
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginPresenter (loginInteractor: LoginInteractor): LoginContract.LoginPresenter, CoroutineScope {

    private val TAG = "[LOGIN_ACTIVITY]"
    private val loginJob = Job()

    var view : LoginContract.LoginView ?= null
    var loginInteractor : LoginInteractor ?= null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + loginJob

    init {
        this.loginInteractor = loginInteractor
    }

    override fun attachView(view: LoginContract.LoginView) {
        this.view = view
    }

    override fun dettachView() {
        this.view = null
    }

    override fun isViewAttached(): Boolean {
        return this.view != null
    }

    override fun checkEmptyLoginEmail(email: String): Boolean {
        return email.isNullOrEmpty()
    }

    override fun checkEmptyLoginPassword(password: String): Boolean {
        return password.isNullOrEmpty()
    }

    override fun checkEmptyFields(email: String, password: String): Boolean {
        return checkEmptyLoginEmail(email) || checkEmptyLoginPassword(password)
    }

    override fun checkValidLoginEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun loginWithEmailAndPassword(email: String, password: String) {
        launch {
            Log.d(TAG, "Triying to Login with email $email")
            view?.showLoginProgressBar()
            view?.disableLoginButton()
            try {
                loginInteractor?.login(email, password)
                Log.d(TAG, "Successfully Login with email $email")
                if (isViewAttached()){
                    view?.hideLoginProgressBar()
                    view?.enableLoginButton()
                    view?.navigateToMenu()
                    //view?.showMessage("Bienvenido ")
                }

            }catch (error: FirebaseLoginException){
                Log.d(TAG, "ERROR!: Cannot login user in Firebase. Error Message --> ${error.message}.")
                if (isViewAttached()){
                    view?.hideLoginProgressBar()
                    view?.enableLoginButton()
                    view?.showError(R.string.ERR_LOGIN_FAILURE)
                }
            }

        }
    }

}