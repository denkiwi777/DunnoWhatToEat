package com.dunnoWhatToEat.snapshot.utility;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;


public class SesionCreator {
    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                configure().build();
        Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
}
