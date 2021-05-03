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

    private lateinit var  mAuth: FirebaseAuth
    private lateinit var refUsuarios: DatabaseReference
    private var firebaseIdUsuario: String = ""

    private lateinit var nome: EditText
    private lateinit var login: EditText
    private lateinit var senha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_conta)

        nome = findViewById(R.id.editText_nome) as EditText
        login = findViewById(R.id.editText_login) as EditText
        senha = findViewById(R.id.editText_senha) as EditText

        //fazendo o link entre o botão do layout e da activity
        var button_cadastrar = findViewById(R.id.button_cadastrar) as Button;

        mAuth = FirebaseAuth.getInstance();


        button_cadastrar.setOnClickListener{
            //val intent = Intent(this, MenuPrincipalActivity::class.java).apply{}
            // startActivity(intent)
            cadastrarUsuario()

        }

    }

    private fun cadastrarUsuario(){

        val nomeUsuario : String = nome.text.toString()
        val loginUsuario : String = login.text.toString()
        val senhaUsuario : String = senha.text.toString()

        //Abaixo faz as validações de cada campo se estão em branco ou se foram realmente digitadas.
        if (nomeUsuario == ""){
            nome.setError("Digite um nome.")
            nome.requestFocus()
        }else if (loginUsuario == ""){
            login.setError("Digite um email.")
            login.requestFocus()
        }else if(senhaUsuario == ""){
            senha.setError("Digite uma senha.")
            senha.requestFocus()
        }else{

            mAuth.createUserWithEmailAndPassword(loginUsuario, senhaUsuario)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){

                            firebaseIdUsuario = mAuth.currentUser!!.uid
                            refUsuarios = FirebaseDatabase.getInstance().reference.child("Usuarios").child(firebaseIdUsuario)

                            val usuarioHashMap = HashMap<String,Any>()
                            usuarioHashMap["uid"] = firebaseIdUsuario
                            usuarioHashMap["nome"] = nomeUsuario
                            usuarioHashMap["login"] = loginUsuario

                            refUsuarios.updateChildren(usuarioHashMap)
                                    .addOnCompleteListener { task ->
                                        if(task.isSuccessful){
                                            Toast.makeText(this@CriarContaActivity, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show()

                                            val intent = Intent(this@CriarContaActivity, LoginActivity::class.java)
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                            startActivity(intent)
                                            finish()
                                        }
                                    }

                        }else{
                            //se a criação de erro, exibe a mensagem do erro.
                            Toast.makeText(this@CriarContaActivity, "Erro:" + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                        }
                    }

        }

    }


}



