package br.com.etecia.meus_direitos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Entrar_Como extends AppCompatActivity {

    CardView entrarCliente, entrarAdvogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_como);

        entrarAdvogado = findViewById(R.id.EntrarAdvogado);
        entrarCliente = findViewById(R.id.EntrarCliente);

        entrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cardCliente = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(cardCliente);
                finish();
            }
        });

        entrarAdvogado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cardAdvogado = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);
                startActivity(cardAdvogado);
                finish();
            }
        });

    }
}