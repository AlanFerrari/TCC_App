package br.com.etecia.meus_direitos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.etecia.meus_direitos.objetos.ListAdvogados;

public class DBHelper extends SQLiteOpenHelper {
    
    public static final String DBNAME = "meusdireitos";
    public DBHelper(Context context){
        super(context, "meusdireitos", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE advogados" +
                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nome VARCHAR(50) NOT NULL, " +
                        "email VARCHAR(50) NOT NULL, " +
                        "telefone Integer NOT NULL, " +
                        "estado VARCHAR(20) NOT NULL, " +
                        "cidade VARCHAR(30) NOT NULL, " +
                        "numeroOAB VARCHAR(20) NOT NULL, " +
                        "senha TEXT NOT NULL," +
                        "areaAtuacao VARCHAR(100)," +
                        "bibliografia VARCHAR(500)," +
                        "fotoPerfil INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS advogados;");
        onCreate(db);
    }

    public Boolean Cadastro(String nome, String email, String telefone, String estado, String cidade, String numeroOAB, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nome", nome);
        values.put("email", email);
        values.put("telefone", telefone);
        values.put("estado", estado);
        values.put("cidade", cidade);
        values.put("numeroOAB", numeroOAB);
        values.put("senha", senha);

        long result = db.insert("advogados", null, values);
        if (result == -1) return false;
        else
            return true;
    }
    public Boolean checandoEmailDoUsuario(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM advogados WHERE email = ?", new  String[] {email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Boolean checandoEmailESenhaDoUsuario(String email, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM advogados WHERE email = ? AND senha = ?", new  String[] {email, senha});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean PegarDadosDoBanco(String nome, String email, String telefone, String estado, String cidade, String registroOAB){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nome, email, telefone, estado, cidade, registroOAB FROM advogados WHERE email = ?", new String[] {nome, email, telefone, estado, cidade, registroOAB});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

}
