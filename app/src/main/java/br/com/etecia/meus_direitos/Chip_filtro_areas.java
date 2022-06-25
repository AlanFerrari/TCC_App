package br.com.etecia.meus_direitos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class Chip_filtro_areas extends AppCompatActivity {

    Chip civil, consumidor, trabalhista, penal, empresarial, ambiental, ti, contratual, tributario;
    ArrayList<String> selectedChipData;
    Button enviar;
    EditText bibliografia;
    ArrayList<String> areaAtuacao = new ArrayList<>();
    PerfilUsuario perfilUsuario = new PerfilUsuario();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip_filtro_areas);

        DB db = new DB(this);

        civil = findViewById(R.id.chipCivil);
        consumidor = findViewById(R.id.chipConsumidor);
        trabalhista = findViewById(R.id.chipTrabalhista);
        penal = findViewById(R.id.chipPenal);
        empresarial = findViewById(R.id.chipEmpresarial);
        ambiental = findViewById(R.id.chipAmbiental);
        ti = findViewById(R.id.chipTI);
        contratual = findViewById(R.id.chipContratual);
        tributario = findViewById(R.id.chipTributário);

        bibliografia = findViewById(R.id.bibliografia);
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


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PerfilAdvogado_Adv.class);

                perfilUsuario.setAreaAtuacao(areaAtuacao.toString());
                perfilUsuario.setBibliografia(bibliografia.getText().toString());

                db.atualizar(perfilUsuario);

                intent.putExtra("areas", selectedChipData.toString());
                setResult(101, intent);
                startActivity(intent);
                finish();
            }
        });
    }

}