package com.food4health.presentation.Register.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.food4health.base.BaseActivity
import com.food4health.presentation.Main.View.MainActivity
import com.food4health.presentation.Register.Presenter.RegisterPresenter
import com.food4health.presentation.Register.RegisterContract
import com.sinergia.food4health.R
import kotlinx.android.synthetic.main.activity_register.*

class Register : BaseActivity(), RegisterContract.RegisterView {

    lateinit var registerPresenter: RegisterContract.RegisterPresenter

    // BASE ACTIVITY IMPLEMENTS METHODS
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        registerPresenter = RegisterPresenter()
        registerPresenter.attachView(this)

    }

    override fun getLayout(): Int {
        return R.layout.activity_register
    }

    override fun getPageTitle(): String {
        return getString(R.string.PG_REGISTER)
    }

    // REGISTER VIEW METHODS
    override fun showError(error: String) {
        toastL(this, error)
    }

    override fun showMessage(message: String) {
        toastL(this, message)
    }

    override fun showProgressBar() {
        register_progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        register_progressBar.visibility = View.INVISIBLE
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
        val userFirstLastName: String = register_userFistrLastName.text.toString()
        val userSecondLastName: String = register_userSecondLastName.text.toString()
        val userNIF: String = register_userNIF.text.toString()
        val userEmail: String = register_userMail.text.toString()
        val userPassword: String = register_userPass1.text.toString()
        val userRepeatPassword: String = register_userPass2.text.toString()

        if(){
            if(registerPresenter.checkEmptyRegisterName(userName)){
                register_userName.error = "¡Cuidado! Te has dejado vacío el campo 'nombre'."
            }
        } else {

        }


    }


}
