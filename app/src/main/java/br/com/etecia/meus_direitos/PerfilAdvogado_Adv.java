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
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class PerfilAdvogado_Adv extends AppCompatActivity {

    Button editarPerfil;
    MaterialToolbar toolbar;
    TextView txtNomeAdvogado,txtEmail, txtTelefone, txtAreaAtuacao, txtCidade, txtEstado, txtRegistro, txtBibliografia;
    PerfilUsuario perfilUsuario = new PerfilUsuario();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_advogado_adv);

        txtNomeAdvogado = findViewById(R.id.nomeAdvogado);
        txtEmail = findViewById(R.id.email);
        txtTelefone = findViewById(R.id.telefone);
        txtAreaAtuacao = findViewById(R.id.area_atuacao);
        txtCidade = findViewById(R.id.cidade);
        txtEstado = findViewById(R.id.estado);
        txtRegistro = findViewById(R.id.registroOAB);
        txtBibliografia = findViewById(R.id.bibliografia);
        editarPerfil = findViewById(R.id.btnEditarPerfil);

        DBHelper DB = new DBHelper(this);

        String nome = txtNomeAdvogado.getText().toString();
        String email = txtEmail.getText().toString();
        String telefone = txtTelefone.getText().toString();
        String areaAtuacao = txtAreaAtuacao.getText().toString();
        String estado = txtEstado.getText().toString();
        String cidade = txtCidade.getText().toString();
        String registroOAB = txtRegistro.getText().toString();
        String bibliografia = txtBibliografia.getText().toString();

            Boolean perfil = DB.PegarDadosDoBanco(nome, email, telefone, areaAtuacao, estado, cidade, registroOAB, bibliografia);

            if (perfil == true){

                perfilUsuario.setNome(txtNomeAdvogado.getText().toString());
                perfilUsuario.setEmail(txtEmail.getText().toString());
                perfilUsuario.setTelefone(Integer.valueOf(txtTelefone.getText().toString()));
                perfilUsuario.setAreaAtuacao(txtAreaAtuacao.getText().toString());
                perfilUsuario.setEstado(txtEstado.getText().toString());
                perfilUsuario.setCidade(txtCidade.getText().toString());
                perfilUsuario.setNumeroOAB(txtRegistro.getText().toString());
                perfilUsuario.setBibliografia(txtBibliografia.getText().toString());


            } else {
                Toast.makeText(this, "Falha na obtenção de dados", Toast.LENGTH_SHORT).show();
            }


        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);
            startActivity(intent);
            finish();
        });

        editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditarPerfil.class);
                startActivity(intent);
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