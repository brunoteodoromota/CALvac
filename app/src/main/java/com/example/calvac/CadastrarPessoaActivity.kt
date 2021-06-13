package com.example.calvac

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vicmikhailau.maskededittext.MaskedEditText
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
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
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_CALENDAR
            )
            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_CALENDAR
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Escrita e Leitura de Calendário Permitidas", Toast.LENGTH_SHORT)
                .show()
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

        Toast.makeText(this, "Escrita e Leitura de Calendário Permitidas", Toast.LENGTH_SHORT)
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calendarioVacinacaoCrianca(dataNascFormatada: LocalDate, nome: String) {
        adicionarEvento(this, checarDataVacDataAtual(dataNascFormatada), "BCG", "Dose: Única", nome)

        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada),
            "Hepatite B",
            "Dose: Primeira",
            nome
        )

        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(2)),
            "Pentavalente",
            "Dose: Primeira",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(4)),
            "Pentavalente",
            "Dose: Segunda",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(6)),
            "Pentavalente",
            "Dose: Terceira",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(15)),
            "Pentavalente",
            "Dose: Primeiro Reforço",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusYears(4)),
            "Pentavalente",
            "Dose: Segundo Reforço",
            nome
        )

        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(2)),
            "VIP/VOP",
            "Dose: Primeira",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(4)),
            "VIP/VOP",
            "Dose: Segunda",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(6)),
            "VIP/VOP",
            "Dose: Terceira",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(15)),
            "VIP/VOP",
            "Dose: Primeiro Reforço",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusYears(4)),
            "VIP/VOP",
            "Dose: Segundo Reforço",
            nome
        )

        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(2)),
            "Pneumocócica",
            "Dose: Primeira",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(4)),
            "Pneumocócica",
            "Dose: Segunda",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(6)),
            "Pneumocócica",
            "Dose: Terceira",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(12)),
            "Pneumocócica",
            "Dose: Reforço",
            nome
        )

        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(2)),
            "Rotavírus Humano",
            "Dose: Primeira",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(4)),
            "Rotavírus Humano",
            "Dose: Segunda",
            nome
        )

        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(3)),
            "Meningocócica C",
            "Dose: Primeira",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(5)),
            "Meningocócica C",
            "Dose: Segunda",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(15)),
            "Meningocócica C",
            "Dose: Reforço",
            nome
        )

        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(9)),
            "Febre Amarela",
            "Dose: Primeira",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusYears(4)),
            "Febre Amarela ",
            "Dose: Segunda",
            nome
        )

        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(12)),
            "Hepatite A",
            "Dose: Única",
            nome
        )

        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(12)),
            "Tríplice Viral",
            "Dose: Primeira",
            nome
        )

        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusMonths(15)),
            "Tetra Viral",
            "Dose: Única",
            nome
        )

        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusYears(9)),
            "HPV",
            "Dose: 3 (Dos 9 aos 11 anos)",
            nome
        )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calendarioVacinacaoAdolescente(dataNascFormatada: LocalDate, nome: String) {

        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusYears(10)),
            "Hepatite B",
            "Dose: 3 (Dependendo da situação vacinal)",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusYears(10)),
            "Febre Amarela",
            "Dose: Uma e um reforço (Dependendo da situação vacinal)",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusYears(10)),
            "Tríplice Viral",
            "Dose: 2",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusYears(10)),
            "HPV",
            "Dose: 3 (Dos 9 aos 11)",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusYears(10)),
            "Duplo Adulto",
            "Dose: Reforço (A cada 10 anos)",
            nome
        )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calendarioVacinacaoAdulto(dataNascFormatada: LocalDate, nome: String) {

        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusYears(20)),
            "Hepatite B",
            "Dose: 3 (Dependendo da situação vacinal)",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusYears(20)),
            "Febre Amarela",
            "Dose: Uma e um reforço (Dependendo da situação vacinal)",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusYears(20)),
            "Tríplice Viral",
            "Dose: 1 (até os 49)",
            nome
        )
        adicionarEvento(
            this,
            checarDataVacDataAtual(dataNascFormatada.plusYears(20)),
            "Duplo Adulto",
            "Dose: Reforço (A cada 10 anos)",
            nome
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calendarioVacinacaoGestante(nome: String) {

        var dataAtual: LocalDate = LocalDate.now()

        adicionarEvento(this, dataAtual, "Hepatite B", "Dose: Uma", nome)

        adicionarEvento(this, dataAtual, "Dupla Adulto", "Dose: Reforço", nome)

        adicionarEvento(this, dataAtual, "dTpa", "Dose: 1 de 27ª a 36ª semana de gestação", nome)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checarDataVacDataAtual(dataVacina: LocalDate): LocalDate {
        var dataVacinaCalc: LocalDate
        var dataAtual: LocalDate = LocalDate.now()

        var diferencaDatas: Int = dataVacina.compareTo(dataAtual)
        if (diferencaDatas <= 0) {
            dataVacinaCalc = dataAtual.plusDays(1)
        } else {
            dataVacinaCalc = dataVacina
        }


        return dataVacinaCalc
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun adicionarEvento(
        context: Context,
        dataVacina: LocalDate,
        nomeVacina: String,
        detalheVacina: String,
        nome: String
    ) {
        try {
            val cal = Calendar.getInstance()
            cal.set(dataVacina.year, dataVacina.monthValue, dataVacina.dayOfMonth)

            var dataVacinaHora: LocalDateTime = dataVacina.atTime(12, 0)
            var dataInMillis: Long =
                dataVacinaHora.atOffset(ZoneOffset.ofHours(0)).toInstant().toEpochMilli()

            val titulo: String = "CALvac: " + nome + " - " + nomeVacina
            val descricao: String = detalheVacina
            val cr = context.contentResolver
            val values = ContentValues()
            values.put(CalendarContract.Events.DTSTART, dataInMillis)
            values.put(CalendarContract.Events.DTEND, dataInMillis)
            values.put(CalendarContract.Events.TITLE, titulo)
            values.put(CalendarContract.Events.DESCRIPTION, descricao)
            values.put(CalendarContract.Events.CALENDAR_ID, getCalendarId(context))
            values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().timeZone.id)

            val uri = cr.insert(CalendarContract.Events.CONTENT_URI, values)

            val idEvento = uri!!.lastPathSegment!!.toLong()

            setarLembrete(cr, idEvento, 0)
            Toast.makeText(
                baseContext,
                "Lembrete da vacina: " + nomeVacina + " cadastrada com sucesso",
                Toast.LENGTH_SHORT
            ).show()


        } catch (se: SecurityException) {
            se.printStackTrace()
            Toast.makeText(baseContext, "Erro: " + se.message, Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCalendarId(context: Context): Long? {
        val projection = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
        )

        var calCursor = context.contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            projection,
            CalendarContract.Calendars.VISIBLE + " = 1 AND " + CalendarContract.Calendars.IS_PRIMARY + " = 1 ",
            null,
            CalendarContract.Calendars._ID + " ASC"
        )

        if (calCursor != null && calCursor.count <= 0) {
            calCursor = context.contentResolver.query(
                CalendarContract.Calendars.CONTENT_URI,
                projection,
                CalendarContract.Calendars.VISIBLE + " = 1 ",
                null,
                CalendarContract.Calendars._ID + " ASC"
            )
        }

        if (calCursor != null) {
            if (calCursor.moveToFirst()) {
                val calNome: String
                val calID: String
                val nomeCol = calCursor.getColumnIndex(projection[1])
                val idCol = calCursor.getColumnIndex(projection[0])

                calNome = calCursor.getString(nomeCol)
                calID = calCursor.getString(idCol)

                println("Nome do calendário: " + calNome + "Id do Calendario: " + calID)

                calCursor.close()
                return calID.toLong()
            }
        }
        return null
    }

    fun setarLembrete(cr: ContentResolver, idEvento: Long, timeBefore: Int) {
        try {
            val values = ContentValues()
            values.put(CalendarContract.Reminders.MINUTES, timeBefore)
            values.put(CalendarContract.Reminders.EVENT_ID, idEvento)
            values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
            cr.insert(CalendarContract.Reminders.CONTENT_URI, values)
            val c = CalendarContract.Reminders.query(
                cr,
                idEvento,
                arrayOf(CalendarContract.Reminders.MINUTES)
            )
            if (c.moveToFirst()) {
                println("calendar" + c.getInt(c.getColumnIndex(CalendarContract.Reminders.MINUTES)))
            }
            c.close()
        } catch (se: SecurityException) {
            se.printStackTrace()
            Toast.makeText(baseContext, "Erro: " + se.message, Toast.LENGTH_LONG).show()
        }
    }


}

