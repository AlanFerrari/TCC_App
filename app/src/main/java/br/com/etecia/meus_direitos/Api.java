package br.com.etecia.meus_direitos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.etecia.meus_direitos.objetos.PerfilUsuario;

public class Api extends SQLiteOpenHelper {
    public Api(Context context){
        super(context, "banco", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE advogados(nome TEXT, email TEXT, telefone Integer, estado TEXT, cidade TEXT, numeroOAB TEXT, senha TEXT);";
            db.execSQL(sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS advogados;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insereAdvogado(PerfilUsuario perfilUsuario){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("nome", perfilUsuario.getNome());
        dados.put("email", perfilUsuario.getEmail());
        dados.put("telefone", perfilUsuario.getTelefone());
        dados.put("estado", perfilUsuario.getEstado());
        dados.put("cidade", perfilUsuario.getCidade());
        dados.put("numeroOAB", perfilUsuario.getNumeroOAB());
        dados.put("senha", perfilUsuario.getSenha());

        db.insert("advogados", null, dados);
    }

    @SuppressLint("Range")
    public List<PerfilUsuario> buscaAdvogado(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM pessoa;";

        Cursor c = db.rawQuery(sql, null);

        List<PerfilUsuario> perfilUsuarios = new ArrayList<PerfilUsuario>();

        while (c.moveToNext()){
            PerfilUsuario perfilUsuario = new PerfilUsuario();
            perfilUsuario.setNome(c.getString(c.getColumnIndex("nome")));
            perfilUsuario.setEmail(c.getString(c.getColumnIndex("email")));
            perfilUsuario.setTelefone(Integer.valueOf(c.getString(c.getColumnIndex("telefone"))));
            perfilUsuario.setEstado(c.getString(c.getColumnIndex("estado")));
            perfilUsuario.setCidade(c.getString(c.getColumnIndex("cidade")));
            perfilUsuario.setNumeroOAB(c.getString(c.getColumnIndex("numeroOAB")));
            perfilUsuario.setSenha(c.getString(c.getColumnIndex("senha")));
            perfilUsuarios.add(perfilUsuario);
        }
        return perfilUsuarios;
    }

}
