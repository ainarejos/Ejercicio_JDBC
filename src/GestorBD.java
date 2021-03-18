import java.util.*;
import java.sql.*;

public class GestorBD {
    Connection conn;

    public GestorBD() throws Exception{
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/encarrecs_bd",
                "root", "Cide2050");
    }

    public void tancar() throws Exception{
        conn.close();
    }

    public int obtenirNouIDClient() throws Exception{
        Statement cercaMaxId = conn.createStatement();
        ResultSet r = cercaMaxId.executeQuery("Select MAX(ID) FROM CLIENTS");
        if (r.next()) return (1 + r.getInt(1));
        else return 1;
    }

    public int obtenirNouIDProducte() throws Exception{
        Statement cercaMaxId = conn.createStatement();
        ResultSet r = cercaMaxId.executeQuery("Select MAX(ID) FROM PRODUCTES");
        if (r.next()) return (1 + r.getInt(1));
        else return 1;
    }

    public int obtenirNouIDEncarrec() throws Exception{
        Statement cercaMaxId = conn.createStatement();
        ResultSet r = cercaMaxId.executeQuery("Select MAX(ID) FROM ENCARRECS");
        if (r.next()) return (1 + r.getInt(1));
        else return 1;
    }

    public List<Client> cercarClient(String nom) throws Exception {
        Statement cerca = conn.createStatement();
        ResultSet r = cerca.executeQuery("SELECT * FROM CLIENTS WHERE NOM='" + nom
                + "'");
        LinkedList<Client> llista = new LinkedList<Client>();
        while (r.next()){
            llista.add(new Client(r.getInt("ID"), r.getString("NOM"), r.getString(
                    "APOSTAL"), r.getString("AELECTRONICA"), r.getString("TELEFON")));
        }
        return llista;
    }


    public void afegirClient(Client c) throws Exception{
        Statement update = conn.createStatement();
        String valors = c.getId() + ",'" + c.getNom() + "','" + c.getApostal() + "" +
                "','" + c.getAElectronica() + "','" + c.getTelefon() + "'";
        update.executeUpdate("INSERT INTO CLIENTS VALUE(" + valors + ")");
    }

    public void afegirProducte(Producte c) throws Exception{
        Statement update = conn.createStatement();
        String valors = c.getId() + ",'" + c.getNom() + "','" + c.getPreu()+ "','" + c.getUnitat() +"'";
        update.executeUpdate("INSERT INTO PRODUCTES VALUE(" + valors + ")");
    }

    public void llistarProductes() throws Exception{
        Statement llistar = conn.createStatement();
        ResultSet r = llistar.executeQuery("SELECT * FROM PRODUCTES");
        while (r.next()){
            System.out.println("ID: " + r.getInt("ID") + ", Nom: " + r.getString("NOM")
                    + ", Preu: " + r.getFloat("PREU"));
        }
    }

    public Timestamp covert(java.util.Date data){
        Timestamp dataSql=new Timestamp(data.getTime());
        return dataSql;
    }

    public void afegirEncarrec(Encarrec e) throws Exception{
        Statement update = conn.createStatement();
        String valors = e.getId() + ",'" + e.getData() + "','" + e.getIdClient()+ "'";
        update.executeUpdate("INSERT INTO ENCARRECS VALUE(" + valors + ")");
    }

    public void lliurarUnitatsProducte(String producto, int unitats) throws SQLException {
        Statement lliurar = conn.createStatement();
        boolean r = lliurar.execute("UPDATE PRODUCTES SET UNITAT=UNITAT-" + unitats + " WHERE NOM='" + producto + "'" );
    }

    public void eliminarEncarrec(int id) throws SQLException {
        Statement eliminar = conn.createStatement();
        boolean r = eliminar.execute("DELETE FROM ENCARRECS WHERE ID=" + id);
    }

    public void consulatrEncarrec(int client) throws Exception{
        Statement consultar = conn.createStatement();
        ResultSet r = consultar.executeQuery("SELECT * FROM ENCARRECS WHERE IDCLIENT=" + client);
        while (r.next()){
            System.out.println("ID: " + r.getInt("ID") + ", Data: " + r.getString("DATA")
                    + ", IdClient: " + r.getInt("IDCLIENT"));
        }
    }
}
