package br.com.etecia.meus_direitos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

import br.com.etecia.meus_direitos.objetos.User;

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

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Login.class));
        }


        txtNomeAdvogado =  findViewById(R.id.nomeAdvogado);
        txtEmail =  findViewById(R.id.email);
        txtTelefone =  findViewById(R.id.telefone);
        txtCidade = findViewById(R.id.cidade);
        txtEstado = findViewById(R.id.estado);
        txtRegistro = findViewById(R.id.registroOAB);
        EditarPerfil = findViewById(R.id.btnEditarPerfil);

        User user = SharedPrefManager.getInstance(this).getUser();


        txtNomeAdvogado.setText(user.getUsuario());
        txtEmail.setText(user.getEmail());
        txtTelefone.setText(user.getTelefone_cel());
        txtCidade.setText(user.getCidade());
        txtEstado.setText(user.getEstado());
        txtRegistro.setText(user.getNumero_oab());

        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);
            startActivity(intent);
            finish();
        });

        //quando o usuário pressiona o botão de sair
        //chamando o método de logout
        findViewById(R.id.btnSair).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                finish();
            }
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
                Intent intent2 = new Intent(this, InformacoesCli.class);
                startActivity(intent2);
                break;
        }

        return true;
    }
}