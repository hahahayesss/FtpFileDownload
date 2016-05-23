package com.haha.main;

import com.haha.process.FtpDownloadProcessTask;
import com.haha.properties.PropertiesUtil;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

public class FtpDownloader {
    private static Logger logger = LoggerFactory.getLogger(FtpDownloader.class);
    private static final String DEFAULT_JOB_EXPR = "0 0/5 * * * ?";

    public static void main(String[] args) throws SchedulerException {
        if (args.length > 0) {
            PropertiesUtil.init(args[0]);
        } else {
            PropertiesUtil.init(null);
        }

        StdSchedulerFactory stdFactory = new StdSchedulerFactory(PropertiesUtil.getQuartzProperties());
        final Scheduler scheduler = stdFactory.getScheduler();
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                try {
                    scheduler.shutdown();
                } catch (SchedulerException e) {
                }
            }
        });

        JobDetail jobDetail = new JobDetail("FTPDownloadProcess", FtpDownloadProcessTask.class);
        scheduler.addJob(jobDetail, true);
        CronTrigger trigger1 = new CronTrigger("FTPDownloadProcessTrigger");

        try {
            trigger1.setCronExpression(PropertiesUtil.getProperty("FTPDownloadProcessTrigger.expression", DEFAULT_JOB_EXPR));
        } catch (ParseException e) {
            try {
                trigger1.setCronExpression(DEFAULT_JOB_EXPR);
            } catch (ParseException e1) {
                logger.error("Error :" + e.getMessage());
            }
        }
        trigger1.setStartTime(new Date());
        trigger1.setJobName("FTPDownloadProcess");
        scheduler.scheduleJob(trigger1);

        scheduler.start();
    }
}
