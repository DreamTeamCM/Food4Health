package com.food4health.presentation.Register

import com.food4health.data.Model.User

interface RegisterContract {

    interface RegisterView{

        fun showError(error: String)
        fun showMessage(message: String)
        fun showProgressBar()
        fun hideProgressBar()
        fun enableRegisterButton()
        fun disableRegisterButton()
        fun navigateToLogin()
        fun register()

    }

    interface RegisterPresenter{

        fun attachView(view: RegisterView)
        fun dettachView()
        fun isViewAttached(): Boolean

        fun checkEmptyRegisterName(userName: String): Boolean
        fun checkEmptyRegisterFirstLastName(firstLastName: String): Boolean
        fun checkEmptyRegisterSecondLastName(secondLastName: String): Boolean
        fun checkEmptyRegisterNIF(nif: String):Boolean
        fun checkEmptyRegisterEmail(email: String): Boolean
        fun checkEmptyRegisterPassword(password: String): Boolean
        fun checkEmptyRegisterRepeatPassword(repeatPassword: String): Boolean
        fun checkValidRegisterEmail(email: String): Boolean
        fun checkRegisterPasswordMatch(password: String, repeatPassword: String): Boolean

        fun registerWithEmailAndPassword(user: User, password: String)

    }

}