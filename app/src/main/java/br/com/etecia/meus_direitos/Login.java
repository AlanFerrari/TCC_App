package br.com.etecia.meus_direitos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class Login extends AppCompatActivity {

    EditText edtUsuario;
    EditText edtSenha;
    ImageView voltar;
    TextView esqueciSenha, cadastrarUsurario;
    Button login;
    DBHelper DB;

    String[] mensagens = {"Preencha todos os campos", "Login feito com sucesso", "Falha ao fazer Login"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsuario = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        voltar = findViewById(R.id.imgVoltar);
        esqueciSenha = findViewById(R.id.txtesqueciSenha);
        cadastrarUsurario = findViewById(R.id.txtIrCadastrar);

        login = findViewById(R.id.btnLogar);
        DB = new DBHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edtUsuario.getText().toString();
                String senha = edtSenha.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)){
                    Snackbar snackbar = Snackbar.make(view, mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {
                    Boolean checandoUsuario = DB.checandoEmailESenhaDoUsuario(email, senha);
                    if (checandoUsuario == true){
                        Snackbar snackbar = Snackbar.make(view, mensagens[1],Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                        Intent intent = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Snackbar snackbar = Snackbar.make(view, mensagens[2],Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                    }
                }
            }
        });

        cadastrarUsurario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CadastroAdvogado.class);
                startActivity(intent);
                finish();
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Entrar_Como.class);
                startActivity(intent);
                finish();
            }
        });
        esqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SolicitarNovaSenha.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
