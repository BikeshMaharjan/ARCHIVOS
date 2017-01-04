package com.example.bikesh.archivos;

import android.util.Log;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

/**
 * Created by bikesh on 1/4/17.
 */

public class FTPConnection  {
   FTPClient ftpClient = null;

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

    public void listFiles() throws IOException {
        if(this.ftpClient.isConnected()) {
           // String[] filenames = ftpClient.listNames("/Cassis/STB/CSV");

            //FTPFile[] files = ftpClient.listFiles();
            FTPListParseEngine engine = ftpClient.initiateListParsing("/Cassis/STB/CSV");

            while (engine.hasNext()) {
                FTPFile[] files = engine.getNext(25);  // "page size" you want

                for (FTPFile file : files) {
                    System.out.println(file.getName());
                }

            }
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
