package entities.dao;

import entities.Database;
import entities.Role;
import entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoleImpl implements IRole{
    private Database db = new Database();
    private ResultSet rs ;
    private int ok;
    @Override
    public Role saisie(Role role) {
        Scanner s = new Scanner(System.in);
        System.out.println("Veuillez saisir le nom du role");
        role.setName(s.nextLine());
        return role;
    }

    @Override
    public int add(Role role) {
        String sql = "INSERT INTO role(name) VALUES(?)";
        try {
            db.initPrep(sql);
            db.getPreState().setString(1,role.getName());
            ok = db.executeChange();
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM role WHERE id=?";
        try {
            db.initPrep(sql);
            db.getPreState().setInt(1,id);
            ok = db.executeChange();
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public int update(Role role) {
        String sql = "UPDATE role SET name=? WHERE id=?";
        try {
            db.initPrep(sql);
            db.getPreState().setString(1,role.getName());
            db.getPreState().setInt(2,role.getId());
            ok = db.executeChange();
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public Role get(int id) {
        String sql = "SELECT * FROM role WHERE id=?";
        Role role = null;
        try {
            db.initPrep(sql);
            db.getPreState().setInt(1,id);
            rs = db.select();
            if(rs.next()){
                role = new Role();
                role.setName(rs.getString("name"));
                role.setId(rs.getInt("id"));
            }
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public List<Role> list() {
        List<Role> lst = new ArrayList<>();
        String sql = "SELECT * FROM role ORDER BY id ASC";
        try {
            db.initPrep(sql);
            rs=db.select();
            while (rs.next()){
                Role r = new Role();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                lst.add(r);
            }
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return lst;
    }

    @Override
    public int showListe() {
        if(!list().isEmpty()){
            for(Role rl : list()){
                System.out.print("id: "+ rl.getId());
                System.out.print(" || name "+ rl.getName());
            }
            return 1;
        }
        System.out.println("Vous ne possedez aucun role");
        return 0;
    }
}
