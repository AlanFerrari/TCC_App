package br.com.etecia.meus_direitos;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class Login extends AppCompatActivity {

    EditText edtEmail;
    EditText edtSenha;
    ImageView voltar;
    TextView esqueciSenha, cadastrarUsurario;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        voltar = findViewById(R.id.imgVoltar);
        esqueciSenha = findViewById(R.id.txtesqueciSenha);
        cadastrarUsurario = findViewById(R.id.txtIrCadastrar);

        login = findViewById(R.id.btnLogar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtEmail.getText().toString()) || TextUtils.isEmpty(edtSenha.getText().toString())){

                    Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();

                } else {

                    DBHelper db = new DBHelper(getApplicationContext());
                    PerfilUsuario perfilUsuario = new PerfilUsuario();
                    if (edtEmail.equals(perfilUsuario.getEmail()) || edtSenha.equals(perfilUsuario.getSenha())){

                        Intent intent = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);

                        db.autenticaUsuario(perfilUsuario);
                        db.close();

                        startActivity(intent);
                        finish();
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
