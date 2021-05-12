package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfProperties {
    static Properties properties;

    private ConfProperties(){
        throw new IllegalStateException();
    }

    static {
        try (FileInputStream fileInputStream = new FileInputStream("conf.properties")){
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}
