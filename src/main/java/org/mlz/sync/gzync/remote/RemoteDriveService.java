package org.mlz.sync.gzync.remote;


import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class RemoteDriveService {
    private static final Logger LOG = LogManager.getLogger(RemoteDriveService.class);

    private Drive drive;

    public RemoteDriveService(Drive drive) {
        this.drive = drive;
    }


    public void getChangesOnRootFolder() {


    }

    public void getAllFilesInFolder(String remoteBaseFolder) {
        try {
           String fields="nextPageToken, files(id, name,parents,mimeType, trashed, createdTime, modifiedTime, " +
            "modifiedTime, modifiedByMe, shared, ownedByMe, capabilities(canDelete, canEdit))";
            String currentToken = null;
            FileList fileList = drive.files().list().setSpaces("drive").setPageToken(currentToken).setFields(fields).execute();
            final String pageToken = fileList.getNextPageToken();
            LOG.info("new page token :"+ pageToken);
            
            final List<File> files = fileList.getFiles();
            for(File f:files){
                final String fileId = f.getId();
                LOG.info("id: {}", fileId);
                final List<String> parents =   f.getParents();
                LOG.info("parents:");
                for(String p : parents ){
                    LOG.info("- {}", p);
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}





