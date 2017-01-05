package com.example.bikesh.archivos.Class;

import android.util.Log;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by bikesh on 1/4/17.
 */

public class FTPConnection  {
   FTPClient ftpClient = null;
    public FTPConnection() {}
    public FTPConnection(String host, String user, String pwd) throws Exception{
        ftpClient = new FTPClient();
        int reply;
        ftpClient.connect(host);
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftpClient.login(user, pwd);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
    }

    public ArrayList<String> listFiles(String directory) throws IOException {
        ArrayList<String> retrievedfiles = new ArrayList<String>();

        if(this.ftpClient.isConnected()) {
            FTPListParseEngine engine = ftpClient.initiateListParsing(directory);

            while (engine.hasNext()) {
                FTPFile[] files = engine.getNext(25);  // "page size" you want
                for (FTPFile file : files) {
                    retrievedfiles.add(file.getName());

                }
            }
        }
        return retrievedfiles;
    }


    public boolean connectionTest(String host, String user, String pwd) throws Exception {
        ftpClient = new FTPClient();
        int reply;
        ftpClient.connect(host);
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftpClient.login(user, pwd);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();

        if(this.ftpClient.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public void disconnect(){
        if (this.ftpClient.isConnected()) {
            try {
                this.ftpClient.logout();
                this.ftpClient.disconnect();
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }
    }
}
