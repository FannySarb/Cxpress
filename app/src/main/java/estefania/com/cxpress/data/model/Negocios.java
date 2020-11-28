package estefania.com.cxpress.data.model;

public class Negocios {
    int id;
    String nombre;
    String numero;
    String foto;
    String hapertura;
    String hcierre;
    int idMercado;
    int idVendedor;

    public Negocios(int id, String nombre) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getHapertura() {
        return hapertura;
    }

    public void setHapertura(String hapertura) {
        this.hapertura = hapertura;
    }

    public String getHcierre() {
        return hcierre;
    }

    public void setHcierre(String hcierre) {
        this.hcierre = hcierre;
    }

    public int getIdMercado() {
        return idMercado;
    }

    public void setIdMercado(int idMercado) {
        this.idMercado = idMercado;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }



}
