package com.grl.comunidadesespaa

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import com.grl.comunidadesespaa.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        title = "Comunidades autónomas"
        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                binding.motionLayout.visibility = View.GONE
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }

        })
        setContentView(binding.root)
        prefs = getSharedPreferences("app", MODE_PRIVATE)
        setDefaultValuesIfExists()
        if (prefs.getBoolean("remember", false) && !intent.getBooleanExtra("logout",false)) {
            goToMain()
        }
        binding.loginButton.setOnClickListener {
            if (login()) {
                savePreferences(binding.emailEdit.text.toString(), binding.passEdit.text.toString())
                goToMain()
            }
        }
    }

    private fun setDefaultValuesIfExists() {
        val email = prefs.getString("email", "")
        val pass = prefs.getString("password", "")
        val remember = prefs.getBoolean("remember", false)
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
            binding.emailEdit.setText(email)
            binding.passEdit.setText(pass)
        }
        binding.checkbox.isChecked = remember
    }

    private fun savePreferences(email: String, password: String) {
        val editor = prefs.edit()
        if (binding.checkbox.isChecked) {
            editor.putString("email", email)
            editor.putString("password", password)
            editor.putBoolean("remember", true)
        } else {
            editor.clear()
            editor.putBoolean("remember", false)
        }
        editor.apply()
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun login(): Boolean {
        var output = false
        if (!checkEmail(binding.emailEdit.text.toString())) {
            Toast.makeText(
                this,
                "Usuario incorrecto, introduzca un e-mail válido",
                Toast.LENGTH_SHORT
            ).show()
        } else if (!checkPassword(binding.passEdit.text.toString())) {
            Toast.makeText(
                this,
                "Contraseña no valida. Debe tener al menos 8 caracteres",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            output = true
        }
        return output
    }

    private fun checkEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkPassword(password: String): Boolean {
        return !TextUtils.isEmpty(password)
    }
}