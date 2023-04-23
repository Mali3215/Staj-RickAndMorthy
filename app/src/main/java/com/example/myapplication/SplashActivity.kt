package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.characters.CharacterListActivity
import com.example.myapplication.databinding.ModelTitleBinding
import com.example.myapplication.databinding.SplashScreenBinding
import com.example.myapplication.epoxy.ViewBindingKotlinModel

class SplashActivity: AppCompatActivity() {
    private lateinit var binding: SplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (getSupportActionBar() != null) {
            supportActionBar!!.hide();
        }
        val sharedPrefs = get("my_prefs", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPrefs.getBoolean("is_first_launch", true)
// Eğer uygulama ilk kez açılıyorsa "Welcome!" yazdırıyoruz
        if (isFirstLaunch) {
            binding.text.text = "Welcome!!"
            // SharedPreferences'taki değeri false olarak güncelliyoruz
            val editor = sharedPrefs.edit()
            editor.putBoolean("is_first_launch", false)
            editor.apply()
        } else {
            binding.text.text = "Hello!!"
        }



        // Splash ekranının 3 saniye boyunca görüntülenmesi
        Handler(Looper.getMainLooper()).postDelayed({
            // MainActivity'ye yönlendirme
            startActivity(Intent(this@SplashActivity, CharacterListActivity::class.java))
            finish()
        }, 3000) // 3 saniye

    }



}