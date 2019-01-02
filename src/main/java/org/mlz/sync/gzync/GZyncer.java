package org.mlz.sync.gzync;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mlz.sync.gzync.local.parser.ArgParser;
import org.mlz.sync.gzync.local.parser.UnknownCliArgumentException;
import org.mlz.sync.gzync.remote.AuthorizationService;
import org.mlz.sync.gzync.remote.RemoteDriveService;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class GZyncer {

    /** Directory to store user credentials. */
    private static final java.io.File DATA_STORE_DIR =
            new java.io.File(System.getProperty("user.home"), ".store/gzync");
    private static FileDataStoreFactory dataStoreFactory;
    private static final Logger LOG = LogManager.getLogger(GZyncer.class);

    private static final String APPLICATION_NAME = "GZYNC" ;
    /** Global instance of the JSON factory */
    private static final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    /** Global Drive API client. */
    private static Drive drive;
    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;


    public static void main(String... args) throws IOException, GeneralSecurityException {

        try {
            //read arguments

            var argParser = new ArgParser();
            argParser.parse(args);
            dataStoreFactory = new FileDataStoreFactory(GZyncer.DATA_STORE_DIR);

            httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            LOG.debug("datastore setup");
            // authorization
             if ( argParser.isForcedDownload() ){
                 //TODO ask for confirmation
                 // authorization
                 Credential credential = new AuthorizationService(GZyncer.httpTransport, jsonFactory, GZyncer.dataStoreFactory).authorize();
                 drive = new Drive.Builder(GZyncer.httpTransport, GZyncer.jsonFactory, credential).setApplicationName(
                         APPLICATION_NAME).build();
                 RemoteDriveService service = new RemoteDriveService(GZyncer.drive);
                 // TODO add remote folder name
                 service.getAllFilesInFolder(argParser.getRemoteBaseFolder());
             }


        }
        catch(UnknownCliArgumentException exception){
            // TODO print suggestions to stdout
            LOG.error("wrong arguments: ", exception.getMessage());

        } catch(IOException exception){
            LOG.error(exception.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);
    }


}

