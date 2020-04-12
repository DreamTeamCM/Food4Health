package com.food4health.presentation.Register.Presenter

import android.util.Log
import com.food4health.base.Exceptions.FirebaseAddUserException
import com.food4health.base.Exceptions.FirebaseRegisterException
import com.food4health.data.Model.User
import com.food4health.domain.Interactors.Register.RegisterInteractor
import com.food4health.presentation.Register.Model.RegisterViewModel
import com.food4health.presentation.Register.RegisterContract
import com.sinergia.food4health.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RegisterPresenter(registerInteractor: RegisterInteractor, registerViewModel: RegisterViewModel): RegisterContract.RegisterPresenter, CoroutineScope {

    private val TAG = "[REGISTER_ACTIVITY]"
    private val registerJob = Job()

    var view: RegisterContract.RegisterView? = null
    var registerInteractor: RegisterInteractor? = null
    var registerViewModel: RegisterViewModel ?= null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + registerJob

    init {
        this.registerInteractor = registerInteractor
        this.registerViewModel = registerViewModel
    }

    override fun attachView(view: RegisterContract.RegisterView) {
        this.view = view
    }

    override fun dettachView() {
        this.view = null
    }

    override fun isViewAttached(): Boolean {
        return this.view != null
    }

    override fun checkEmptyRegisterName(userName: String): Boolean {
        return userName.isNullOrEmpty()
    }

    override fun checkEmptyRegisterFirstLastName(firstLastName: String): Boolean {
        return firstLastName.isNullOrEmpty()
    }

    override fun checkEmptyRegisterSecondLastName(secondLastName: String): Boolean {
        return secondLastName.isNullOrEmpty()
    }

    override fun checkEmptyRegisterNIF(nif: String): Boolean {
        return nif.isNullOrEmpty()
    }

    override fun checkEmptyRegisterEmail(email: String): Boolean {
        return email.isNullOrEmpty()
    }

    override fun checkEmptyRegisterPassword(password: String): Boolean {
        return password.isNullOrEmpty()
    }

    override fun checkEmptyRegisterRepeatPassword(repeatPassword: String): Boolean {
        return repeatPassword.isNullOrEmpty()
    }

    override fun checkEmptyFields(
        name: String,
        firstLastName: String,
        secondLastName: String,
        nif: String,
        email: String,
        password: String,
        repeatPassword: String
    ): Boolean {
        return (checkEmptyRegisterName(name) ||
                checkEmptyRegisterFirstLastName(firstLastName) ||
                checkEmptyRegisterSecondLastName(secondLastName) ||
                checkEmptyRegisterNIF(nif) ||
                checkEmptyRegisterEmail(email) ||
                checkEmptyRegisterPassword(password) ||
                checkEmptyRegisterRepeatPassword(repeatPassword))
    }

    override fun checkValidRegisterEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun checkRegisterPasswordMatch(password: String, repeatPassword: String): Boolean {
        return password === repeatPassword
    }

    override fun registerWithEmailAndPassword(user: User, password: String) {

        launch {

            Log.d(TAG, "Trying to register user in Firebase with email ${user.email}.")

            view?.showRegisterProgressBar()
            view?.disableRegisterButton()

            try {

                registerInteractor?.register(user.name, user.email, password)

                try{

                    Log.d(TAG, "Trying to add new user to database with email ${user.email}...")
                    registerViewModel?.addUser(user)
                    Log.d(TAG, "Succesfully added new user to database with email ${user.email}.")

                } catch (error: FirebaseAddUserException){

                    if(isViewAttached()){
                        view?.hideRegisterProgressBar()
                        view?.enableRegisterButton()
                        view?.showError(R.string.ERR_REGISTER_FAILURE)
                    }

                    Log.d(TAG, "ERROR!: Cannot added new user to database with email ${user.email}. Error Message --> ${error.message}.")

                }

                if(isViewAttached()){
                    view?.hideRegisterProgressBar()
                    view?.enableRegisterButton()
                    view?.navigateToLogin()
                }

            } catch (error: FirebaseRegisterException) {

                if(isViewAttached()){
                    view?.hideRegisterProgressBar()
                    view?.enableRegisterButton()
                    view?.showError(R.string.ERR_REGISTER_FAILURE)
                }

                Log.d(TAG, "ERROR!: Cannot create and update user in Firebase. Error Message --> ${error.message}.")

            }

        }

    }

}