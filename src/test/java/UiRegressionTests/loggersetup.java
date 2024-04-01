package UiRegressionTests;
import java.util.logging.FileHandler;
import java.io.IOException;
import java.util.logging.*;

public class loggersetup {
    
    private static final Logger logger;

    static {
        logger = Logger.getLogger(loggersetup.class.getName());
        try {
            FileHandler fileHandler = new FileHandler("docDokLog.log", true); // Set autoFlush to true
            logger.addHandler(fileHandler);
            fileHandler.setFormatter(new CustomSimpleFormatter()); // Using custom formatter
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Logger getLogger() {
        return logger;
    }
}