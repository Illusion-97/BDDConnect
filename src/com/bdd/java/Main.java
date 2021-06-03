package com.bdd.java;

import java.sql.*;
import java.util.List;
import java.util.Random;


public class Main {

    public static void main(String[] args) {

        try (FruitJdbcDAO fruitDao = new FruitJdbcDAO()) {

            System.out.println("FindAll :");
            List<Fruit> lF = fruitDao.findAll();
            for (Fruit f : lF) {
                System.out.println("id : " + f.getId() +", name : " + f.getName() + ", DLC : " + f.getDlc());
            }

            System.out.println("\nFind random :");
            Fruit f = fruitDao.findById((long) new Random().nextInt(lF.size()-1));
            System.out.println("id : " + f.getId() +", name : " + f.getName() + ", DLC : " + f.getDlc());

            System.out.println("\nCreate");
            Fruit aFruit = new Fruit("Prune");
            if(fruitDao.create(aFruit))
                System.out.println("Nouveau Fruit = id : " + aFruit.getId() +", name : " + aFruit.getName() + ", DLC : " + aFruit.getDlc());

            System.out.println("\nUpdate");
            aFruit.setName("Grenadine");
            aFruit.setDlc(aFruit.getDlc().plusDays(5));
            if(fruitDao.update(aFruit))
                System.out.println("Fruit mis à jour = id : " + aFruit.getId() +", name : " + aFruit.getName() + ", DLC : " + aFruit.getDlc());

            System.out.println("\nDelete");
            if(fruitDao.delete(aFruit.getId()))
                System.out.println("Fruit supprimé : " + aFruit.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}



