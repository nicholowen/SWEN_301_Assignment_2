package nz.ac.wgtn.swen301.assignment2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.javafx.UnmodifiableArrayList;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemAppender extends AppenderSkeleton {

    public static List<LoggingEvent> loggingEvents; //stores logging events
    public String name;
    public long maxSize = 1000;
    public static long discardedLogs = 0;

    private static Logger LOGGER;

    public MemAppender(){
        BasicConfigurator.configure();
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        if(loggingEvents.size() >= maxSize){
            loggingEvents.remove(0);
            discardedLogs++;
        }
        loggingEvents.add(loggingEvent);
    }

    //Logs can be accessed using the following non-static method
    public List<LoggingEvent> getCurrentLogs(){
        return Collections.unmodifiableList(this.loggingEvents);
    }

    public long getDiscardedLogCount(){
        return discardedLogs;
    }

    /*
    This method exports the log events currently stored into a JSON file.
    The JSON file should contain a JSON array containing JSON objects.
    Each JSON object represents a log event, the log
     */
    public void exportToJSON(String fileName){
        //stores logs into a JSON file.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        LOGGER = Logger.getLogger(fileName);
        JSONLayout jsonLayout = new JSONLayout(gson.toJson(LOGGER));


    }

    @Override
    public void close() {

    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }


}
