package Services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DbContext {
    private static StandardServiceRegistry registry = null;
    private static SessionFactory sessionFactory = null;
    private static DbContext context;

    private DbContext() {
        registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    public static Session getSession() {
        if (context == null)
            new DbContext();
        return context.sessionFactory.openSession();
    }
}
