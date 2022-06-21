package br.com.etecia.meus_direitos.objetos;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import br.com.etecia.meus_direitos.ListaAdvogados;

public class API_IBGE extends AsyncTask<String, Void, String> {

    ListaAdvogados listaAdvogados = new ListaAdvogados();

    @Override
    protected String doInBackground(String... params) {

        if (params[0].equals("estado")){
            StringBuilder respostaEstados = new StringBuilder();

            try {
                URL urlEstados =  new URL("https://servicodados.ibge.gov.br/api/v1/localidades/estados/");

                HttpURLConnection conexao = (HttpURLConnection) urlEstados.openConnection();
                conexao.setRequestMethod("GET");
                conexao.setRequestProperty("Content-type", "aplication/json");
                conexao.setDoOutput(true);
                conexao.setConnectTimeout(3000);
                conexao.connect();

                Scanner scanner = new Scanner(urlEstados.openStream());
                while (scanner.hasNext()){
                    respostaEstados.append(scanner.next());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return respostaEstados.toString();

        } else if (params[0].equals("municipio")){
            StringBuilder respostaMunicipios = new StringBuilder();

            try {
                URL urlMunicipios =  new URL("https://servicodados.ibge.gov.br/api/v1/localidades/estados/"+params[1]+"/municipios");

                HttpURLConnection conexao = (HttpURLConnection) urlMunicipios.openConnection();
                conexao.setRequestMethod("GET");
                conexao.setRequestProperty("Content-type", "aplication/json");
                conexao.setDoOutput(true);
                conexao.setConnectTimeout(3000);
                conexao.connect();

                Scanner scanner = new Scanner(urlMunicipios.openStream());
                while (scanner.hasNext()){
                    respostaMunicipios.append(scanner.next());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return respostaMunicipios.toString();
        }
        else if (params[0].equals("subdistritos")){
            StringBuilder respostaSubdistritos = new StringBuilder();

            try {
                URL urlSubdistritos =  new URL("https://servicodados.ibge.gov.br/api/v1/localidades/municipios/"+params[1]+"/subdistritos");

                HttpURLConnection conexao = (HttpURLConnection) urlSubdistritos.openConnection();
                conexao.setRequestMethod("GET");
                conexao.setRequestProperty("Content-type", "aplication/json");
                conexao.setDoOutput(true);
                conexao.setConnectTimeout(3000);
                conexao.connect();

                Scanner scanner = new Scanner(urlSubdistritos.openStream());
                while (scanner.hasNext()){
                    respostaSubdistritos.append(scanner.next());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return respostaSubdistritos.toString();
        }
        return null;
    }

}
