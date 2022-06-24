package br.com.etecia.meus_direitos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.etecia.meus_direitos.objetos.ListAdvogados;
import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class DB {
    private SQLiteDatabase db;

    public DB(Context context) {
        DBHelper auxBD = new DBHelper(context);
        db = auxBD.getWritableDatabase();
    }

    public void inserir(PerfilUsuario perfilUsuario) {
        ContentValues values = new ContentValues();

        values.put("nome", perfilUsuario.getNome());
        values.put("email", perfilUsuario.getEmail());
        values.put("telefone", perfilUsuario.getTelefone());
        values.put("estado", perfilUsuario.getEstado());
        values.put("cidade", perfilUsuario.getCidade());
        values.put("numeroOAB", perfilUsuario.getNumeroOAB());
        values.put("senha", perfilUsuario.getSenha());

        db.insert("advogados", null, values);
    }

    public void atualizar(PerfilUsuario perfilUsuario) {
        ContentValues values = new ContentValues();

        values.put("nome", perfilUsuario.getNome());
        values.put("email", perfilUsuario.getEmail());
        values.put("telefone", perfilUsuario.getTelefone());
        values.put("estado", perfilUsuario.getEstado());
        values.put("cidade", perfilUsuario.getCidade());
        values.put("numeroOAB", perfilUsuario.getNumeroOAB());
        values.put("senha", perfilUsuario.getSenha());
        values.put("areaAtuacao", perfilUsuario.getAreaAtuacao());
        values.put("bibliografia", perfilUsuario.getBibliografia());
        values.put("fotoPerfil", perfilUsuario.getFotoPerfil());

        db.update("advogados", values, "_id = ?", new String[]{"" + perfilUsuario.getId()});
    }

    public void buscarPerfil() {

        String[] colunas = new String[]{"_id", "nome", "email", "telefone", "estado", "cidade", "numeroOAB", "areaAtuacao", "bibliografia", "fotoPerfil"};
        Cursor cursor = db.query("advogados", colunas, null, null, null, null, null);
    }

    public ArrayList<ListAdvogados> buscarAdvogados() {

        ArrayList<ListAdvogados> arrayList = new ArrayList<ListAdvogados>();
        String[] colunas = new String[]{"_id", "nome", "estado", "cidade", "areaAtuacao", "fotoPerfil"};
        Cursor cursor = db.query("advogados", colunas, null, null, null, null, null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();

            do {
                ListAdvogados adv = new ListAdvogados();
                adv.setId(cursor.getLong(0));
                adv.setNome(cursor.getString(1));
                adv.setEstado(cursor.getString(2));
                adv.setCidade(cursor.getString(3));
                adv.setAreaAtuacao(cursor.getString(4));
                adv.setFotoPerfil(Integer.valueOf(cursor.getString(5)));
                arrayList.add(adv);


            }while (cursor.moveToNext());
        }
        return (arrayList);
    }
}
