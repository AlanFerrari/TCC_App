package br.com.etecia.meus_direitos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class Splash extends AppCompatActivity implements Runnable {

    private ProgressBar carregando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        carregando = findViewById(R.id.carregando);

        Handler splash = new Handler();
        splash.postDelayed((Runnable) this, 3000);

    }

    public void run() {
        startActivity(new Intent(this, Entrar_Como.class));
        Splash.this.finish();
    }
}