package nz.ac.wgtn.swen301.assignment2;

import com.sun.javafx.UnmodifiableArrayList;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.List;

public class MemAppender {

    public List<LoggingEvent> loggingEvents; //stores logging events
    public String name;
    public long maxSize = 1000;

    //Logs can be accessed using the following non-static method
    public List<LoggingEvent> getCurrentLogs(){
        ArrayList<LoggingEvent> loggingEvents = new ArrayList<>();


//        return new UnmodifiableArrayList<LoggingEvent>(loggingEvents);
        return null;
    }



    public long getDiscardedLogCount(){

        return 0;
    }

    /*
    This method exports the log events currently stored into a JSON file.
    The JSON file should contain a JSON array containing JSON objects.
    Each JSON object represents a log event, the log
     */
    public void exportToJSON(String fileName){
        //stores logs into a JSON file.

    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }



}
