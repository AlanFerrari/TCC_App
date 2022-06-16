package br.com.etecia.meus_direitos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SolicitarNovaSenha extends AppCompatActivity {

    ImageView voltar;
    Button SolicitarSenhaNova;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_nova_senha);

        voltar = findViewById(R.id.imgVoltar);
        SolicitarSenhaNova = findViewById(R.id.btnSolicitarSenha);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SolicitarNovaSenha.this, Login.class);
                startActivity(intent);
                SolicitarNovaSenha.this.finish();
            }
        });

        SolicitarSenhaNova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SolicitarNovaSenha.this, RedefinirSenha.class);
                startActivity(intent);
                SolicitarNovaSenha.this.finish();
            }
        });
    }
}