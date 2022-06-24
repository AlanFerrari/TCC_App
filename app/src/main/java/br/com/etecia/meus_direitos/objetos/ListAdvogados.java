package br.com.etecia.meus_direitos.objetos;

public class ListAdvogados {

    long id;
    String nome;
    String cidade;
    String estado;
    String areaAtuacao;
    Integer fotoPerfil;

    public ListAdvogados() {

    }

    public ListAdvogados(long id, String nome, String cidade, String estado, String areaAtuacao, Integer fotoPerfil) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
        this.areaAtuacao = areaAtuacao;
        this.fotoPerfil = fotoPerfil;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    public Integer getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(Integer fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}
