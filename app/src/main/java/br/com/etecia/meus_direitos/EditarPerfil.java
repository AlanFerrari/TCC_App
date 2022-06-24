package br.com.etecia.meus_direitos;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class EditarPerfil extends AppCompatActivity {

    ImageView voltar, fotoPerfil;
    TextView editarFoto;
    EditText nomeAdvogado, email, telefone, registroOAB, bibliografia;
    Chip civil, consumidor, trabalhista, penal, empresarial, ambiental, ti, contratual, tributario;
    ArrayList<String> selectedChipData;
    Button alterarPerfil;
    Spinner estado, cidade;
    String ultimoCaracterDigitado = "";

    DB db;
    PerfilUsuario perfilUsuario = new PerfilUsuario();
    ArrayList<String> areaAtuacao = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        voltar = findViewById(R.id.imgVoltar);
        fotoPerfil = findViewById(R.id.fotoPerfil);
        nomeAdvogado = findViewById(R.id.nomeAdvogado);
        email = findViewById(R.id.email);
        telefone = findViewById(R.id.telefone);
        cidade = findViewById(R.id.alterarCidade);
        estado = findViewById(R.id.alterarEstado);
        registroOAB = findViewById(R.id.registroOAB);
        bibliografia = findViewById(R.id.bibliografia);

        editarFoto = findViewById(R.id.editarFoto);

        civil = findViewById(R.id.chipCivil);
        consumidor = findViewById(R.id.chipConsumidor);
        trabalhista = findViewById(R.id.chipTrabalhista);
        penal = findViewById(R.id.chipPenal);
        empresarial = findViewById(R.id.chipEmpresarial);
        ambiental = findViewById(R.id.chipAmbiental);
        ti = findViewById(R.id.chipTI);
        contratual = findViewById(R.id.chipContratual);
        tributario = findViewById(R.id.chipTributário);

        alterarPerfil = findViewById(R.id.AlterarPerfil);
        selectedChipData = new ArrayList<>();
        db = new DB(this);

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    selectedChipData.add(buttonView.getText().toString());
                    areaAtuacao.add(String.valueOf(selectedChipData));
                } else {
                    selectedChipData.remove(buttonView.getText().toString());
                    areaAtuacao.remove(String.valueOf(selectedChipData));
                }
            }
        };

        civil.setOnCheckedChangeListener(checkedChangeListener);
        consumidor.setOnCheckedChangeListener(checkedChangeListener);
        trabalhista.setOnCheckedChangeListener(checkedChangeListener);
        penal.setOnCheckedChangeListener(checkedChangeListener);
        empresarial.setOnCheckedChangeListener(checkedChangeListener);
        ambiental.setOnCheckedChangeListener(checkedChangeListener);
        ti.setOnCheckedChangeListener(checkedChangeListener);
        contratual.setOnCheckedChangeListener(checkedChangeListener);
        tributario.setOnCheckedChangeListener(checkedChangeListener);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);
                startActivity(intent);
                finish();
            }
        });

        String[] areaAtuacaoEscolhida = areaAtuacao.toArray(new String[0]);

        telefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer tamanhoDoTelefone = telefone.getText().toString().length();
                if (tamanhoDoTelefone > 1){
                    ultimoCaracterDigitado = telefone.getText().toString().substring(tamanhoDoTelefone-1);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer tamanhoDoTelefone = telefone.getText().toString().length();
                if (tamanhoDoTelefone == 2){
                    if (ultimoCaracterDigitado.equals(" ")){
                        telefone.append(" ");
                    } else {
                        telefone.getText().delete(tamanhoDoTelefone -1, tamanhoDoTelefone);
                    }
                } else if (tamanhoDoTelefone == 8){
                    if (ultimoCaracterDigitado.equals("-")){
                        telefone.append("-");
                    } else {
                        telefone.getText().delete(tamanhoDoTelefone -1, tamanhoDoTelefone);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, 2);

                db.atualizar(perfilUsuario);

            }
        });

        alterarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                perfilUsuario.setNome(nomeAdvogado.getText().toString());
                perfilUsuario.setEmail(email.getText().toString());
                perfilUsuario.setTelefone(Integer.valueOf(telefone.getText().toString()));
                perfilUsuario.setEstado(estado.getSelectedItem().toString());
                perfilUsuario.setCidade(cidade.getSelectedItem().toString());
                perfilUsuario.setNumeroOAB(registroOAB.getText().toString());
                perfilUsuario.setAreaAtuacao(areaAtuacaoEscolhida.toString());
                perfilUsuario.setBibliografia(bibliografia.getText().toString());
                perfilUsuario.setFotoPerfil(Integer.valueOf(String.valueOf(fotoPerfil)));

                db.atualizar(perfilUsuario);

                Snackbar snackbar = Snackbar.make(view, "Alteração feita com sucesso",Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2){
            if (resultCode == RESULT_OK){

                Uri imagemSelecionada = getIntent().getData();
                String[] colunas = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(imagemSelecionada, colunas, null, null, null);
                cursor.moveToFirst();

                int indexColuna = cursor.getColumnIndex(colunas[0]);
                String pathImg = cursor.getString(indexColuna);
                cursor.close();

                Bitmap bitmap = BitmapFactory.decodeFile(pathImg);
                fotoPerfil.setImageBitmap(bitmap);
            }
        }
    }
}