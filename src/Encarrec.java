import java.util.Date;

public class Encarrec {
    private int id;
    private Date data;
    private int idclient;

    public Encarrec(int i, Date n, int p){
        id= i;
        data=n;
        idclient=p;
    }

    public int getId() { return id; }
    public Date getData() { return data; }
    public int getIdClient() { return idclient; }
}
