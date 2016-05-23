package com.haha.logger;

import com.haha.properties.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LogService {
    private static Logger logger = LoggerFactory.getLogger(LogService.class);

    public static void logFilesCreate() throws IOException {
        try {
            if (PropertiesUtil.getLogPath().equals("")) {
                throw new Exception("log.path not found");
            }

            File downloadLogFile = new File(PropertiesUtil.getLogPath());
            downloadLogFile.createNewFile();
        } catch (Exception e) {
            logger.error("Error :" + e.getMessage());
        }

    }

    public static void infoDownload(String log) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(PropertiesUtil.getLogPath(), true));
        bufferedWriter.append(log);
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    public static Map<String, String> getDownloadLogModel() throws IOException {
        Map<String, String> map = new HashMap<String, String>();

        String line = "";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(PropertiesUtil.getLogPath()));
        while ((line = bufferedReader.readLine()) != null) {
            String[] row_arr = line.split("\\|");
            if (row_arr.length > 0) {
                map.put(row_arr[0], row_arr[0] + "|" + row_arr[1] + "|" + row_arr[2]);
            }
        }

        return map;
    }
}
