package br.com.etecia.meus_direitos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.etecia.meus_direitos.objetos.ListAdvogados;
import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class  DB {
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

    public boolean atualizar(PerfilUsuario perfilUsuario) {
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

        int rows = db.update("advogados", values, "id = ?", new String[]{String.valueOf(perfilUsuario.getId())});
        db.close();
        return rows > 0;
    }

    public PerfilUsuario buscarPerfil(int id) {

        Cursor cursor = db.rawQuery("SELECT * FROM advogados WHERE id = ?", new  String[]{String.valueOf(id)});
        PerfilUsuario perfilUsuario = null;

        if (cursor.moveToFirst()){
            perfilUsuario = new PerfilUsuario();
            perfilUsuario.setId(cursor.getLong(0));
            perfilUsuario.setNome(cursor.getString(1));
            perfilUsuario.setEmail(cursor.getString(2));
            perfilUsuario.setTelefone(Integer.valueOf(cursor.getString(3)));
            perfilUsuario.setEstado(cursor.getString(4));
            perfilUsuario.setCidade(cursor.getString(5));
            perfilUsuario.setNumeroOAB(cursor.getString(6));
            perfilUsuario.setAreaAtuacao(cursor.getString(8));
            perfilUsuario.setBibliografia(cursor.getString(9));
            perfilUsuario.setFotoPerfil(Integer.valueOf(cursor.getString(10)));
        }
        db.close();
        return perfilUsuario;
    }

    @SuppressLint("Range")
    public ArrayList<ListAdvogados> buscarAdvogados(String orderBy) {

        ArrayList<ListAdvogados> arrayList = new ArrayList<ListAdvogados>();
        String sql = "SELECT id, nome, estado, cidade, areaAtuacao, fotoPerfil FROM advogados ORDER BY" +orderBy+";";

        Cursor cursor = db.rawQuery(sql, null);

        List<ListAdvogados> advogadosList = new ArrayList<ListAdvogados>();

        while (cursor.moveToNext()) {

            ListAdvogados adv = new ListAdvogados();

            adv.setId(cursor.getLong(cursor.getColumnIndex("id")));
            adv.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            adv.setEstado(cursor.getString(cursor.getColumnIndex("estado")));
            adv.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
            adv.setAreaAtuacao(cursor.getString(cursor.getColumnIndex("areaAtuacao")));
            adv.setFotoPerfil(Integer.valueOf(cursor.getString(cursor.getColumnIndex("fotoPerfil"))));
            advogadosList.add(adv);
        }
        return (arrayList);
    }

    @SuppressLint("Range")
    public List<ListAdvogados> buscarAdvogados() {

        ArrayList<ListAdvogados> arrayList = new ArrayList<ListAdvogados>();
        String sql = "SELECT id, nome, estado, cidade, areaAtuacao, fotoPerfil FROM advogados;";

        Cursor cursor = db.rawQuery(sql, null);

        List<ListAdvogados> advogadosList = new ArrayList<ListAdvogados>();

        while (cursor.moveToNext()) {

            ListAdvogados adv = new ListAdvogados();

            adv.setId(cursor.getLong(cursor.getColumnIndex("id")));
            adv.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            adv.setEstado(cursor.getString(cursor.getColumnIndex("estado")));
            adv.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
            adv.setAreaAtuacao(cursor.getString(cursor.getColumnIndex("areaAtuacao")));
            adv.setFotoPerfil(Integer.valueOf(cursor.getString(cursor.getColumnIndex("fotoPerfil"))));
            advogadosList.add(adv);
        }
        return (arrayList);
    }
}
