package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.MainActivity2Binding

class MainActivity_2 : AppCompatActivity() {

    private lateinit var binding: MainActivity2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Aktifkan fitur Edge-to-Edge agar tampilan memenuhi layar (sama seperti MainActivity)
        enableEdgeToEdge()

        // 2. Inisialisasi View Binding
        binding = MainActivity2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3. Mengatur padding agar konten tidak tertutup status bar atau navigasi bar
        // Pastikan di layout XML Anda, root layout memiliki ID 'main2' (atau sesuaikan namanya)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main2) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 4. Setup Toolbar jika Anda ingin menggunakan Action Bar di activity kedua
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Tombol "Back" di kiri atas
    }

    // Fungsi untuk membuat tombol "Back" di Toolbar berfungsi
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}