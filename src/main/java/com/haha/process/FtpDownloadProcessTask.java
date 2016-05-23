package com.haha.process;

import com.haha.downloader.FileDownloader;
import com.haha.properties.PropertiesUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FtpDownloadProcessTask implements StatefulJob {

    private Logger logger = LoggerFactory.getLogger(FtpDownloadProcessTask.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            logger.info("FtpDownload job");
            if (PropertiesUtil.getFtpServerAddress().equals("")) {
                throw new Exception("ftp.server.address not found");
            }

            if (PropertiesUtil.getFtpUserName().equals("")) {
                throw new Exception("ftp.server.username not found");
            }

            if (PropertiesUtil.getFtpUserPassword().equals("")) {
                throw new Exception("ftp.server.userpassword not found");
            }

            if (PropertiesUtil.getDownloadedFileLocation().equals("")) {
                throw new Exception("downloaded.file.location not found");
            }

            if (PropertiesUtil.getLogPath().equals("")) {
                throw new Exception("log.path not found");
            }

            FileDownloader fileDownloader = new FileDownloader();
            fileDownloader.downloadFile();
        } catch (IOException e) {
            logger.error("Error :" + e.getMessage());
        } catch (Exception e) {
            logger.error("Error :" + e.getMessage());
        }
    }
}
