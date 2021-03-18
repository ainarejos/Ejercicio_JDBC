public class EncarrecsProductes {
    private int idencarrec;
    private int idproducte;
    private int numarticles;

    public EncarrecsProductes(int i, int n, int p){
        idencarrec= i;
        idproducte=n;
        numarticles=p;
    }

    public int getIdencarrec() { return idencarrec; }
    public int getIdproducte() { return idproducte; }
    public int getNumarticlest() { return numarticles; }
}
