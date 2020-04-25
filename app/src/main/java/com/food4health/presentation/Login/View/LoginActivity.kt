package com.food4health.presentation.Login.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.food4health.base.BaseActivity
import com.food4health.domain.Interactors.Login.LoginInteractorImpl
import com.food4health.presentation.Login.LoginContract
import com.food4health.presentation.Login.Model.LoginViewModelImpl
import com.food4health.presentation.Login.Presenter.LoginPresenter
import com.food4health.presentation.Main.View.MainActivity
import com.food4health.presentation.MainMenu.View.MainMenuActivity
import com.sinergia.food4health.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginContract.LoginView {

    lateinit var loginPresenter: LoginContract.LoginPresenter

    // BASE ACTIVITY IMPLEMENTS METHODS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginPresenter = LoginPresenter(LoginInteractorImpl(), LoginViewModelImpl())
        loginPresenter.attachView(this)
        login_button.setOnClickListener { login() }
    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun getPageTitle(): String {
        return getString(R.string.PG_LOGIN)
    }


    // LOGIN VIEW METHODS

    override fun showError(error: Int) {
        toastL(this, getString(error))
    }

    override fun showMessage(message: Int) {
        toastL(this, getString(message))
    }

    override fun showMessage(message: String) {
        toastS(this, message)
    }

    override fun showLoginProgressBar() {
        login_progressBar.visibility = View.VISIBLE
    }

    override fun hideLoginProgressBar() {
        login_progressBar.visibility = View.INVISIBLE
    }

    override fun enableLoginButton() {
        login_button.isClickable = true
        login_button.isEnabled = true
    }

    override fun disableLoginButton() {
        login_button.isClickable = false
        login_button.isEnabled = false
    }

    override fun navigateToMenu() {
        val menuIntent = Intent(this, MainMenuActivity::class.java)
        menuIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(menuIntent)
    }

    override fun login() {
        var email = login_userMail.text.toString()
        var password = login_userPass.text.toString()

        if (loginPresenter.checkEmptyFields(email,password)) {

            if (loginPresenter.checkEmptyLoginEmail(email)) {
                login_userMail.error =
                    "¡Cuidado! Te has dejado vacío el campo 'Correo Electrónico'."
            }

            if (loginPresenter.checkEmptyLoginPassword(password)) {
                login_userPass.error = "¡Cuidado! Te has dejado vacío el campo 'Contraseña'."
            }
        } else {

            if (!loginPresenter.checkValidLoginEmail(email)) {
                login_userMail.error =  "¡Cuidado! No has introducido el correo electrónico en formato válido."
                return
            }
            loginPresenter.loginWithEmailAndPassword(email,password)
        }
    }
}
