package modelo;

public class Usuario {
    
    private int id;
    private String nome;
    private String usuario;
    private String senha;
    private String sexo;
    private String email;
    private String acesso;

    public Usuario(String nome, String usuario, String senha, String sexo, String email, String acesso) {
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.sexo = sexo;
        this.email = email;
        this.acesso = acesso;
    }

    public Usuario() {
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getAcesso() {
        return acesso;
    }

    public void setAcesso(String acesso) {
        this.acesso = acesso;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
