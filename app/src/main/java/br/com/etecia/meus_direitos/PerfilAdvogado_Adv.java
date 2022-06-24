package br.com.etecia.meus_direitos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class PerfilAdvogado_Adv extends AppCompatActivity {

    Button EditarPerfil;
    MaterialToolbar toolbar;
    TextView txtNomeAdvogado;
    TextView txtEmail;
    TextView txtTelefone;
    TextView txtCidade;
    TextView txtEstado;
    TextView txtRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_advogado_adv);

        txtNomeAdvogado = findViewById(R.id.nomeAdvogado);
        txtEmail = findViewById(R.id.email);
        txtTelefone = findViewById(R.id.telefone);
        txtCidade = findViewById(R.id.cidade);
        txtEstado = findViewById(R.id.estado);
        txtRegistro = findViewById(R.id.registroOAB);
        EditarPerfil = findViewById(R.id.btnEditarPerfil);

        DBHelper DB = new DBHelper(this);

        String nome = txtNomeAdvogado.getText().toString();
        String email = txtEmail.getText().toString();
        String telefone = txtTelefone.getText().toString();
        String estado = txtEstado.getText().toString();
        String cidade = txtCidade.getText().toString();
        String registroOAB = txtRegistro.getText().toString();

        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(email) || TextUtils.isEmpty(telefone) || TextUtils.isEmpty(estado) || TextUtils.isEmpty(cidade) || TextUtils.isEmpty(registroOAB)){
            Boolean perfil = DB.PegarDadosDoBanco(nome, email, telefone, estado, cidade, registroOAB);
            if (perfil == true){
                txtNomeAdvogado.setText(nome);
                txtEmail.setText(email);
                txtTelefone.setText(telefone);
                txtEstado.setText(estado);
                txtCidade.setText(cidade);
                txtRegistro.setText(registroOAB);
            } else {
                Toast.makeText(this, "Falha na obtenção de dados", Toast.LENGTH_SHORT).show();
            }
        }

        /*String nome = getIntent().getStringExtra("nome");

        String email = getIntent().getStringExtra("email");

        String telefone = getIntent().getStringExtra("telefone");

        String estado = getIntent().getStringExtra("estado");

        String cidade = getIntent().getStringExtra("cidade");

        String registroOAB = getIntent().getStringExtra("numeroOAB");
        */

        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navegacao_advogado, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.informacoes:
                Intent intent2 = new Intent(this, InformacoesAdv.class);
                startActivity(intent2);
                break;
        }

        return true;
    }
}