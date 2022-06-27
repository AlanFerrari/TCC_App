package br.com.etecia.meus_direitos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "meusdireitos", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_advogados = "CREATE TABLE advogados (idAdvogado INTERGER PRIMARY KEY, " +
                "nome TEXT , " +
                "email TEXT UNIQUE, " +
                "telefone String, " +
                "estado TEXT, " +
                "cidade TEXT, " +
                "numeroOAB TEXT, " +
                "senha TEXT);";
        String sql_areas = "CREATE TABLE areas (idUsuario INTERGER PRIMARY KEY, " +
                "areaAtuacao TEXT, " +
                "bibliografia TEXT, " +
                "idAdvogado TEXT, " +
                "FOREIGN KEY (idAdvogado) REFERENCES advogados(idAdvodo));";

        db.execSQL(sql_advogados);
        db.execSQL(sql_areas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql_upgrade_advogados = "DROP TABLE IF EXISTS advogados;";
        String sql_upgrade_areas = "DROP TABLE IF EXISTS areas;";
        db.execSQL(sql_upgrade_advogados);
        db.execSQL(sql_upgrade_areas);
        onCreate(db);
    }

    public String autenticaUsuario(PerfilUsuario perfilUsuario) {
        SQLiteDatabase db = getReadableDatabase();
        String slq_buscaUsuario = "SELECT * FROM usuario WHERE usuario_email = " + "'" + perfilUsuario.getEmail() + "'";
        Cursor cursor = db.rawQuery(slq_buscaUsuario, null);
        while (cursor.moveToNext()) {
            if (perfilUsuario.getEmail().equals(cursor.getColumnIndex("usuario_email"))) {
                if (perfilUsuario.getSenha().equals(cursor.getColumnIndex("usuario_senha"))) {
                    return "login efetuado com sucesso";
                }
            }
        }
        db.close();
        cursor.close();
        return "Dados inválidos";
    }

    public void inserirDados(PerfilUsuario perfilUsuario) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("nome", perfilUsuario.getNome());
        dados.put("email", perfilUsuario.getEmail());
        dados.put("telefone", perfilUsuario.getEmail());
        dados.put("estado", perfilUsuario.getEstado());
        dados.put("cidade", perfilUsuario.getCidade());
        dados.put("numeroOAB", perfilUsuario.getNumeroOAB());
        dados.put("senha", perfilUsuario.getSenha());

        try {
            db.insertOrThrow("advogados", null, dados);

        } catch (SQLiteConstraintException e) {
            db.update("advogados", dados, "email = ?", new String[]{perfilUsuario.getEmail()});
        }

    }

    public void areasEbibliografia(PerfilUsuario perfilUsuario) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("areaAtuacao", perfilUsuario.getAreaAtuacao());
        dados.put("bibliografia", perfilUsuario.getBibliografia());

        try {
            db.insertOrThrow("advogados", null, dados);

        } catch (SQLiteConstraintException e) {
            db.update("advogados", dados, "email = ?", new String[]{perfilUsuario.getEmail()});
        }

    }

    @SuppressLint("Range")
    public void buscarAdvogados(String orderBy) {

        String sql = "SELECT nome, estado, cidade, areaAtuacao FROM advogados ORDER BY areaAtuacao, estado, cidade;";
        SQLiteDatabase db = getReadableDatabase();

        List<Advogados> advogadosList = new ArrayList<Advogados>();
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
/*
            Advogados adv = new Advogados();
            adv.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            adv.setEstado(cursor.getString(cursor.getColumnIndex("estado")));
            adv.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
            adv.setAreaAtuacao(cursor.getString(cursor.getColumnIndex("areaAtuacao")));
            advogadosList.add(adv);

        return (advogadosList);*/
        }
    }
}