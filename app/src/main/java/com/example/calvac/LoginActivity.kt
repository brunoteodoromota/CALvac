package com.example.calvac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var  mAuth: FirebaseAuth
    private lateinit var login: EditText
    private lateinit var senha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login = findViewById(R.id.editText_login) as EditText
        senha = findViewById(R.id.editText_senha) as EditText

        //fazendo o link entre o botÃo do layout e da activity
        var button_login = findViewById(R.id.button_login) as Button;
        var button_cadastrar = findViewById(R.id.button_cadastrar) as Button;



        button_cadastrar.setOnClickListener{
            val intent = Intent(this, CriarContaActivity::class.java).apply{}
            startActivity(intent)

        }

        mAuth = FirebaseAuth.getInstance();

        button_login.setOnClickListener{
            loginUsuario()

        }

    }

    private fun loginUsuario() {
        val loginUsuario : String = login.text.toString()
        val senhaUsuario : String = senha.text.toString()

        //valida se campos foram digitados
        if (loginUsuario == ""){
            Toast.makeText(this@LoginActivity, "Digite um email para login.", Toast.LENGTH_LONG).show()
            login.requestFocus()
        }else if(senhaUsuario == ""){
            Toast.makeText(this@LoginActivity, "Digite uma senha.", Toast.LENGTH_LONG).show()
            senha.requestFocus()
        }else{
            mAuth.signInWithEmailAndPassword(loginUsuario, senhaUsuario)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this@LoginActivity, "Login efetuado com sucesso!",Toast.LENGTH_LONG).show()
                        val intent = Intent(this@LoginActivity, MenuPrincipalActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@LoginActivity, "Erro:" + task.exception!!.message.toString(),Toast.LENGTH_LONG).show()
                    }
                }

        }

    }


}
