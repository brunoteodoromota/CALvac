package com.example.calvac

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vicmikhailau.maskededittext.MaskedEditText
import java.util.*

class CadastrarPessoaActivity : AppCompatActivity() {
    private lateinit var buttonVoltar: Button
    private lateinit var editTextNome: EditText
    private lateinit var maskedEditTextDataNasc: MaskedEditText
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var switchGestante: Switch
    private var gravida: Boolean = false
    private lateinit var buttonSalvarDados: Button

    companion object {
        private const val READ_CALENDAR = 100
        private const val WRITE_CALENDAR = 101
        private const val LEITURA_E_ESCRITA_CALENDAR = 102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_pessoa)

        buttonVoltar = findViewById(R.id.button_voltar_cadastro)
        editTextNome = findViewById(R.id.editText_nome)
        maskedEditTextDataNasc = findViewById(R.id.maskedEditText_data_nascimento)
        switchGestante = findViewById(R.id.switch_gestante)
        buttonSalvarDados = findViewById(R.id.button_salvar_dados)

        buttonVoltar.setOnClickListener {
            val intent = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intent)

        }

        campoData()


        maskedEditTextDataNasc.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                datePickerDialog.show()
                return false
            }

        })

        switchGestante.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gravida = true
                maskedEditTextDataNasc.setEnabled(false)
            } else {
                maskedEditTextDataNasc.setEnabled(true)
                gravida = false
            }

        }

        checarPermissoes()

    }

    private fun campoData() {
        val newCalendar: Calendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(
            this,
            { view, ano, mes, dia ->
                val newDate: Calendar = Calendar.getInstance()
                newDate.set(ano, mes, dia)
                val sd = SimpleDateFormat("dd/MM/yyyy")
                val startDate: Date = newDate.getTime()
                val dataFormatada: String = sd.format(startDate)
                maskedEditTextDataNasc.setText(dataFormatada)
            },
            newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis())
    }

    fun checarPermissoes() {
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_CALENDAR)
            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_CALENDAR)
            == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Escrita e Leitura de Calendário Permitidas", Toast.LENGTH_SHORT).show()
        } else {
            solicitarPermissoesCalendario()
        }
    }

    fun solicitarPermissoesCalendario() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR),
            LEITURA_E_ESCRITA_CALENDAR
        )

        Toast.makeText(this, "Escrita e Leitura de Calendário Permitidas", Toast.LENGTH_SHORT).show()
    }

}

