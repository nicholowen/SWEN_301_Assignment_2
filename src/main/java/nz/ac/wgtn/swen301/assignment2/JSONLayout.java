package nz.ac.wgtn.swen301.assignment2;

import com.google.gson.Gson;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

public class JSONLayout extends Layout {
    private String logger;
    private String level;
    private long starttime;
    private String thread;
    private String message;

    public JSONLayout(){}

    public JSONLayout(String loggingEvent){

    }

    @Override
    public String format(LoggingEvent loggingEvent) {
        return null;
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    @Override
    public void activateOptions() {

    }
}
