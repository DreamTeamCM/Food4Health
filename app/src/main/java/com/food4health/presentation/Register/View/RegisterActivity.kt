package com.food4health.presentation.Register.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.food4health.base.BaseActivity
import com.food4health.data.Model.User
import com.food4health.domain.Interactors.Register.RegisterInteractorImpl
import com.food4health.presentation.Main.View.MainActivity
import com.food4health.presentation.Register.Model.RegisterViewModelImpl
import com.food4health.presentation.Register.Presenter.RegisterPresenter
import com.food4health.presentation.Register.RegisterContract
import com.sinergia.food4health.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity(), RegisterContract.RegisterView {

    lateinit var registerPresenter: RegisterContract.RegisterPresenter

    // BASE ACTIVITY IMPLEMENTS METHODS
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        registerPresenter = RegisterPresenter(RegisterInteractorImpl(), RegisterViewModelImpl())
        registerPresenter.attachView(this)

        register_button.setOnClickListener { register() }

    }

    override fun getLayout(): Int {
        return R.layout.activity_register
    }

    override fun getPageTitle(): String {
        return getString(R.string.PG_REGISTER)
    }

    // REGISTER VIEW METHODS
    override fun showError(error: Int) {
        toastL(this, getString(error))
    }

    override fun showMessage(message: Int) {
        toastL(this, getString(message))
    }

    override fun showRegisterProgressBar() {
        login_progressBar.visibility = View.VISIBLE
        login_progressBar.smoothToShow()
    }

    override fun hideRegisterProgressBar() {
        login_progressBar.visibility = View.INVISIBLE
        login_progressBar.smoothToHide()
    }

    override fun enableRegisterButton() {
        register_button.isClickable = true
        register_button.isEnabled = true
    }

    override fun disableRegisterButton() {
        register_button.isClickable = false
        register_button.isEnabled = false
    }

    override fun navigateToLogin() {
        val loginIntent = Intent(this, MainActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(loginIntent)
    }

    override fun register() {

        val userName: String = register_userName.text.toString()
        val userFirstLastName: String = register_userFirstLastName.text.toString()
        val userSecondLastName: String = register_userSecondLastName.text.toString()
        val userNIF: String = register_userNIF.text.toString()
        val userEmail: String = register_userMail.text.toString()
        val userPassword: String = register_userPass1.text.toString()
        val userRepeatPassword: String = register_userPass2.text.toString()

        if(registerPresenter.checkEmptyFields(userName, userFirstLastName, userSecondLastName, userNIF, userEmail, userPassword, userRepeatPassword)){

            if(registerPresenter.checkEmptyRegisterName(userName)){
                register_userName.error = "¡Cuidado! Te has dejado vacío el campo 'nombre'."
            }

            if(registerPresenter.checkEmptyRegisterFirstLastName(userFirstLastName)){
                register_userFirstLastName.error = "¡Cuidado! Te has dejado vacío el campo 'Primer Apellido'."
            }

            if(registerPresenter.checkEmptyRegisterSecondLastName(userSecondLastName)){
                register_userSecondLastName.error = "¡Cuidado! Te has dejado vacío el campo 'Segundo Apellido'."
            }

            if(registerPresenter.checkEmptyRegisterNIF(userNIF)){
                register_userNIF.error = "¡Cuidado! Te has dejado vacío el campo 'NIF'."
            }

            if(registerPresenter.checkEmptyRegisterEmail(userEmail)){
                register_userMail.error = "¡Cuidado! Te has dejado vacío el campo 'Correo Electrónico'."
            }

            if(registerPresenter.checkEmptyRegisterPassword(userPassword)){
                register_userPass1.error = "¡Cuidado! Te has dejado vacío el campo 'Contraseña'."
            }

            if(registerPresenter.checkEmptyRegisterRepeatPassword(userRepeatPassword)){
                register_userPass2.error = "¡Cuidado! Te has dejado vacío el campo 'Repetir Contraseña'."
            }

        } else {

            if(!registerPresenter.checkValidRegisterEmail(userEmail)){
                register_userMail.error = "¡Cuidado! No has introducido el correo electrónico en formato válido."
                return
            }

            if(!registerPresenter.checkRegisterPasswordMatch(userPassword, userRepeatPassword)){
                register_userPass2.error = "¡Cuidado! Las contraseñas no coinciden."
                return
            }

            val newUser = User(userName, userFirstLastName, userSecondLastName, userNIF, userEmail)
            registerPresenter.registerWithEmailAndPassword(newUser, userPassword)

        }

    }


}
