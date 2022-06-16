package br.com.etecia.meus_direitos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RedefinirSenha extends AppCompatActivity {

    ImageView voltar;
    Button redefinirSenha;
    EditText edtNovaSenha;
    EditText edtRepetirSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        voltar = findViewById(R.id.imgVoltar);
        redefinirSenha = findViewById(R.id.btnRedefinirSenha);
        edtNovaSenha = findViewById(R.id.nova_senha);
        edtRepetirSenha = findViewById(R.id.repetir_nova_senha);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RedefinirSenha.this, Login.class);
                startActivity(intent);
                RedefinirSenha.this.finish();
            }
        });

        redefinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RedefinirSenha.this, Login.class);

                intent.putExtra("novaSenha", edtNovaSenha.getText().toString());
                intent.putExtra("repetirSenha", edtRepetirSenha.getText().toString());

                startActivity(intent);
                RedefinirSenha.this.finish();
            }
        });
    }
}