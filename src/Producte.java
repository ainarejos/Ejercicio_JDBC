public class Producte {
    private int id;
    private String nom;
    private float preu;
    private int unitat;

    public Producte(int i, String n, float p, int u){
        id= i;
        nom=n;
        preu=p;
        unitat=u;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public float getPreu() { return preu; }
    public int getUnitat() { return unitat; }
}
