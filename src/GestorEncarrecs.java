import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.InflaterOutputStream;

public class GestorEncarrecs {
    GestorBD gestor;
    BufferedReader entrada;
    public static void main(String[] args) throws Exception {
        GestorEncarrecs gbd = new GestorEncarrecs();
        gbd.start();
    }
    public GestorEncarrecs() throws Exception{
        gestor = new GestorBD();
        entrada = new BufferedReader(new InputStreamReader(System.in));
    }
    public void start() throws Exception{
        int opcio;
        while (0 != (opcio = menuPrincipal())){
            try{
                switch (opcio){
                    case 1:
                        cercarClient();
                        break;
                    case 2:
                        afegirClient();
                        break;
                    case 3:
                        afegirProducte();
                        break;
                    case 4:
                        llistarProductes();
                        break;
                    case 5:
                        afegirEncarrec();
                        break;
                    case 6:
                        eliminarEncarrec();
                        break;
                    case 7:
                        consultarEncarrec();
                        break;
                    default: mostrarDades("Opció incorrecta\n");
                }
            }catch (Exception ex){
                mostrarDades("Opció incorrecta\n");
            }
        }
        gestor.tancar();
    }
    private int menuPrincipal() throws Exception{
        String menu = "\nQuina acció vols realitzar?\n" + "[1] Cercar client\n" + "" +
                "[2] Afegir client\n" + "[3] Afegir Producte\n" + "[4] Llistar Producte\n"
                + "[5] Afegir Encarrec\n" + "[6] Eliminar Encarrec\n" + "[7] Consultar Encarrec\n"
                + "[0] Sortir\n" + "Opció>";
        String lin=entrarDades(menu);
        try{int opcio = Integer.parseInt(lin); return opcio;
        }catch (Exception ex){ return -1;}
    }

    /** Amb els metodes entrarDades i mostrarDades, fem el codi independent de la interficie.
     *    Si mai es fan canvis, nomes cal canviar aquests dos metodes */
    private String entrarDades(String pregunta) throws IOException {
        mostrarDades(pregunta);
        return entrarDades();
    }

    private String entrarDades() throws IOException{
        String linia = entrada.readLine();
        if ("".equals(linia)) return null;
        return linia;
    }

    private void mostrarDades(String dades) throws IOException {
        System.out.print(dades);
    }

    private void cercarClient() throws  Exception{
        String nom = entrarDades("Introdueix el nom del client: "); if (null == nom) return;
        List<Client> llista = gestor.cercarClient(nom);
        Iterator it = llista.iterator();
        mostrarDades("Els clients trobats amb aquest nom son:\n−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−\n");
        while (it.hasNext()){
            Client c = (Client)it.next();
            mostrarDades(c.toString() + "\n");
        }
    }

    public void afegirClient() throws Exception {
        mostrarDades("Introduex les seguents dades del nou usuari (deixa en blanc" +
                " per sortir).\n");
        String nom = entrarDades("Nom: "); if (null == nom) return;
        String apostal = entrarDades("Adreça postal: "); if (null == apostal)
            return;
        String aelectronica = entrarDades("E-mail: "); if (null == aelectronica)
            return;
        String telefon = entrarDades("Telefon: "); if (null == telefon) return;
        int id = gestor.obtenirNouIDClient();
        gestor.afegirClient(new Client(id, nom, apostal, aelectronica, telefon));
        mostrarDades("Operació completada satisfactòriament.\n");
    }

    public void afegirProducte() throws Exception {
        mostrarDades("Introduex les seguents dades del nou producte (deixa en blanc" +
                " per sortir).\n");
        String nom = entrarDades("Nom: "); if (null == nom) return;
        float preu =Float.parseFloat(entrarDades("Preu: ")); if (0.00 == preu)
            return;
        int unitat=Integer.parseInt(entrarDades("Unitats:")); if (0 == unitat)
            return;

        int id = gestor.obtenirNouIDProducte();
        gestor.afegirProducte(new Producte(id, nom, preu, unitat));
        mostrarDades("Operació completada satisfactòriament.\n");
    }

    public void llistarProductes() throws Exception {
        gestor.llistarProductes();
        mostrarDades("Operació completada satisfactòriament.\n");
    }

    public void afegirEncarrec() throws Exception{
        mostrarDades("Introduex les seguents dades del nou encarrec (deixa en blanc" +
                " per sortir).\n");
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date data =format.parse(entrarDades("Data: ")); if (null==data) return;
        int idclient =Integer.parseInt(entrarDades("IdClient: ")); if (0 == idclient)
            return;
        int id = gestor.obtenirNouIDEncarrec();
        int cuantitat = Integer.parseInt(entrarDades("Cantidad productos: ")); if (0 == cuantitat)
            return;
        for (int i = 0; i <cuantitat ; i++) {
            String producto = entrarDades("Producto: "); if (null == producto) return;
            int unitats = Integer.parseInt(entrarDades("Unitats: ")); if (0 == unitats) return;
            gestor.lliurarUnitatsProducte(producto, unitats);
        }
        gestor.afegirEncarrec(new Encarrec(id, gestor.covert(data), idclient));
        mostrarDades("Operació completada satisfactòriament.\n");
    }

    public void eliminarEncarrec() throws Exception{
        mostrarDades("Introduex 'ID de l'encarrec que es vol elminiar (deixa en blanc" +
                " per sortir).\n");
        int encarrec=Integer.parseInt(entrarDades("id: ")); if (0 == encarrec) return;
        gestor.eliminarEncarrec(encarrec);
        mostrarDades("Operació completada satisfactòriament.\n");
    }

    public void consultarEncarrec() throws Exception{
        mostrarDades("Introduex 'ID del client que es vol consultar els seus encarrecs (deixa en blanc" +
                " per sortir).\n");
        int client=Integer.parseInt(entrarDades("IdClient")); if (0 == client) return;
        gestor.consulatrEncarrec(client);
    }

}