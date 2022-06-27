package br.com.etecia.meus_direitos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.etecia.meus_direitos.objetos.API_IBGE;
import br.com.etecia.meus_direitos.objetos.Cidades;
import br.com.etecia.meus_direitos.objetos.Estados;

public class ListaAdvogados extends AppCompatActivity {

    MaterialToolbar toolbar;
    Spinner spinnerAreas, spinnerEstados, spinnerCidades;
    ImageView imagemfiltro;
    CardView cardViewFiltro;
    Button buttonFiltrar;
    Context context;

    Cidades[] municipios = null;

  /*  List<Advogados> advogadosList = new ArrayList<>();

    List<String> nomes = new ArrayList<String>();
    List<String> estados = new ArrayList<String>();
    List<String> cidades = new ArrayList<String>();
    List<String> areas = new ArrayList<String>();

    String[] dados_nomes = new String[]{};
    String[] dados_estados = new String[]{};
    String[] dados_cidades = new String[]{};
    String[] dados_areas = new String[]{};
*/
    ArrayList<Advogados> lstAdvogados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_advogados);

        context = getApplicationContext();

        spinnerAreas = findViewById(R.id.filtrarArea);
        spinnerEstados = findViewById(R.id.filtrarEstado);
        spinnerCidades = findViewById(R.id.filtrarCidade);
        spinnerCidades.setEnabled(false);

        imagemfiltro = findViewById(R.id.filtrar);
        cardViewFiltro = findViewById(R.id.cardFiltro);
        buttonFiltrar = findViewById(R.id.bottomFiltrar);

        //buscaLista("nome");

        lstAdvogados = new ArrayList<>();

        lstAdvogados.add(new Advogados("Marcelo Abdalla Kilsan", "SP","São Paulo", "Civil, Trabalhista, Consumidor", R.drawable.marcelo));
        lstAdvogados.add(new Advogados("Fatima Abibe Ferrarezi", "SP", "São Paulo", "Penal, Tributária, TI", R.drawable.fatima));
        lstAdvogados.add(new Advogados("Bruna Abrantes Flor da Silva", "SP","São Paulo", "Trabalhista, Civil, TI", R.drawable.bruna));
        lstAdvogados.add(new Advogados("Patrick Aguiar Bernardo", "SP", "São Paulo", "Contratual, Ambiental", R.drawable.patrick));
        lstAdvogados.add(new Advogados("Simone Abrão Giacummo", "SP", "São Paulo", "Empresarial, Contratual", R.drawable.simone));
        lstAdvogados.add(new Advogados("Paulo Abreu Junior", "SP", "São Paulo", "Civil, Consumidor", R.drawable.paulo));
        lstAdvogados.add(new Advogados("Sandra Abate Murcia", "SP", "São Paulo", "Penal, Tributária, TI", R.drawable.sandra));
        lstAdvogados.add(new Advogados("Sebastiao Abilio da Silva", "SP", "São Paulo", "Civil, Trabalhista, Consumidor", R.drawable.sebastiao));

        RecyclerView mrecyclerView = findViewById(R.id.recycler_view_lista);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        RecyclerAdapter mAdapter = new RecyclerAdapter(getApplicationContext(), lstAdvogados);
        mrecyclerView.setAdapter(mAdapter);

        //Criando um array de areas de trabalho no spinner
        ArrayAdapter<CharSequence> adapterAreas = ArrayAdapter.createFromResource(this, R.array.areas_atuacao, android.R.layout.simple_spinner_item);
        adapterAreas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAreas.setAdapter(adapterAreas);

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
        spinnerEstados.setAdapter(adapterEstados);

        final String[] areasAtuacao = new String[9];

        spinnerAreas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               /* switch (position){
                    case 0:
                        buscaLista("nome");
                        break;
                    case 1:
                        buscaLista("nome");
                        break;

                    case 2:
                        buscaLista("nome");
                        break;

                    case 4:
                        buscaLista("nome");
                        break;

                    case 5:
                        buscaLista("nome");
                        break;

                    case 6:
                        buscaLista("nome");
                        break;

                    case 7:
                        buscaLista("nome");
                        break;

                    case 8:
                        buscaLista("nome");
                        break;

                    case 9:
                        buscaLista("nome");
                        break;
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Estados estado : estados) {
                    if (estado.getNome().equals(spinnerEstados.getSelectedItem().toString())) {
                        solicitarMunicipios(estado.getSigla());
                       // buscaLista("estado");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Cidades cidade : municipios) {
                    if (cidade.getNome().equals(spinnerCidades.getSelectedItem().toString())) {
                        solicitarSubdistritos(String.valueOf(cidade.getId()));
                        //buscaLista("cidade");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final int[] clique = {1};

        buttonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent1 = new Intent(getApplicationContext(), ListaAdvogados.class);
                clique[0] = 0;
                //startActivity(intent1);
                //finish();
            }
        });

        imagemfiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clique[0] == 1) {
                    cardViewFiltro.setVisibility(View.VISIBLE);
                    clique[0] = 0;

                } else {
                    cardViewFiltro.setVisibility(View.GONE);
                    clique[0] = 1;
                }
            }
        });


        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaAdvogados.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navegacao_cliente, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.advogados:
                Intent intent = new Intent(getApplicationContext(), ListaAdvogados.class);
                startActivity(intent);
                break;

            case R.id.informacoes:
                Intent intent2 = new Intent(getApplicationContext(), InformacoesCli.class);
                startActivity(intent2);
                break;
        }
        return true;
    }

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

        spinnerCidades.setEnabled(true);

        //Criando um array de municipios no spinner
        ArrayAdapter<String> adapterMunicipios = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, municipiosParaSpinner);
        spinnerCidades.setAdapter(adapterMunicipios);

    }

    private void solicitarSubdistritos(String idMunicipio) {
        String respostaSubdistritos = executaApiIBGE("subdistrito", idMunicipio);
    }

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

  /*  private void buscaLista(String orderBy){

        DBHelper db = new DBHelper(getApplicationContext());
        //advogadosList = db.buscarAdvogados(orderBy);

        for (Advogados pessoaBuscada : advogadosList){

            nomes.add(pessoaBuscada.getNome());
            estados.add(pessoaBuscada.getEstado());
            cidades.add(pessoaBuscada.getCidade());
            areas.add(pessoaBuscada.getAreaAtuacao());
        }

        dados_nomes = nomes.toArray(new String[0]);
        dados_estados = estados.toArray(new String[0]);
        dados_cidades = cidades.toArray(new String[0]);
        dados_areas = areas.toArray(new String[0]);

        RecyclerView mrecyclerView = findViewById(R.id.recycler_view_lista);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        RecyclerAdapter mAdapter = new RecyclerAdapter(context, dados_nomes, dados_estados, dados_cidades, dados_areas);
        mrecyclerView.setAdapter(mAdapter);

    }*/
}