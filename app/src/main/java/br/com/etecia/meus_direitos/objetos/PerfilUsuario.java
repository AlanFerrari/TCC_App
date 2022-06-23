package br.com.etecia.meus_direitos.objetos;

public class PerfilUsuario {
    private int id;
    private String nome;
    private String email;
    private Integer telefone;
    private String estado;
    private String cidade;
    private String numeroOAB;
    private String senha;

    public PerfilUsuario(int id, String nome, String email, Integer telefone, String estado, String cidade, String numeroOAB, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.estado = estado;
        this.cidade = cidade;
        this.numeroOAB = numeroOAB;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getNumeroOAB() {
        return numeroOAB;
    }

    public void setNumeroOAB(String numeroOAB) {
        this.numeroOAB = numeroOAB;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
