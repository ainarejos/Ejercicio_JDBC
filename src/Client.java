//Clase para la tabla Clients.
public class Client {
    //Atributos
    private int id;
    private String nom;
    private String apostal;
    private String aElectronica;
    private String telefon;

    //Constructor.
    public Client(int i, String n, String ap, String ae, String t){
        id= i;
        nom=n;
        apostal = ap;
        aElectronica=ae;
        telefon=t;
    }

    //Getters.
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getApostal() { return apostal; }
    public String getAElectronica() { return aElectronica; }
    public String getTelefon() { return telefon; }
}
