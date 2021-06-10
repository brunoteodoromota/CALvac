package com.example.calvac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CriarContaActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsuarios: DatabaseReference
    private var firebaseIdUsuario: String = ""

    private lateinit var nome: EditText
    private lateinit var login: EditText
    private lateinit var senha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_conta)

        nome = findViewById(R.id.editText_nome)
        login = findViewById(R.id.editText_login)
        senha = findViewById(R.id.editText_senha)


        var button_cadastrar = findViewById<Button>(R.id.button_cadastrar);

        mAuth = FirebaseAuth.getInstance();


        button_cadastrar.setOnClickListener {
            cadastrarUsuario()

        }

    }

    private fun cadastrarUsuario() {

        val nomeUsuario: String = nome.text.toString()
        val loginUsuario: String = login.text.toString()
        val senhaUsuario: String = senha.text.toString()


        if (nomeUsuario == "") {
            nome.setError("Digite um nome.")
            nome.requestFocus()
        } else if (loginUsuario == "") {
            login.setError("Digite um email.")
            login.requestFocus()
        } else if (senhaUsuario == "") {
            senha.setError("Digite uma senha.")
            senha.requestFocus()
        } else {

            mAuth.createUserWithEmailAndPassword(loginUsuario, senhaUsuario)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        firebaseIdUsuario = mAuth.currentUser!!.uid
                        refUsuarios = FirebaseDatabase.getInstance().reference.child("Usuarios")
                            .child(firebaseIdUsuario)

                        val usuarioHashMap = HashMap<String, Any>()
                        usuarioHashMap["uid"] = firebaseIdUsuario
                        usuarioHashMap["nome"] = nomeUsuario
                        usuarioHashMap["login"] = loginUsuario

                        refUsuarios.updateChildren(usuarioHashMap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this@CriarContaActivity,
                                        "Usuário cadastrado com sucesso!",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    val intent =
                                        Intent(this@CriarContaActivity, LoginActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                }
                            }

                    } else {
                        Toast.makeText(
                            this@CriarContaActivity,
                            "Erro:" + task.exception!!.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

        }

    }


}



