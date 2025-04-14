package com.example.trabalhofinal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var textViewCronometro: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnReset: Button
    private lateinit var btnSave: Button
    private lateinit var btnView: Button
    private lateinit var editTextNome: EditText

    private var segundos = 0
    private var rodando = false
    private val handler = Handler(Looper.getMainLooper())

    private val runnable = object : Runnable {
        override fun run() {
            if (rodando) {
                segundos++
                textViewCronometro.text = formatarTempo(segundos)
                handler.postDelayed(this, 1000)
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNome = findViewById(R.id.editTextNome)
        textViewCronometro = findViewById(R.id.textViewCronometro)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnReset = findViewById(R.id.btnReset)
        btnSave = findViewById(R.id.btnSave)
        btnView = findViewById(R.id.btnView)

        btnStart.setOnClickListener {
            if (!rodando) {
                rodando = true
                handler.post(runnable)
            }
        }

        btnStop.setOnClickListener {
            rodando = false
        }

        btnReset.setOnClickListener {
            rodando = false
            segundos = 0
            textViewCronometro.text = formatarTempo(segundos)
        }

        btnSave.setOnClickListener {
            val tempo = formatarTempo(segundos)
            val nome = editTextNome.text.toString()

            if (nome.isNotBlank()) {
                salvarTempo(nome, tempo)
            } else {
                Toast.makeText(this, "Digite um nome antes de salvar", Toast.LENGTH_SHORT).show()
            }
        }

        btnView.setOnClickListener {
            val intent = Intent(this, RegistrosActivity::class.java)
            startActivity(intent)
        }
    }

    private fun formatarTempo(segundos: Int): String {
        val h = segundos / 3600
        val m = (segundos % 3600) / 60
        val s = segundos % 60
        return String.format("%02d:%02d:%02d", h, m, s)
    }

    private fun salvarTempo(nome: String, tempo: String) {
        val dao = AppDatabase.getDatabase(this).tempoDao()
        val tempoRegistrado = TempoRegistrado(nome = nome, tempo = tempo)

        lifecycleScope.launch {
            dao.inserirTempo(tempoRegistrado)
            runOnUiThread {
                Toast.makeText(this@MainActivity, "Tempo '$nome' salvo no banco: $tempo", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
