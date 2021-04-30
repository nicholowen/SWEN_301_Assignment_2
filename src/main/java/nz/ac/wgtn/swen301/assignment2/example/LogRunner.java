package nz.ac.wgtn.swen301.assignment2.example;


import nz.ac.wgtn.swen301.assignment2.MemAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Random;

public class LogRunner {

    private static String[] randMessage = {"beep beep", "logs are people too", "cannon fodder", "it's just a painting", "acme TNT"};
    private static Level[] randLevels = {Level.INFO, Level.DEBUG, Level.WARN, Level.ERROR, Level.FATAL};
    private static MemAppender memAppender = new MemAppender("MBeanAppender");

    public static void main(String[] args) throws InterruptedException {
        try {
            ObjectName objectName = new ObjectName("MemAppenderMBean:type="+memAppender.getName());
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            server.registerMBean(memAppender, objectName);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException |
                MBeanRegistrationException | NotCompliantMBeanException e) {
            // handle exceptions
        }
        Logger logger = Logger.getLogger(LogRunner.class);
        Random r = new Random();

        int time = 20;
        while(time >= 0) {

            LoggingEvent lg = new LoggingEvent("MBeanLogEvent", logger, randLevels[r.nextInt(5)], randMessage[r.nextInt(5)], null);
            System.out.println("Time remaining: " + time + " seconds - " + lg.getLevel().toString() + " : " + lg.getMessage());
            memAppender.append(lg);
            Thread.sleep(1000);
            time--;
        }
    }
}
