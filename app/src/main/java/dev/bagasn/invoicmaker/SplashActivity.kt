package dev.bagasn.invoicmaker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

    }

    override fun onResume() {
        super.onResume()

        Thread {
            try {
                Thread.sleep(1500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                moveToMain();
            }
        }.start()
    }

    override fun onBackPressed() {

    }

    private fun moveToMain() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)

        finish()
    }

}