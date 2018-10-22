package ru.stoliarenko.gb.lesson7.server.services;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.deltaspike.jpa.api.entitymanager.PersistenceUnitName;
import org.apache.deltaspike.jpa.api.transaction.TransactionScoped;

public class ProducerCDI {
    private static final String UNIT_NAME = "ENTERPRISE";
    
    @Inject
    @PersistenceUnitName(UNIT_NAME)
    private EntityManagerFactory entityManagerFactory;
    
    @Produces
    @TransactionScoped
    public EntityManager create() {return this.entityManagerFactory.createEntityManager();}
    
    public void dispose(@Disposes EntityManager entityManager) {if(entityManager.isOpen()) entityManager.close();} 
}
