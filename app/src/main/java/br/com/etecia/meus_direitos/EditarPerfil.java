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
        alterarPerfil = findViewById(R.id.AlterarPerfil);

        //recebendo dados
        edtnomeAdvogado.setText(perfilUsuario.getNome());
        edtemail.setText(perfilUsuario.getEmail());
        edttelefone.setText(perfilUsuario.getTelefone());
        areaAtuacao.add(perfilUsuario.getAreaAtuacao());
        estadoSpinner.setSelection(Integer.parseInt(perfilUsuario.getEstado()));
        cidadeSpinner.setSelection(Integer.parseInt(perfilUsuario.getCidade()));
        edtregistroOAB.setText(perfilUsuario.getNumeroOAB());
        edtbibliografia.setText(perfilUsuario.getBibliografia());

        //instanciando chips das areas de atuação
        civil = findViewById(R.id.chipCivil);
        consumidor = findViewById(R.id.chipConsumidor);
        trabalhista = findViewById(R.id.chipTrabalhista);
        penal = findViewById(R.id.chipPenal);
        empresarial = findViewById(R.id.chipEmpresarial);
        ambiental = findViewById(R.id.chipAmbiental);
        ti = findViewById(R.id.chipTI);
        contratual = findViewById(R.id.chipContratual);
        tributario = findViewById(R.id.chipTributário);

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

                if (db.atualizar(perfilUsuario)){

                    Intent intent1 = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);

                    intent1.putExtra("perfilUsuario", String.valueOf(perfilUsuario));

                    Toast.makeText(EditarPerfil.this, "Alteração feita com sucesso", Toast.LENGTH_SHORT).show();

                    startActivity(intent1);
                    finish();

                } else {
                    Toast.makeText(EditarPerfil.this, "Erro ao atualizar os dados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}