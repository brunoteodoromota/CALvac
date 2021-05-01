package com.example.calvac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var button_cadastrar = findViewById(R.id.button_cadastrar) as Button;
        button_cadastrar.setOnClickListener{
            val intent = Intent(this, CriarContaActivity::class.java).apply{}
            startActivity(intent)

        }
    }
}