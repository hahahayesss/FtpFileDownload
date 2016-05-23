package com.haha.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties props = new Properties();

    public static void init(String propsPath) {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream propertyStream = propsPath != null ? new FileInputStream(propsPath) : loader
                    .getResourceAsStream("3h-ftp.properties");
            props.load(propertyStream);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public static String getProperty(String property, String defaultValue) {
        return props.getProperty(property, defaultValue);
    }

    public static String getFtpServerAddress() {
        return props.getProperty("ftp.server.address");
    }

    public static Integer getFtpServerPort() {
        if (props.getProperty("ftp.server.port").equals("")) return 21;
        else return Integer.parseInt(props.getProperty("ftp.server.port"));
    }

    public static String getFtpUserName() {
        return props.getProperty("ftp.server.username");
    }

    public static String getFtpUserPassword() {
        return props.getProperty("ftp.server.userpassword");
    }

    public static String getFtpFilePath() {
        return props.getProperty("ftp.file.path");
    }

    public static Integer getFtpDownloadLimit() {
        if (props.getProperty("ftp.file.download.limit").equals("")) return 25;
        else return Integer.parseInt(props.getProperty("ftp.file.download.limit"));
    }
}
