package it.unicam.cs.mpgc.jtime119159.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DatabaseManager {

    private static SessionFactory sessionFactory;

    public static void initialize() {
        if (sessionFactory != null) {
            return;
        }

        // Configures settings from hibernate.properties (if found) or defaults
        // Since we don't have hibernate.properties, we might need to configure
        // programmatically or add resources
        // Ideally we should used hibernate.cfg.xml or persistence.xml

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble
            // building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize Hibernate SessionFactory", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
