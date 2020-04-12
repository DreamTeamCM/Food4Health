package com.food4health.presentation.Login

interface LoginContract {
    interface LoginView {

        fun showError(error: Int)
        fun showMessage(message: Int)
        fun showMessage(message: String)
        fun showLoginProgressBar()
        fun hideLoginProgressBar()
        fun enableLoginButton()
        fun disableLoginButton()
        fun navigateToMenu()
        fun login()

    }

    interface LoginPresenter {

        fun attachView(view: LoginView)
        fun dettachView()
        fun isViewAttached(): Boolean

        fun checkEmptyLoginEmail(email: String): Boolean
        fun checkEmptyLoginPassword(password: String): Boolean
        fun checkEmptyFields(email:String, password: String): Boolean

        fun checkValidLoginEmail(email: String): Boolean


        fun loginWithEmailAndPassword(email: String, password: String)


    }

}