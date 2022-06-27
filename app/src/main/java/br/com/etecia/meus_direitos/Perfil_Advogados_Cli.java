package br.com.etecia.meus_direitos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class Perfil_Advogados_Cli extends AppCompatActivity {

    MaterialToolbar toolbar;
    TextView txtNomeAdvogado;
    TextView txtEmail;
    TextView txtTelefone;
    TextView txtAreaAtuacao;
    TextView txtCidade;
    TextView txtEstado;
    TextView txtRegistro;
    TextView txtBibliografia;
    Button botaoWhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_advogados_cli);

        txtNomeAdvogado =  findViewById(R.id.nomeAdvogadoCli);
        txtEmail =  findViewById(R.id.emailCli);
        txtTelefone =  findViewById(R.id.telefoneCli);
        txtCidade = findViewById(R.id.cidadeCli);
        txtEstado = findViewById(R.id.estadoCli);
        txtRegistro = findViewById(R.id.registroOABCli);
        txtBibliografia = findViewById(R.id.bibliografiaCli);
        txtAreaAtuacao = findViewById(R.id.area_atuacaoCli);
        botaoWhatsapp = findViewById(R.id.Whatsapp);


        //Recebendo dados do usuario
        PerfilUsuario perfilUsuario = new PerfilUsuario();

        perfilUsuario.setNome(txtNomeAdvogado.getText().toString());
        perfilUsuario.setEmail(txtEmail.getText().toString());
        perfilUsuario.setTelefone(txtTelefone.getText().toString());
        perfilUsuario.setEstado(txtEstado.getText().toString());
        perfilUsuario.setCidade(txtCidade.getText().toString());
        perfilUsuario.setNumeroOAB(txtRegistro.getText().toString());
        perfilUsuario.setAreaAtuacao(txtAreaAtuacao.getText().toString());
        perfilUsuario.setBibliografia(txtBibliografia.getText().toString());


        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent1);
            finish();
        });

        String fone = txtTelefone.getText().toString();
        botaoWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone=" + "055" + fone;
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