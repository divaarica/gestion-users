package entities.dao;
//regex
import java.util.regex.*;

//Bcrypt
import entities.Role;
import org.mindrot.jbcrypt.BCrypt;

import entities.Database;
import entities.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserImpl implements IUser{

    private Database db = new Database();
    private int ok;// ok = 1 ou 0 suivant le nombre de collonne retourner par la requete

    private ResultSet rs; //resultat des requetes select

    @Override
    public User saisie(User u) {
        int choix;
        Scanner s = new Scanner(System.in);
        System.out.println("Donner l'email");
        u.setEmail(s.nextLine());
        do {
            System.out.println("Donner le mot de passe");
            u.setPwd(s.nextLine());
            if (!validatePassword(u.getPwd())){
                System.out.println("Mot de passe incorrecte");
            }
        } while (!validatePassword(u.getPwd()));
        u.setPwdhashed(hashPassword(u.getPwd()));

        System.out.println("Liste des role");
        IRole roleMethod = new RoleImpl();
        for(Role r : roleMethod.list()){
            System.out.println("- "+ r.getId() + " " + r.getName());
        }
        do{
            System.out.println("Faites le choix du role");
            choix = s.nextInt();
            if (choix<=0 || choix>roleMethod.list().size()){
                System.out.println("choix incorrecte");
            }
        }while (choix<=0 || choix>roleMethod.list().size());
        u.setIdRole(choix);

        return u;
    }


    @Override
    public int add(User u) {
        String sql = "INSERT INTO users(email, password, passwordHashed, idRole) VALUES (?,?,?,?)" ;
        try{
            db.initPrep(sql);
            db.getPreState().setString(1, u.getEmail());
            db.getPreState().setString(2, u.getPwd());
            db.getPreState().setString(3, u.getPwdhashed());
            db.getPreState().setInt(4, u.getIdRole());
            ok = db.executeChange();
            db.closeConnection();

        }catch (Exception e){
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try {
            db.initPrep(sql);
            db.getPreState().setInt(1, id);
            ok = db.executeChange();
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public int update(User u) {
        String sql = "UPDATE users SET email=?, password=?, passwordHashed=?, idRole=? WHERE id =? ";
        try{
            db.initPrep(sql);
            db.getPreState().setString(1, u.getEmail());
            db.getPreState().setString(2, u.getPwd());
            db.getPreState().setString(3, u.getPwdhashed());
            db.getPreState().setInt(4, u.getIdRole());
            db.getPreState().setInt(5, u.getId());
            ok = db.executeChange();
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        return ok;
    }

    @Override
    public User get(int id) {
        String sql = "SELECT * FROM users WHERE id=? ";
        User u = null;
        try{
            db.initPrep(sql);
            db.getPreState().setInt(1,id);
            rs = db.select();
            if(rs.next()){
                u = new User();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setPwd(rs.getString("password"));
                u.setPwdhashed(rs.getString("passwordHashed"));
                u.setIdRole(rs.getInt("idRole"));
            }
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();

        }
        return u;
    }

    public List<User> list(){
        List<User> lst = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id ASC";
        try {
            db.initPrep(sql);
            rs=db.select();
            while (rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setPwd(rs.getString("password"));
                u.setPwdhashed(rs.getString("passwordHashed"));
                u.setIdRole(rs.getInt("idRole"));
                lst.add(u);
            }
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return lst;
    }

    public int showListe(){
        if(!list().isEmpty()){
            for(User l : list()){
                System.out.print("id: "+ l.getId());
                System.out.print(" || email "+ l.getEmail());
                System.out.print(" || password "+ l.getPwd());
                System.out.println(" || password hashed "+ l.getPwdhashed());
            }
            return 1;
        }
        System.out.println("Vous ne possedez aucun utilisateur");
        return 0;
    }

    //Methode d'hachage
    public static String hashPassword(String Password) {
        // ou return BCrypt.hashpw(Password, BCrypt.gensalt(12));
        return BCrypt.hashpw(Password, BCrypt.gensalt());
    }

    //methode pour  verfier si le mot de passe coreespond a l'hachage
    public static boolean checkPassword(String password, String hashedPassword) {
        // Vérifier le mot de passe
        return BCrypt.checkpw(password, hashedPassword);
    }

    public static boolean validatePassword(String password) {
        // Supprimer tous les espaces
        password = password.replaceAll("\\s", "");

        // Au moins une lettre, au moins un chiffre et au moins un caractère spécial
        if (Pattern.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=]).+$", password)) {
            return true;
        }
        return false;
    }




}
