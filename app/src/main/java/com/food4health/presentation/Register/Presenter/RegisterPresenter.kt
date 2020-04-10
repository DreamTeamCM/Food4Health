package com.food4health.presentation.Register.Presenter

import com.food4health.data.Model.User
import com.food4health.presentation.Register.RegisterContract

class RegisterPresenter: RegisterContract.RegisterPresenter{

    var view: RegisterContract.RegisterView ?= null

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

    override fun checkValidRegisterEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun checkRegisterPasswordMatch(password: String, repeatPassword: String): Boolean {
        return password === repeatPassword
    }

    override fun registerWithEmailAndPassword(user: User, password: String) {
        TODO("Not yet implemented")
    }

}