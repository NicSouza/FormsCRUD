package formscrud;

/**
 *
 * @author nicol
 */
import java.util.Date;

public class viagemForm {
    
    private Integer passagem;
    private String origem;
    private String destino;
    private Date dataIda;
    private Date dataChegada;
    
    public viagemForm(Integer passagem, String origem, String destino, Date dataIda, Date dataChegada){
        this.passagem = passagem;
        this.origem = origem;
        this.destino = destino;
        this.dataIda = dataIda;
        this.dataChegada = dataChegada;
    }
    
    public Integer getPassagem(){
        return passagem;
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

