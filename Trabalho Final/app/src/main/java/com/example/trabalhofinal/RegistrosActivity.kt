package com.example.trabalhofinal

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RegistrosActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var voltarBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registros)

        listView = findViewById(R.id.listViewTempos)
        voltarBtn = findViewById(R.id.btnVoltar)

        voltarBtn.setOnClickListener {
            finish() // Volta para a MainActivity
        }

        val db = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            val tempos = db.tempoDao().buscarTodos()
            val nomesETempos = tempos.map { "${it.nome}: ${it.tempo}" }
            val adapter = ArrayAdapter(this@RegistrosActivity, android.R.layout.simple_list_item_1, nomesETempos)
            listView.adapter = adapter
        }
    }
}
