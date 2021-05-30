package com.example.calvac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RecuperarSenhaActivity : AppCompatActivity() {
    private lateinit var editEmailRecuperar: EditText
    private lateinit var buttonEviar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_senha)
        editEmailRecuperar = findViewById(R.id.editText_recuperar_email)
        buttonEviar = findViewById(R.id.button_enviar)

        buttonEviar.setOnClickListener {
            val email: String = editEmailRecuperar.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(this, "Digite um email", Toast.LENGTH_SHORT).show()
                editEmailRecuperar.requestFocus()
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Email enviado com sucesso para a redefinição de senha",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }
}