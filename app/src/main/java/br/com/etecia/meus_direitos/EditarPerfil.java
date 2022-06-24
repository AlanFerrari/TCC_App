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

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class EditarPerfil extends AppCompatActivity {

    ImageView voltar, fotoPerfil;
    TextView editarFoto;
    EditText edtnomeAdvogado, edtemail, edttelefone, edtregistroOAB, edtbibliografia;
    Chip civil, consumidor, trabalhista, penal, empresarial, ambiental, ti, contratual, tributario;
    ArrayList<String> selectedChipData;
    Button alterarPerfil;
    Spinner estadoSpinner, cidadeSpinner;
    String ultimoCaracterDigitado = "";

    DB db;
    PerfilUsuario perfilUsuario = new PerfilUsuario();
    ArrayList<String> areas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        voltar = findViewById(R.id.imgVoltar);
        fotoPerfil = findViewById(R.id.fotoPerfil);
        edtnomeAdvogado = findViewById(R.id.nomeAdvogado);
        edtemail = findViewById(R.id.email);
        edttelefone = findViewById(R.id.telefone);
        cidadeSpinner = findViewById(R.id.alterarCidade);
        estadoSpinner = findViewById(R.id.alterarEstado);
        edtregistroOAB = findViewById(R.id.registroOAB);
        edtbibliografia = findViewById(R.id.bibliografia);

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

        DBHelper DB = new DBHelper(this);

        String nome = edtnomeAdvogado.getText().toString();
        String email = edtemail.getText().toString();
        String telefone = edttelefone.getText().toString();
        String areaAtuacao = areas.toString();
        String estado = estadoSpinner.getSelectedItem().toString();
        String cidade = cidadeSpinner.getSelectedItem().toString();
        String numeroOAB = edtregistroOAB.getText().toString();
        String bibliografia = edtbibliografia.getText().toString();

        Boolean perfil = DB.PegarDadosDoBanco(nome, email, telefone, areaAtuacao, estado, cidade, numeroOAB, bibliografia);

        if (perfil == true){

            perfilUsuario.setNome(edtnomeAdvogado.getText().toString());
            perfilUsuario.setEmail(edtemail.getText().toString());
            perfilUsuario.setTelefone(Integer.valueOf(edttelefone.getText().toString()));
            perfilUsuario.setAreaAtuacao(areas.toString());;
            perfilUsuario.setEstado(estadoSpinner.getSelectedItem().toString());
            perfilUsuario.setCidade(cidadeSpinner.getSelectedItem().toString());
            perfilUsuario.setNumeroOAB(edtregistroOAB.getText().toString());
            perfilUsuario.setBibliografia(edtbibliografia.getText().toString());


        } else {
            Toast.makeText(this, "Falha na obtenção de dados", Toast.LENGTH_SHORT).show();
        }

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    selectedChipData.add(String.valueOf(areas));
                    selectedChipData.add(buttonView.getText().toString());
                    areas.add(String.valueOf(selectedChipData));

                } else {
                    selectedChipData.remove(String.valueOf(areas));
                    selectedChipData.remove(buttonView.getText().toString());
                    areas.remove(String.valueOf(selectedChipData));
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


        edttelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer tamanhoDoTelefone = edttelefone.getText().toString().length();
                if (tamanhoDoTelefone > 1){
                    ultimoCaracterDigitado = edttelefone.getText().toString().substring(tamanhoDoTelefone-1);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer tamanhoDoTelefone = edttelefone.getText().toString().length();
                if (tamanhoDoTelefone == 2){
                    if (ultimoCaracterDigitado.equals(" ")){
                        edttelefone.append(" ");
                    } else {
                        edttelefone.getText().delete(tamanhoDoTelefone -1, tamanhoDoTelefone);
                    }
                } else if (tamanhoDoTelefone == 8){
                    if (ultimoCaracterDigitado.equals("-")){
                        edttelefone.append("-");
                    } else {
                        edttelefone.getText().delete(tamanhoDoTelefone -1, tamanhoDoTelefone);
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

        String[] areaAtuacaoEscolhida = areas.toArray(new String[0]);

        alterarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);

                perfilUsuario.setNome(edtnomeAdvogado.getText().toString());
                perfilUsuario.setEmail(edtemail.getText().toString());
                perfilUsuario.setTelefone(Integer.valueOf(edttelefone.getText().toString()));
                perfilUsuario.setEstado(estadoSpinner.getSelectedItem().toString());
                perfilUsuario.setCidade(cidadeSpinner.getSelectedItem().toString());
                perfilUsuario.setNumeroOAB(edtregistroOAB.getText().toString());
                perfilUsuario.setAreaAtuacao(areaAtuacaoEscolhida.toString());
                perfilUsuario.setBibliografia(edtbibliografia.getText().toString());
                perfilUsuario.setFotoPerfil(Integer.valueOf(String.valueOf(fotoPerfil)));

                db.atualizar(perfilUsuario);

                Snackbar snackbar = Snackbar.make(view, "Alteração feita com sucesso",Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.show();

                startActivity(intent);
                finish();
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