package br.com.etecia.meus_direitos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class PerfilAdvogado_Adv extends AppCompatActivity {

    Button editarPerfil;
    MaterialToolbar toolbar;
    ImageView fotoPerfil;
    TextView txtNomeAdvogado,txtEmail, txtTelefone, txtAreaAtuacao, txtCidade, txtEstado, txtRegistro, txtBibliografia;
    PerfilUsuario perfilUsuario = new PerfilUsuario();
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_advogado_adv);

        db = new DB(this);
        DBHelper DB = new DBHelper(this);
        Intent intent = getIntent();

        PerfilUsuario perfilUsuario = (PerfilUsuario) intent.getSerializableExtra("perfilUsuario");

        txtNomeAdvogado = findViewById(R.id.nomeAdvogado);
        txtEmail = findViewById(R.id.email);
        txtTelefone = findViewById(R.id.telefone);
        txtAreaAtuacao = findViewById(R.id.area_atuacao);
        txtCidade = findViewById(R.id.cidade);
        txtEstado = findViewById(R.id.estado);
        txtRegistro = findViewById(R.id.registroOAB);
        txtBibliografia = findViewById(R.id.bibliografia);
        editarPerfil = findViewById(R.id.btnEditarPerfil);
        fotoPerfil = findViewById(R.id.fotoPerfil);

        //Recebendo dados do usuario
        txtNomeAdvogado.setText(perfilUsuario.getNome());
        txtEmail.setText(perfilUsuario.getEmail());
        txtTelefone.setText(perfilUsuario.getTelefone());
        txtAreaAtuacao.setText(perfilUsuario.getAreaAtuacao());
        txtEstado.setText(perfilUsuario.getEstado());
        txtCidade.setText(perfilUsuario.getCidade());
        txtRegistro.setText(perfilUsuario.getNumeroOAB());
        txtBibliografia.setText(perfilUsuario.getBibliografia());


        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);
            startActivity(intent1);
            finish();
        });

        editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), EditarPerfil.class);
                intent1.putExtra("perfilUsuario", String.valueOf(perfilUsuario));
                startActivity(intent1);
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
                Intent intent2 = new Intent(this, InformacoesAdv.class);
                startActivity(intent2);
                break;
        }

        return true;
    }
}