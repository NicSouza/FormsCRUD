package formscrud;

/**
 *
 * @author nicol
 */
public class pessoaForm {
    
    private Integer cpf;
    private String nome;
    private String nacionalidade;
    private String sexo;
    
    public pessoaForm(Integer cpf, String nome, String nacionalidade, String sexo){
        this.cpf = cpf;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.sexo = sexo;
    }
    
    public Integer getCpf(){
        return cpf;
    }
    
    public String getNome(){
        return nome;
    }
    
    public String getNacionalidade(){
        return nacionalidade;
    }
    
    public String getSexo(){
        return sexo;
    }
}
