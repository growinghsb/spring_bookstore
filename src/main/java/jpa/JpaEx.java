package jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaEx {
    public static void main(String[] args){
       EntityManagerFactory emf =  Persistence.createEntityManagerFactory("");

    }
}
