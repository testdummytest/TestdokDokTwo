package UiRegressionTests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class CustomSimpleFormatter extends SimpleFormatter {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public synchronized String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();

        sb.append("[").append(dateFormat.format(new Date(record.getMillis()))).append("]");
        sb.append("[").append(record.getLevel().toString().toLowerCase()).append("]");
        sb.append("[").append(record.getSourceMethodName()).append("]");
        sb.append(" - ").append(formatMessage(record));
        sb.append("\n");

        return sb.toString();
    }
}
