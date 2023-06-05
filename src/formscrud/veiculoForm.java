package formscrud;

/**
 *
 * @author nicol
 */
public class veiculoForm {
    
    private Integer placa;
    private String tipo;
    private String modelo;
    private String cor;
    
    public veiculoForm(Integer placa, String tipo, String modelo, String cor){
        this.placa = placa;
        this.tipo = tipo;
        this.modelo = modelo;
        this.cor = cor;
    }
    
    public Integer getPlaca(){
        return placa;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public String getModelo(){
        return modelo;
    }
    
    public String getCor(){
        return cor;
    }
}
