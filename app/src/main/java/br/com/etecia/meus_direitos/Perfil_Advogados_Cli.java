package br.com.etecia.meus_direitos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class Perfil_Advogados_Cli extends AppCompatActivity {

    MaterialToolbar toolbar;
    TextView txtNomeAdvogado;
    TextView txtEmail;
    TextView txtTelefone;
    TextView txtCidade;
    TextView txtEstado;
    TextView txtRegistro;

    Button whatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_advogados_cli);

        txtNomeAdvogado =  findViewById(R.id.nomeAdvogado);
        txtEmail =  findViewById(R.id.email);
        txtTelefone =  findViewById(R.id.telefone);
        txtCidade = findViewById(R.id.cidade);
        txtEstado = findViewById(R.id.estado);
        txtRegistro = findViewById(R.id.registroOAB);
        whatsapp = findViewById(R.id.Whatsapp);

        PerfilUsuario user = SharedPrefManager.getInstance(this).getUser();


        txtNomeAdvogado.setText(user.getNome());
        txtEmail.setText(user.getEmail());
        txtTelefone.setText(user.getTelefone());
        txtCidade.setText(user.getCidade());
        txtEstado.setText(user.getEstado());
        txtRegistro.setText(user.getNumeroOAB());

        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        String fone = txtTelefone.getText().toString();
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone=" + "055" + fone + "&text=Solicito%20Suporte%20Aplicativo%20Ibi-System";
                Intent conversar = new Intent(Intent.ACTION_VIEW);
                conversar.setData(Uri.parse(url));
                startActivity(conversar);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navegacao_cliente, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.informacoes:
                Intent intent2 = new Intent(this, InformacoesCli.class);
                startActivity(intent2);
                break;
        }

        return true;
    }
}