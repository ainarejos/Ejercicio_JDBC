//Clase para la tabla EncarrecsProductes.
public class EncarrecsProductes {
    //Atributos.
    private int idencarrec;
    private int idproducte;
    private int numarticles;

    //Constructor.
    public EncarrecsProductes(int i, int n, int p){
        idencarrec= i;
        idproducte=n;
        numarticles=p;
    }

    //Getters
    public int getIdencarrec() { return idencarrec; }
    public int getIdproducte() { return idproducte; }
    public int getNumarticlest() { return numarticles; }
}
