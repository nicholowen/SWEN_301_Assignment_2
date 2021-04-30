package nz.ac.wgtn.swen301.assignment2;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Test;

public class JSONLayoutTest {

    @Test
    public void test_ParsedValuesAssertEquals(){
        JSONLayout layout = new JSONLayout();
        Logger logger = Logger.getLogger(JSONLayout.class);
        LoggingEvent lg = new LoggingEvent("JSONLayoutTest", logger, Level.WARN, "Warning, this is a test!", null);
        String json = layout.format(lg);
        JsonObject jsonObject = new JsonObject();
        jsonObject.getAsJsonObject(json);

        Assert.assertEquals(jsonObject.get("level").toString(), "\"WARN\"");
        Assert.assertEquals(jsonObject.get("message").toString(), "\"Warning, this is a test!\"" );
        Assert.assertEquals(jsonObject.get("logger").toString(), "\"nz.ac.wgtn.swen301.assignment2.JSONLayout\"");
    }


}
