package entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class Database {

    //pour la connexion
    private Connection conn;

    //pour les requetes preparees
    private PreparedStatement preState;

    //pour les resultats des requtes de type MAJ (INSERT, DELETE, UPDATE)
    private int ok;

    //pour les resultats des requetes de type SELECT
    private ResultSet result;

    //Methode pour se connecter a la base de donnes
    public void getConnection() {
        String database = "gestion-users";
        String host = "localhost";
        String port = "3306";
        String url = "jdbc:mysql://"+host+":"+port+"/"+database;
        // ou url = "jdbc:mysql://localhost:3306/gestion-users";
        String username = "root";
        String password = "";
        try {
            // Charger le pilote JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établir la connexion
            conn = DriverManager.getConnection(url, username, password);

        }catch (Exception e){
            //e.printStackTrace(); //permet de determiner l'erreur
            System.out.println("erreur");
        }
    }

    //Methode pour initialiser la connexion a la base et preparer la requete
    public void initPrep(String sql){
        try {
            getConnection();//connexion a la base
            preState = conn.prepareStatement(sql);// preparation de la requete
        }catch (Exception e){
            System.out.println("Erreur de preparation de requete");
            //e.printStackTrace(); //permet de determiner l'erreur
        }
    }

    //pour faire la mise a jour des requetes ou traitment
    public int executeChange(){
        try {
          ok = preState.executeUpdate();
        }catch (Exception e){
            System.out.println("erreur lors du changement");
        }

        return ok;//retourne 1 ou 0 par rapport au nombre de ligne inserer ou non a la base

    }

    public ResultSet select(){
        result=null;
        try {
            result = preState.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public void closeConnection(){
        try {
            if( conn != null){
                conn.close();
            }
        }catch (Exception e){
            //e.printStackTrace();
            System.out.println("erreur");
        }
    }

    public PreparedStatement getPreState() {
        return preState;
    }
}
