package com.haha.downloader;

import com.haha.logger.LogService;
import com.haha.properties.PropertiesUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class FileDownloader {

    private static Logger logger = LoggerFactory.getLogger(FileDownloader.class);

    private String path() {
        String ftpFilePath = "";

        if (!PropertiesUtil.getFtpFilePath().equals("")) {
            ftpFilePath = PropertiesUtil.getFtpFilePath() + "/" + PropertiesUtil.getFtpFileNames();
        } else {
            ftpFilePath = PropertiesUtil.getFtpFileNames();
        }
        return ftpFilePath;
    }

    private ArrayList<String> readAllFile() throws IOException {
        int downloadFileLimit = PropertiesUtil.getFtpDownloadLimit();
        ArrayList<String> lst = new ArrayList<String>();
        Map<String, String> map = LogService.getDownloadLogModel();

        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(PropertiesUtil.getFtpServerAddress(), PropertiesUtil.getFtpServerPort());
        ftpClient.login(PropertiesUtil.getFtpUserName(), PropertiesUtil.getFtpUserPassword());
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        for (FTPFile x : ftpClient.listFiles(path())) {
            if (map.get(x.getName()) == null) {
                if (downloadFileLimit > 0 || downloadFileLimit == -1) {
                    lst.add(x.getName());
                    if (downloadFileLimit > 0) {
                        downloadFileLimit--;
                    }
                } else {
                    break;
                }
            }
        }
        ftpClient.disconnect();
        return lst;
    }

    private String ftpFilePath() {
        if (PropertiesUtil.getFtpFilePath().equals("")) {
            return "";
        } else {
            return PropertiesUtil.getFtpFilePath() + "/";
        }
    }

    public void downloadFile() throws Exception {
        ArrayList<String> lst = readAllFile();

        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(PropertiesUtil.getFtpServerAddress(), PropertiesUtil.getFtpServerPort());
        ftpClient.login(PropertiesUtil.getFtpUserName(), PropertiesUtil.getFtpUserPassword());
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        for (String x : lst) {
            File tempFileDownload = File
                    .createTempFile("kron.", ".tmp", new File(System.getProperty("java.io.tmpdir")));

            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFileDownload));
            ftpClient.retrieveFile(ftpFilePath() + x, outputStream);
            outputStream.close();

            File localDownloadFilePath = new File(PropertiesUtil.getDownloadedFileLocation() + "/" + x);
            Path tempFilePath = Paths.get(tempFileDownload.getAbsolutePath());
            Path localFilePath = Paths.get(localDownloadFilePath.getAbsolutePath());
            Files.move(tempFilePath, localFilePath);
            tempFileDownload.deleteOnExit();

            FTPFile[] file = ftpClient.listFiles((ftpFilePath() + x));
            LogService.infoDownload(x + "|" + new Date().toString() + "|" + file[0].getSize());
            logger.debug("Download File :" + x);
        }
        ftpClient.disconnect();
    }
}
