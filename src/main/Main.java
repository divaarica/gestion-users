package main;

//Importation des classes et interfaces
import entities.*;
import entities.dao.*;


//Importation des element de jdbc
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        Scanner s = new Scanner(System.in);
        int reponse,cpt;
        String rep;
        //Pour les roles
//
//        do{
//            System.out.println("Veuillez saisir le nombre de role a entrer");
//            rep = s.nextLine();
//            try{
//                reponse = Integer.parseInt(rep);
//                break;
//            }catch (NumberFormatException e){
//                System.out.println("Entree invalide");
//            }
//        }while(true);
//
//            //Saisie et ajout des roles
//        for(int i=0; i<reponse; i++){
//
//            //instanciation d'un objet Roleimpl pour avoir acces au methodes
//            IRole roleMethod = new RoleImpl();
//
//            Role r  = new Role();
//            roleMethod.saisie(r); //saisie
//            roleMethod.add(r);  //ajout au niveau de la base de donnes
//
//        }

            //modification exemple
//        Role r2 = new Role();
//        r2 = roleMethod.get(3);
//        r2.setName("Gerer les dettes");
//        roleMethod.update(r2);

            //supression exemple
//        roleMethod.delete(3);

//        //liste des role
//        for(Role rl : roleMethod.list()){
//            System.out.println("id: "+ rl.getId());
//            System.out.println("name "+ rl.getName());
//
//        }

        int ch,ifList,ok, chModif, id;;
        IUser userMethod = new UserImpl();
        //instanciation d'un objet Userimpl pour avoir acces au methodes
        IRole roleMethod = new RoleImpl();



        do {
            do {
                System.out.println("Menu");
                System.out.println("1- Ajouter un utilisateur");
                System.out.println("2- Modifier les informations d'un utlisateur");
                System.out.println("3- Supprimer un utilisateur");
                System.out.println("4- Liste des utilisateur");
                System.out.println("5- Quitter");
                System.out.println("Veuillez  entrer votre choix");
                ch = s.nextInt();
                if (ch <= 0 || ch > 5) {
                    System.out.println("Entree Invalide , veuillez entrer un nombres parmis ceux qui figurent sur la liste");
                }
            } while (ch <= 0 || ch > 5);
            switch (ch) {
                case 1:
                    System.out.println("*** Ajouter un utilisateur ***");
                    User u1 = new User();
                    userMethod.saisie(u1);
                    userMethod.add(u1);
                    break;

                case 2:
                    ok = 0;
                    System.out.println("*** Modifier les informations d'un utlisateur ***");
                    ifList = userMethod.showListe();//affichage de la liste des utilisatuer et affectation du rslt a ifList

                    if (ifList == 1) { // pour voir si il y a au moins 1  user
                        do {
                            System.out.println("Saisir l'id de l'utilisateur a modifier");
                            id = s.nextInt();
                            boolean idExists = false;
                            for (User user : userMethod.list()) {  //voir si l'id existe dans la liste
                                System.out.println(user.getId());
                                if (user.getId() == id) {
                                    idExists = true;
                                    break;
                                }
                            }
                            if(!idExists){
                                System.out.println("ID invalide. Veuillez entrer un ID valide.");
                            }else {
                                break;
                            }
                        } while (true);

                        User u = userMethod.get(id); //si l'id est valide on recherche le user et on l'affecte a une variable de type User
                        do {
                            do {
                                System.out.println("1- Changer l'email");
                                System.out.println("2- Changer le mot de passe");
                                System.out.println("3- Changer le role");
                                System.out.println("4- Quitter");
                                System.out.println("Faites votre choix");
                                chModif = s.nextInt();
                                if (chModif <= 0 || chModif > 4) {
                                    System.out.println("Entree Incorrecte");
                                }
                            } while (chModif <= 0 || chModif > 4);
                            s.nextLine(); //buffer

                            switch (chModif) {
                                case 1:
                                    System.out.println("Donner la nouvelle adresse mail");
                                    u.setEmail(s.nextLine());
                                    ok = userMethod.update(u);
                                    break;
                                case 2:
                                    ok=0;
                                    System.out.println("Donner l'ancien mot de pass");
                                    if(UserImpl.checkPassword(s.nextLine(),u.getPwdhashed())){
                                        System.out.println("Donner le nouveau mot de passe");
                                        u.setPwd(s.nextLine());
                                        u.setPwdhashed(UserImpl.hashPassword(u.getPwd()));
                                        ok = userMethod.update(u);

                                    }else{
                                        System.out.println("Votre ancien mot de passe est incorrecte veuillez ressayer ulterierement");
                                    }
                                    break;

                                case 3:
                                    System.out.println("Donner le numero du nouveau role de l'utiisateur");
                                    do {
                                        if (roleMethod.showListe() == 1) {
                                            u.setIdRole(s.nextInt());
                                        }
                                        if (u.getIdRole() <= 0 || u.getIdRole() > roleMethod.list().size()) {
                                            System.out.println("Entree incorrecte");
                                        }
                                    } while (u.getIdRole() <= 0 || u.getIdRole() > roleMethod.list().size());
                                    ok = userMethod.update(u);
                                    break;
                            }
                            if (ok == 1) {
                                System.out.println("Changement reussi");
                            } else {
                                System.out.println("Aucune modification n'a ete effectue");
                            }
                        } while (chModif != 4);


                    }
                    break;

                case 3:
                    System.out.println("3- Supprimer un utilisateur");
                    ifList = userMethod.showListe();
                    if (ifList == 1) {
                        do{
                            System.out.println("Saisir l'id de l'utilisateur a supprimer");
                            id = s.nextInt();
                            boolean idExists = false;
                            for (User user : userMethod.list()) {
                                if (user.getId() == id) {
                                    idExists = true;
                                    break;
                                }
                            }
                            if(!idExists){
                                System.out.println("ID invalide. Veuillez entrer un ID valide.");
                                break;
                            }
                        } while (true);
                        User u = userMethod.get(id);
                        ok=userMethod.delete(id);
                        System.out.println(ok);
                        if (ok == 1) {
                            System.out.println("Changement reussi");
                        } else {
                            System.out.println("Aucune modification n'a ete effectue");
                        }


                    }
                    break;
                case 4:
                    System.out.println("4- Liste des utilisateur");
                    userMethod.showListe();
                    break;

            }
        } while (ch != 5);





    }

}
//                    boolean idExists = userList.stream().anyMatch(u -> u.getId() == selectedId);