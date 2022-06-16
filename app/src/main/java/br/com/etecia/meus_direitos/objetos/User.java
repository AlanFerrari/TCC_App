package br.com.etecia.meus_direitos.objetos;

public class User {
    private int id;
    private String usuario;
    private String email;
    private String cidade;
    private String estado;
    private String numero_oab;
    private String telefone_cel;



    public User(int id, String usuario, String email, String cidade, String estado, String numero_oab, String telefone_cel ) {
        this.id = id;
        this.usuario = usuario;
        this.email = email;
        this.cidade = cidade;
        this.estado = estado;
        this.numero_oab = numero_oab;
        this.telefone_cel = telefone_cel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNumero_oab() {
        return numero_oab;
    }

    public void setNumero_oab(String numero_oab) {
        this.numero_oab = numero_oab;
    }

    public String getTelefone_cel() {
        return telefone_cel;
    }

    public void setTelefone_cel(String telefone_cel) {
        this.telefone_cel = telefone_cel;
    }
}
