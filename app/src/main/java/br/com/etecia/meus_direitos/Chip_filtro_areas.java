package br.com.etecia.meus_direitos;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class Chip_filtro_areas extends AppCompatActivity {

    Chip civil, consumidor, trabalhista, penal, empresarial, ambiental, ti, contratual, tributario;
    ArrayList<String> selectedChipData;
    Button enviar;
    EditText edtbibliografia;
    List<String> areaAtuacao = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip_filtro_areas);



        civil = findViewById(R.id.chipCivil);
        consumidor = findViewById(R.id.chipConsumidor);
        trabalhista = findViewById(R.id.chipTrabalhista);
        penal = findViewById(R.id.chipPenal);
        empresarial = findViewById(R.id.chipEmpresarial);
        ambiental = findViewById(R.id.chipAmbiental);
        ti = findViewById(R.id.chipTI);
        contratual = findViewById(R.id.chipContratual);
        tributario = findViewById(R.id.chipTributário);

        edtbibliografia = findViewById(R.id.bibliografia);
        enviar = findViewById(R.id.btnEnviarAreas);


        selectedChipData = new ArrayList<>();

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    selectedChipData.add(buttonView.getText().toString());
                    areaAtuacao.add(String.valueOf(selectedChipData));
                }
                else{
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


        DBHelper db = new DBHelper(this);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper db = new DBHelper(getApplicationContext());
                PerfilUsuario perfilUsuario = new PerfilUsuario();

                String areas = areaAtuacao.toString();
                String bibliografia = edtbibliografia.getText().toString();

                if (TextUtils.isEmpty(areas) || TextUtils.isEmpty(bibliografia)) {

                    Toast.makeText(getApplicationContext(), "Escolha uma area de atuação e escreva uma bibliografia", Toast.LENGTH_SHORT).show();

                } else {

                    PerfilUsuario adv = new PerfilUsuario();

                    Intent intent = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);

                    adv.setAreaAtuacao(areas);
                    adv.setBibliografia(bibliografia);
                    db.areasEbibliografia(perfilUsuario);
                    db.close();

                    System.out.println(perfilUsuario.getAreaAtuacao());
                    System.out.println(perfilUsuario.getBibliografia());

                    Toast.makeText(getApplicationContext(), "Cadastro feito com sucesso", Toast.LENGTH_SHORT).show();

                    System.out.println(areas);
                    System.out.println(bibliografia);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }

}