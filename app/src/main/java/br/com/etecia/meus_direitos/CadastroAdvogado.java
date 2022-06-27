package br.com.etecia.meus_direitos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import br.com.etecia.meus_direitos.objetos.API_IBGE;
import br.com.etecia.meus_direitos.objetos.Cidades;
import br.com.etecia.meus_direitos.objetos.Estados;
import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class CadastroAdvogado extends AppCompatActivity {

    Button btnCadastrarAdvogado;
    ImageView voltar;
    EditText edtNomeAdvogado, edtEmail, edtTelefone, edtRegistroOAB, edtSenha;
    Spinner spinnerEstado, spinnerCidade;

    Cidades[] municipios = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_advogado);

        btnCadastrarAdvogado = findViewById(R.id.btncadastrarAdvogado);
        voltar = findViewById(R.id.imgVoltar);
        edtNomeAdvogado = findViewById(R.id.nomeAdvogado);
        edtEmail = findViewById(R.id.email);
        edtTelefone = findViewById(R.id.telefone);
        edtRegistroOAB = findViewById(R.id.registroOAB);
        edtSenha = findViewById(R.id.senha);

        spinnerEstado = findViewById(R.id.estadoSpinner);
        spinnerCidade = findViewById(R.id.cidadeSpinner);
        spinnerCidade.setEnabled(true);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroAdvogado.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        final String[] valor1 = {null};
        final String[] valor2 = {null};

        //Cadastrando um novo usuario
        btnCadastrarAdvogado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtNomeAdvogado.getText().toString()) || TextUtils.isEmpty(edtEmail.getText().toString()) || TextUtils.isEmpty(edtTelefone.getText().toString()) ||
                        spinnerEstado.getSelectedItem().equals(0) || spinnerCidade.getSelectedItem().equals(0) || TextUtils.isEmpty(edtRegistroOAB.getText().toString()) || TextUtils.isEmpty(edtSenha.getText().toString())){

                    Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();

                } else {

                    DBHelper db = new DBHelper(getApplicationContext());
                    PerfilUsuario perfilUsuario = new PerfilUsuario();
                    valor1[0] = (String) spinnerEstado.getSelectedItem();
                    valor2[0] = (String) spinnerCidade.getSelectedItem();

                    Intent intent = new Intent(getApplicationContext(), Chip_filtro_areas.class);

                    perfilUsuario.setNome(edtNomeAdvogado.getText().toString());
                    perfilUsuario.setEmail(edtEmail.getText().toString());
                    perfilUsuario.setTelefone(edtTelefone.getText().toString());
                    perfilUsuario.setEstado(valor1.toString());
                    perfilUsuario.setCidade(valor2.toString());
                    perfilUsuario.setNumeroOAB(edtRegistroOAB.getText().toString());
                    perfilUsuario.setSenha(edtSenha.getText().toString());
                    db.inserirDados(perfilUsuario);
                    db.close();

                    System.out.println(perfilUsuario.getNome());
                    System.out.println(perfilUsuario.getEmail());
                    System.out.println(perfilUsuario.getTelefone());
                    System.out.println(perfilUsuario.getEstado());
                    System.out.println(perfilUsuario.getCidade());
                    System.out.println(perfilUsuario.getSenha());

                    Toast.makeText(getApplicationContext(), "Cadastro feito com sucesso", Toast.LENGTH_SHORT).show();

                    System.out.println(perfilUsuario);
                    startActivity(intent);
                    finish();

                }


            }
        });

        //preenchendo o spinner estado
        String respostaEstados = executaApiIBGE("estado");

        Gson gsonEstados = new GsonBuilder().setPrettyPrinting().create();
        final Estados[] estados = gsonEstados.fromJson(String.valueOf(respostaEstados), Estados[].class);

        final ArrayList<String> estadosParaSpinner = new ArrayList<>();

        for (Estados estado: estados){
            estadosParaSpinner.add(estado.getNome());
        }

        //Ordena em ordem alfabética
        Collections.sort(estadosParaSpinner);

        estadosParaSpinner.add(0, "Selecione o Estado");

        //Criando um array de estados no spinner
        ArrayAdapter<String> adapterEstados = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estadosParaSpinner);
        spinnerEstado.setAdapter(adapterEstados);

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Estados estado : estados) {
                    if (estado.getNome().equals(spinnerEstado.getSelectedItem().toString())) {
                        solicitarMunicipios(estado.getSigla());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Cidades cidade : municipios) {
                    if (cidade.getNome().equals(spinnerCidade.getSelectedItem().toString())) {
                        solicitarSubdistritos(String.valueOf(cidade.getId()));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //Preenchendo o spinner cidade
    private void solicitarMunicipios(String siglaEstado) {
        String respostaMunicipios = executaApiIBGE("municipio", siglaEstado);

        Gson gsonMunicipios = new GsonBuilder().setPrettyPrinting().create();
        municipios = gsonMunicipios.fromJson(String.valueOf(respostaMunicipios), Cidades[].class);

        final ArrayList<String> municipiosParaSpinner = new ArrayList<>();
        final ArrayList<String> idMunicipios = new ArrayList<>();

        for (Cidades cidade: municipios){
            municipiosParaSpinner.add(cidade.getNome());
            idMunicipios.add(String.valueOf(cidade.getId()));
        }

        //Ordena em ordem alfabética
        Collections.sort(municipiosParaSpinner);

        municipiosParaSpinner.add(0, "Selecione a Cidade");

        spinnerCidade.setEnabled(true);

        //Criando um array de municipios no spinner
        ArrayAdapter<String> adapterMunicipios = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, municipiosParaSpinner);
        spinnerCidade.setAdapter(adapterMunicipios);

    }

    private void solicitarSubdistritos(String idMunicipio) {

    }

    //Chamando a api do IBGE
    private String executaApiIBGE (String... params) {
        API_IBGE api_ibge = new API_IBGE();

        String respostaIBGE = null;

        try {
            respostaIBGE = api_ibge.execute(params).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return respostaIBGE;
    }
}