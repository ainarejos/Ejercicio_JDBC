import java.util.Date;
// Clase para la tabla Encarrecs.
public class Encarrec {
    //Atributos.
    private int id;
    private Date data;
    private int idclient;

    //Constructor.
    public Encarrec(int i, Date n, int p){
        id= i;
        data=n;
        idclient=p;
    }

    //Getters.
    public int getId() { return id; }
    public Date getData() { return data; }
    public int getIdClient() { return idclient; }
}
