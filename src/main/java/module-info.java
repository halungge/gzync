module org.mlz.sync.gzync {
    requires google.api.services.drive.v3.rev136;
    requires google.http.client.jackson2;
    requires google.api.client;
    requires google.oauth.client;
    requires com.google.api.client;
    requires google.oauth.client.java6;
    requires google.oauth.client.jetty;

    exports org.mlz.sync.gzync;

}