package formscrud;

/**
 *
 * @author nicol
 */
public class lugarForm {
    
    private Integer cnpj;
    private String local;
    private String endereco;
    private String acomodacao;
    
    public lugarForm(Integer cnpj, String local, String endereco, String acomodacao){
        this.cnpj = cnpj;
        this.local = local;
        this.endereco = endereco;
        this.acomodacao = acomodacao;
    }
    
    public Integer getCnpj(){
        return cnpj;
    }
    
    public String getLocal(){
        return local;
    }
    
    public String getEndereco(){
        return endereco;
    }
    
    public String getAcomodacao(){
        return acomodacao;
    }
}
