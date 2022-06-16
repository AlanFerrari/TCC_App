package br.com.etecia.meus_direitos.objetos;

import java.math.BigInteger;

public class Subdistritos {
    BigInteger id;
    String nome;

    public Subdistritos(BigInteger id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
