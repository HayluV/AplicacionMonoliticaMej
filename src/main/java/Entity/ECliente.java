package Entity;
public class ECliente {
    private int idcliente;

    private String descnombre,descapellido,descemail,numtelefono;
    private float mntsaldo;
    public ECliente(){}

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }
    
    public String getDescnombre() {
        return descnombre;
    }

    public void setDescnombre(String descnombre) {
        this.descnombre = descnombre;
    }

    public String getDescapellido() {
        return descapellido;
    }

    public void setDescapellido(String descapellido) {
        this.descapellido = descapellido;
    }

    public String getDescemail() {
        return descemail;
    }

    public void setDescemail(String descemail) {
        this.descemail = descemail;
    }

    public String getNumtelefono() {
        return numtelefono;
    }

    public void setNumtelefono(String numtelefono) {
        this.numtelefono = numtelefono;
    }

    public float getMntsaldo() {
        return mntsaldo;
    }

    public void setMntsaldo(float mntsaldo) {
        this.mntsaldo = mntsaldo;
    }
    
}
