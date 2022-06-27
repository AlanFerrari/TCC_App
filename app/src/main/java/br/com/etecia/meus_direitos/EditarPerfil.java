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
import java.util.Collections;
import java.util.List;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class EditarPerfil extends AppCompatActivity {

    ImageView voltar, fotoPerfil;
    TextView editarFoto;
    EditText edtnomeAdvogado, edtemail, edttelefone, edtregistroOAB, edtbibliografia;
    Chip civil, consumidor, trabalhista, penal, empresarial, ambiental, ti, contratual, tributario;
    ArrayList<String> selectedChipData;
    Button alterarPerfil;
    Spinner estadoSpinner, cidadeSpinner;
    PerfilUsuario perfilUsuario = new PerfilUsuario();

    List<String> areaAtuacao = new ArrayList<>();

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
        alterarPerfil = findViewById(R.id.AlterarPerfil);

        //recebendo dados

        Intent intent = getIntent();

        edtnomeAdvogado.setText(intent.getStringExtra("nome"));
        edtemail.setText(intent.getStringExtra("email"));
        edttelefone.setText(intent.getStringExtra("telefone"));
       // estadoSpinner.setSelection(intent.getStringExtra("estado"));
       // cidadeSpinner.setSelection(Integer.parseInt(intent.getStringExtra("cidade")));
        edtregistroOAB.setText(intent.getStringExtra("numeroOAB"));
        edtbibliografia.setText(intent.getStringExtra("bibliografia"));


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
        areaAtuacao = Collections.singletonList(perfilUsuario.getAreaAtuacao());
        List areas = new ArrayList<>();

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    selectedChipData.add(String.valueOf(areaAtuacao));
                    selectedChipData.add(buttonView.getText().toString());
                    areas.add(String.valueOf(selectedChipData));

                } else {
                    selectedChipData.remove(String.valueOf(areaAtuacao));
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
                Intent intent1 = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);
                startActivity(intent1);
                finish();
            }
        });

        alterarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);
                DBHelper db = new DBHelper(getApplicationContext());
                PerfilUsuario perfilUsuario = new PerfilUsuario();

                perfilUsuario.setNome(edtnomeAdvogado.getText().toString());
                perfilUsuario.setEmail(edtemail.getText().toString());
                perfilUsuario.setTelefone(edttelefone.getText().toString());
                perfilUsuario.setEstado(estadoSpinner.getSelectedItem().toString());
                perfilUsuario.setCidade(cidadeSpinner.getSelectedItem().toString());
                perfilUsuario.setNumeroOAB(edtregistroOAB.getText().toString());
                perfilUsuario.setAreaAtuacao(areas.toString());
                perfilUsuario.setBibliografia(edtbibliografia.getText().toString());
                db.inserirDados(perfilUsuario);
                db.close();

                Toast.makeText(EditarPerfil.this, "Alteração feita com sucesso", Toast.LENGTH_SHORT).show();

                startActivity(intent1);
                finish();

            }
        });
    }
}