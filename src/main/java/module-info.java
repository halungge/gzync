module org.mlz.sync.gzync {
    requires google.api.services.drive.v3.rev136;
    requires google.http.client.jackson2;
    requires google.api.client;
    requires google.oauth.client;
    requires com.google.api.client;
    requires google.oauth.client.java6;
    requires google.oauth.client.jetty;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires info.picocli;

    opens org.mlz.sync.gzync.local.parser;

    exports org.mlz.sync.gzync;

}