package br.com.etecia.meus_direitos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class PerfilAdvogado_Adv extends AppCompatActivity {

    Button editarPerfil;
    MaterialToolbar toolbar;
    ImageView fotoPerfil;
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
        fotoPerfil = findViewById(R.id.fotoPerfil);

        //Recebendo dados do usuario
        Intent intent = getIntent();

        txtNomeAdvogado.setText(intent.getStringExtra("nome"));
        txtEstado.setText(intent.getStringExtra("estado"));
        txtCidade.setText(intent.getStringExtra("cidade"));
        txtAreaAtuacao.setText(intent.getStringExtra("area_atuacao"));
        fotoPerfil.setImageResource(Integer.parseInt(intent.getStringExtra("imagem")));


    /*    perfilUsuario.setNome(txtNomeAdvogado.getText().toString());
        perfilUsuario.setEmail(txtEmail.getText().toString());
        perfilUsuario.setTelefone(txtTelefone.getText().toString());
        perfilUsuario.setEstado(txtEstado.getText().toString());
        perfilUsuario.setCidade(txtCidade.getText().toString());
        perfilUsuario.setNumeroOAB(txtRegistro.getText().toString());
        perfilUsuario.setAreaAtuacao(txtAreaAtuacao.getText().toString());
        perfilUsuario.setBibliografia(txtBibliografia.getText().toString());

        String nomeUsuario = txtNomeAdvogado.getText().toString();
        String emailUsuario = txtEmail.getText().toString();
        String telefoneUsuario = txtTelefone.getText().toString();
        String estadoUsuario = txtEstado.toString();
        String cidadeUsuario = txtCidade.toString();
        String registroUsuario = txtRegistro.getText().toString();
        String areasUsuario = txtAreaAtuacao.getText().toString();
        String bibliografiaUsuario = txtBibliografia.getText().toString();

        System.out.println(perfilUsuario.getNome());
        System.out.println(perfilUsuario.getEmail());
        System.out.println(perfilUsuario.getTelefone());
        System.out.println(perfilUsuario.getEstado());
        System.out.println(perfilUsuario.getCidade());
        System.out.println(perfilUsuario.getSenha());
        System.out.println(perfilUsuario.getAreaAtuacao());
        System.out.println(perfilUsuario.getBibliografia());

        System.out.println(nomeUsuario);
        System.out.println(areasUsuario);
        System.out.println(bibliografiaUsuario);
        System.out.println(estadoUsuario);
        System.out.println(cidadeUsuario);
        System.out.println(registroUsuario);
        System.out.println(emailUsuario);
        System.out.println(telefoneUsuario);*/

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
                intent1.putExtra("nome",txtNomeAdvogado.getText().toString());
                intent1.putExtra("email",txtEmail.getText().toString());
                intent1.putExtra("telefone",txtTelefone.getText().toString());
                intent1.putExtra("estado",txtEstado.getText().toString());
                intent1.putExtra("cidade",txtCidade.getText().toString());
                intent1.putExtra("numeroOAB",txtRegistro.getText().toString());
                intent1.putExtra("areaAtuacao", txtAreaAtuacao.getText().toString());
                intent1.putExtra("bibliografia",txtBibliografia.getText().toString());
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