package ru.otus.servlet;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.servlet.*;
import ru.otus.cache.*;
import ru.otus.db.service.*;
import ru.otus.homework12.*;

public class Main {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "public_html";

    public static void main(String[] args) throws Exception {
        DBServiceHibernateWithCacheImpl dbService = new DBServiceHibernateWithCacheImpl();

        DBWorker dbWorker = new DBWorker(dbService);
        dbWorker.setDaemon(true);
        dbWorker.start();

        Server server = startJettyServer(dbService);
        server.start();
        server.join();
    }

    private static Server startJettyServer(CacheInfo dbService) {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(LoginServlet.class, "/login");
        context.addServlet(CacheMonitorServlet.class, "/monitor");
        context.addServlet(new ServletHolder(new CacheParametersServlet(dbService)), "/cache");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));
        return server;
    }
}
