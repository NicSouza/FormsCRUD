package formscrud;

/**
 *
 * @author nicol
 */
import java.util.Date;

public class viagemForm {
    
    private String origem;
    private String destino;
    private Date dataIda;
    private Date dataChegada;
    
    public viagemForm(String origem, String destino, Date dataIda, Date dataChegada){
        this.origem = origem;
        this.destino = destino;
        this.dataIda = dataIda;
        this.dataChegada = dataChegada;
    }
    
    public String getOrigem(){
        return origem;
    }
    
    public String getDestino(){
        return destino;
    }
    
    public Date getDataIda(){
        return dataIda;
    }
    
    public Date getDataChegada(){
        return dataChegada;
    }
}

