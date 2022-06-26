package br.com.etecia.meus_direitos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import br.com.etecia.meus_direitos.objetos.API_IBGE;
import br.com.etecia.meus_direitos.objetos.Cidades;
import br.com.etecia.meus_direitos.objetos.Estados;
import br.com.etecia.meus_direitos.objetos.ListAdvogados;
import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class CadastroAdvogado extends AppCompatActivity {

    Button btnCadastrarAdvogado;
    ImageView voltar;
    EditText edtNomeAdvogado, edtEmail, edtTelefone, edtRegistroOAB, edtSenha;
    Spinner spinnerEstado, spinnerCidade;
    PerfilUsuario perfilUsuario = new PerfilUsuario();

    String ultimoCaracterDigitado = "";

    Cidades[] municipios = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_advogado);

        DBHelper dbHelper = new DBHelper(this);
        DB db = new DB(this);

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

        edtTelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                Integer tamanhoDoTelefone = Integer.valueOf(edtTelefone.getText().toString().length());
                if (tamanhoDoTelefone > 1){
                    ultimoCaracterDigitado = edtTelefone.getText().toString().substring(tamanhoDoTelefone -1);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Integer tamanhoDoTelefone = Integer.valueOf(edtTelefone.getText().toString().length());
                if (tamanhoDoTelefone == 2){
                    if (!ultimoCaracterDigitado.equals(" ")){
                        edtTelefone.append(" ");
                    } else {
                        edtTelefone.getText().delete(tamanhoDoTelefone -1,tamanhoDoTelefone);
                    }

                } else if (tamanhoDoTelefone == 8){
                    if (!ultimoCaracterDigitado.equals("-")){
                        edtTelefone.append("-");
                    } else {
                        edtTelefone.getText().delete(tamanhoDoTelefone -1,tamanhoDoTelefone);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroAdvogado.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        //Cadastrando um novo usuario
        btnCadastrarAdvogado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = edtNomeAdvogado.getText().toString();
                String email = edtEmail.getText().toString();
                String telefone = edtTelefone.getText().toString();
                String estado = spinnerEstado.getSelectedItem().toString();
                String cidade = spinnerCidade.getSelectedItem().toString();
                String registroOAB = edtRegistroOAB.getText().toString();
                String senha = edtSenha.getText().toString();

                if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(email) || TextUtils.isEmpty(telefone) || TextUtils.isEmpty(estado) || TextUtils.isEmpty(cidade) || TextUtils.isEmpty(registroOAB) || TextUtils.isEmpty(senha)){
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checandoUsuario = dbHelper.checandoEmailDoUsuario(email);
                    if (checandoUsuario == false){
                        Boolean inserirDados = dbHelper.Cadastro(perfilUsuario);
                        if (inserirDados == true){

                            Intent intent = new Intent(getApplicationContext(), Chip_filtro_areas.class);

                            intent.putExtra("nome", nome);
                            intent.putExtra("email", email);
                            intent.putExtra("telefone", telefone);
                            intent.putExtra("estado", estado);
                            intent.putExtra("cidade", cidade);
                            intent.putExtra("registroOAB", registroOAB);
                            intent.putExtra("senha", senha);

                            Toast.makeText(getApplicationContext(), "Cadastro feito com sucesso", Toast.LENGTH_SHORT).show();

                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "Falha ao cadastrar", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Usuário já está cadastrado", Toast.LENGTH_SHORT).show();
                    }

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