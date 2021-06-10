package com.example.calvac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuPrincipalActivity : AppCompatActivity() {
    private lateinit var buttonSair: Button
    private lateinit var buttonCrianca: Button
    private lateinit var buttonAdolescente: Button
    private lateinit var buttonAdulto: Button
    private lateinit var buttonIdoso: Button
    private lateinit var buttonGestante: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        buttonSair = findViewById(R.id.button_sair)
        buttonCrianca = findViewById(R.id.button_crianca)
        buttonAdolescente = findViewById(R.id.button_adolescente)
        buttonAdulto = findViewById(R.id.button_adulto)
        buttonIdoso = findViewById(R.id.button_idoso)
        buttonGestante = findViewById(R.id.button_gestante)

        buttonSair.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        buttonCrianca.setOnClickListener {
            val intent = Intent(this, Cal1CriancaActivity::class.java)
            startActivity(intent)
        }

        buttonAdolescente.setOnClickListener {
            val intent = Intent(this, Cal2AdolescenteActivity::class.java)
            startActivity(intent)
        }

        buttonAdulto.setOnClickListener {
            val intent = Intent(this, Cal3AdultoActivity::class.java)
            startActivity(intent)
        }

        buttonIdoso.setOnClickListener {
            val intent = Intent(this, Cal4IdosoActivity::class.java)
            startActivity(intent)
        }

        buttonGestante.setOnClickListener{
            val intent = Intent (this, Cal5GestanteActivity::class.java)
            startActivity(intent)
        }

    }
}