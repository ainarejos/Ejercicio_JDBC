public class EncarrecsProductes {
    private int idencarrec;
    private int idproducte;
    private int numarticles;

    public EncarrecsProductes(int i, int n, int p){
        idencarrec= i;
        idproducte=n;
        numarticles=p;
    }

    public int getId() { return idencarrec; }
    public int getData() { return idproducte; }
    public int getIdClient() { return numarticles; }
}
