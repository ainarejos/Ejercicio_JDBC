import java.util.*;
import java.sql.*;
import java.util.jar.Attributes;

/*La clase GestorBD es la clase donde se realiza la conexión con la base de datos,
* además de realizar la conexión, en esta clase se encuentras los diferentes metodos
* para realizar las querys necesarias. */
public class GestorBD {
    //Conexión con la base de datos.
    Connection conn;
    public GestorBD() throws Exception{
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_bd",
                "root", "Cide2050");
    }

    //Cierre de la conexión.
    public void tancar() throws Exception{
        conn.close();
    }

    /*Los tres siguientes metodos buscan la id mas alta que hay en la tabla en el momento de la
    ejecución y le suma 1 para obtener el siguiente id.*/
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

    /*Este metodo añade a una lista los clientes que tengan el nombre pasado
    * como parametro y finalmente devuelve la lista.*/
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


    //Este metodo añade a la tabla cliente el cliente que se pasa como parametro.
    public void afegirClient(Client c) throws Exception{
        Statement update = conn.createStatement();
        String valors = c.getId() + ",'" + c.getNom() + "','" + c.getApostal() + "" +
                "','" + c.getAElectronica() + "','" + c.getTelefon() + "'";
        update.executeUpdate("INSERT INTO CLIENTS VALUE(" + valors + ")");
    }

    //Este metodo añade a la tabla Productes el producto que se pasa como parametro.
    public void afegirProducte(Producte c) throws Exception{
        Statement update = conn.createStatement();
        String valors = c.getId() + ",'" + c.getNom() + "','" + c.getPreu()+ "','" + c.getUnitat() +"'";
        update.executeUpdate("INSERT INTO PRODUCTES VALUE(" + valors + ")");
    }

    //Este metodo lista todos los productos de la tabla Productes.
    public void llistarProductes() throws Exception{
        Statement llistar = conn.createStatement();
        ResultSet r = llistar.executeQuery("SELECT * FROM PRODUCTES");
        while (r.next()){
            System.out.println("ID: " + r.getInt("ID") + ", Nom: " + r.getString("NOM")
                    + ", Preu: " + r.getFloat("PREU") + ", Unitats: " + r.getInt("UNITATS"));
        }
    }

    //Metodo para convertir un Date a Timestamp.
    public Timestamp covert(java.util.Date data){
        Timestamp dataSql=new Timestamp(data.getTime());
        return dataSql;
    }

    //Este metodo añade un encargo a la tabla Encarrec, el encargo se pasa como parametro.
    public void afegirEncarrec(Encarrec e) throws Exception{
        Statement update = conn.createStatement();
        String valors = e.getId() + ",'" + e.getData() + "','" + e.getIdClient()+ "'";
        update.executeUpdate("INSERT INTO ENCARRECS VALUE(" + valors + ")");
    }

    //Este metodo resta unidades a los productos, como parametro se pasa el nombre y las unidades.
    public void lliurarUnitatsProducte(String producto, int unitats) throws Exception {
        Statement lliurar = conn.createStatement();
        Statement comprovar = conn.createStatement();
        ResultSet c = comprovar.executeQuery("SELECT UNITATS FROM PRODUCTES WHERE NOM='" + producto + "'");
        while (c.next()){
            if (c.getInt("UNITATS") < unitats){
                throw new Exception("No hay suficientes unidades del producto: " + producto);
            }
        }
        boolean r = lliurar.execute("UPDATE PRODUCTES SET UNITATS=UNITATS-" + unitats + " WHERE NOM='" + producto + "' && UNITATS>=" + unitats );
    }

    //Este metodo elmina encargos a partir de su id.
    public void eliminarEncarrec(int id) throws SQLException {
        Statement eliminar = conn.createStatement();
        boolean r = eliminar.execute("DELETE FROM ENCARRECS WHERE ID=" + id);
    }

    //Este metodo muestra los encargos de un determinado cliente que se le pasa por parametros.
    public void consulatrEncarrec(int client) throws Exception{
        Statement consultar = conn.createStatement();
        ResultSet r = consultar.executeQuery("SELECT * FROM ENCARRECS WHERE IDCLIENT=" + client);
        System.out.println("----------------------------------------------");
        while (r.next()){
            System.out.println("ID: " + r.getInt("ID") + ", Data: " + r.getString("DATA")
                    + ", IdClient: " + r.getInt("IDCLIENT"));
        }
    }

    //Este metodo obtiene el id del producto mediante su nombre.
    public int obtenirIDprodcute(String producte) throws Exception {
        int result = 0;
        Statement obtenir = conn.createStatement();
        ResultSet r = obtenir.executeQuery("SELECT ID FROM PRODUCTES WHERE NOM='" + producte + "'");
        while (r.next()){
            result=r.getInt("ID");
        }
        if (result == 0){
            throw new Exception("El producto: " + producte + " no esta en la lista de productos");
        }
        return result;
    }

    /*Este metodo añade filas a la tabla EncarrecProductes con la información que se le pasa
    por parametro*/
    public void afegirEncarrecsProductes(EncarrecsProductes e) throws Exception{
        Statement update = conn.createStatement();
        String valors = e.getIdencarrec() + ",'" + e.getIdproducte() + "','" + e.getNumarticlest()+ "'";
        update.executeUpdate("INSERT INTO encarrecsproductes VALUE(" + valors + ")");
    }

    //Este metodo elimina las filas de un encaro pasado por parametro.
    public void eliminarEncarrecProdcutes(int id) throws Exception{
        Statement eliminar = conn.createStatement();
        boolean r = eliminar.execute("DELETE FROM ENCARRECSPRODUCTES WHERE IDENCARREC=" + id);
    }
}
