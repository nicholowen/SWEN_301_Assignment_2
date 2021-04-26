package nz.ac.wgtn.swen301.assignment2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sun.javafx.UnmodifiableArrayList;
import org.apache.log4j.*;
import org.apache.log4j.spi.LoggingEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MemAppender extends AppenderSkeleton {

    public static List<LoggingEvent> loggingEvents =  new ArrayList<>(); //stores logging events
    public String name;
    public long maxSize = 1000;
    public static long discardedLogs = 0;

    public MemAppender(){

    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        loggingEvents.add(loggingEvent);
        if(loggingEvents.size() >= maxSize){
            loggingEvents.remove(0); // removes the zeroth event log (the oldest).
            discardedLogs++; // counts how many logs have been discarded.
        }
    }

    //Logs can be accessed using the following non-static method
    //This returns a list of all logs (max 1000)
    public List<LoggingEvent> getCurrentLogs(){
        return Collections.unmodifiableList(loggingEvents);
    }

    public long getDiscardedLogCount(){
        return discardedLogs;
    }

    /*
    This method exports the log events currently stored into a JSON file.
    The JSON file should contain a JSON array containing JSON objects.
    Each JSON object represents a log event, the log
     */
    public void exportToJSON(String fileName) {

        try (FileWriter file = new FileWriter(fileName)) {

            //writes a all logging events to file after being formatted by JSONLayout
            JSONLayout layout = new JSONLayout();
            for(LoggingEvent lg : loggingEvents){
                String formatted = layout.format(lg);
                file.write(formatted);
            }
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        this.closed = true;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }


}
