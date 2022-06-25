package br.com.etecia.meus_direitos;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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

    DB db;
    ArrayList<String> areaAtuacao = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        db = new DB(this);
        DBHelper DB = new DBHelper(this);
        Intent intent = getIntent();

        PerfilUsuario perfilUsuario = (PerfilUsuario) intent.getSerializableExtra("perfilUsuario");

        this.voltar = findViewById(R.id.imgVoltar);
        this.fotoPerfil = findViewById(R.id.fotoPerfil);
        this.edtnomeAdvogado = findViewById(R.id.nomeAdvogado);
        this.edtemail = findViewById(R.id.email);
        this.edttelefone = findViewById(R.id.telefone);
        this.cidadeSpinner = findViewById(R.id.alterarCidade);
        this.estadoSpinner = findViewById(R.id.alterarEstado);
        this.edtregistroOAB = findViewById(R.id.registroOAB);
        this.edtbibliografia = findViewById(R.id.bibliografia);
        this.editarFoto = findViewById(R.id.editarFoto);
        this.alterarPerfil = findViewById(R.id.AlterarPerfil);

        //recebendo dados
        this.edtnomeAdvogado.setText(perfilUsuario.getNome());
        this.edtemail.setText(perfilUsuario.getEmail());
        this.edttelefone.setText(perfilUsuario.getTelefone());
        this.areaAtuacao.add(perfilUsuario.getAreaAtuacao());
        this.estadoSpinner.setSelection(Integer.parseInt(perfilUsuario.getEstado()));
        this.cidadeSpinner.setSelection(Integer.parseInt(perfilUsuario.getCidade()));
        this.edtregistroOAB.setText(perfilUsuario.getNumeroOAB());
        this.edtbibliografia.setText(perfilUsuario.getBibliografia());

        //instanciando chips das areas de atuação
        this.civil = findViewById(R.id.chipCivil);
        this.consumidor = findViewById(R.id.chipConsumidor);
        this.trabalhista = findViewById(R.id.chipTrabalhista);
        this.penal = findViewById(R.id.chipPenal);
        this.empresarial = findViewById(R.id.chipEmpresarial);
        this.ambiental = findViewById(R.id.chipAmbiental);
        this.ti = findViewById(R.id.chipTI);
        this.contratual = findViewById(R.id.chipContratual);
        this.tributario = findViewById(R.id.chipTributário);

        selectedChipData = new ArrayList<>();

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    selectedChipData.add(String.valueOf(areaAtuacao));
                    selectedChipData.add(buttonView.getText().toString());
                    areaAtuacao.add(String.valueOf(selectedChipData));

                } else {
                    selectedChipData.remove(String.valueOf(areaAtuacao));
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
                Intent intent1 = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);
                startActivity(intent1);
                finish();
            }
        });

        editarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent1.setType("image/*");
                startActivityForResult(intent1, 2);

                db.atualizar(perfilUsuario);

            }
        });

        alterarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DB db = new DB(getApplicationContext());

                perfilUsuario.setNome(edtnomeAdvogado.getText().toString());
                perfilUsuario.setEmail(edtemail.getText().toString());
                perfilUsuario.setTelefone(Integer.valueOf(edttelefone.getText().toString()));
                perfilUsuario.setEstado(estadoSpinner.getSelectedItem().toString());
                perfilUsuario.setCidade(cidadeSpinner.getSelectedItem().toString());
                perfilUsuario.setNumeroOAB(edtregistroOAB.getText().toString());
                perfilUsuario.setAreaAtuacao(areaAtuacao.toString());
                perfilUsuario.setBibliografia(edtbibliografia.getText().toString());
                perfilUsuario.setFotoPerfil(Integer.valueOf(String.valueOf(fotoPerfil)));

                if (db.atualizar(perfilUsuario)){
                    Intent intent1 = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);

                    intent1.putExtra("perfilUsuario", String.valueOf(perfilUsuario));

                    startActivity(intent1);
                    finish();

                    Snackbar snackbar = Snackbar.make(view, "Alteração feita com sucesso",Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
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