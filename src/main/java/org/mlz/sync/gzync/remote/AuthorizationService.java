package org.mlz.sync.gzync.remote;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mlz.sync.gzync.GZyncer;

import java.io.InputStreamReader;
import java.util.Collections;

public class AuthorizationService {
    public static final String ACCESS_SCOPE = DriveScopes.DRIVE_READONLY;
    private final HttpTransport httpTransport;
    private final JsonFactory jsonFactory;


    private Logger LOG = LogManager.getLogger(AuthorizationService.class);
    /**
     * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
     * globally shared instance across your application.
     */
    private  FileDataStoreFactory dataStoreFactory;


    //TODO move this to central store or main class?

    public AuthorizationService(HttpTransport httpTransport, JsonFactory jsonFactory, FileDataStoreFactory dataStoreFactory) {
        this.httpTransport = httpTransport;
        this.jsonFactory = jsonFactory;
        this.dataStoreFactory = dataStoreFactory;
    }


    /** Authorizes the installed application to access user's protected data. */
    public Credential authorize() throws Exception {


        // load client secrets
        final InputStreamReader reader = new InputStreamReader(GZyncer.class.getResourceAsStream("/client_id.json"));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
                reader);

        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {

            LOG.info("client_id.json is missing");
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=drive "
                            + "into gzync/src/main/resources/client_id.json");
            System.exit(1);
        }
        // set up authorization code flow
        LOG.info("requesting drive access Scope {}", ACCESS_SCOPE);
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets,
                Collections.singleton(ACCESS_SCOPE)).setDataStoreFactory(dataStoreFactory)
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
}
