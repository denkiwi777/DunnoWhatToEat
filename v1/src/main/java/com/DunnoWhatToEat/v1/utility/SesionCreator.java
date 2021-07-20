package com.DunnoWhatToEat.v1.utility;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;


public class SesionCreator {
    private static SessionFactory sessionFactory;

    private SessionFactory buildSessionFactory() {
        ServiceRegistry serviceRegistry = (ServiceRegistry) new StandardServiceRegistryBuilder().
                configure().build();
        Metadata metadata = new MetadataSources((org.hibernate.service.ServiceRegistry) serviceRegistry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        return sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
}
