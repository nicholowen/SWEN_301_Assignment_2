package nz.ac.wgtn.swen301.assignment2;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Random;
import java.util.TimerTask;

public class LogRunner {

    static String[] randMessage = {"beep beep", "logs are people too", "cannon fodder", "it's just a painting", "acme TNT"};
    static Level[] randLevels = {Level.INFO, Level.DEBUG, Level.WARN, Level.ERROR, Level.FATAL};
    static MemAppender memAppender = new MemAppender("MBeanAppender");

    public static void main(String[] args) throws InterruptedException {
        try {
            ObjectName objectName = new ObjectName("nz.ac.wgtn.swen301.assignment2:type=basic,name=MemAppender");
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            server.registerMBean(new MemAppender("MBeanAppender"), objectName);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException |
                MBeanRegistrationException | NotCompliantMBeanException e) {
            // handle exceptions
        }
        Logger logger = Logger.getLogger(LogRunner.class);
        Random r = new Random();

        int time = 120;
        while(time >= 0) {
            System.out.println("Time remaining: " + time + " seconds");
            LoggingEvent lg = new LoggingEvent("MBeanLogEvent", logger, randLevels[r.nextInt(5)], randMessage[r.nextInt(5)], null);
            memAppender.append(lg);
            Thread.sleep(1000);
            time--;
        }




    }

}
