package com.example.calvac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Cal1CriancaActivity : AppCompatActivity() {
    private lateinit var buttonVoltar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cal1_crianca)

        buttonVoltar = findViewById(R.id.button_voltar_crianca) as Button

        buttonVoltar.setOnClickListener{
            val intent = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intent)
        }
    }
}