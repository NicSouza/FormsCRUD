package formscrud;

/**
 *
 * @author nicol
 */
public class lugarForm {
    
    private String local;
    private String endereco;
    
    public lugarForm(String local, String endereco){
        this.local = local;
        this.endereco = endereco;
    }
    
    public String getLocal(){
        return local;
    }
    
    public String getEndereco(){
        return endereco;
    }
}
