package garfield.java.jersey.example.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogExceptionUtil {
    
    private static final Log logger = LogFactory.getLog(LogExceptionUtil.class);
    
    public static void logExceptionStackTrace(Throwable th) {
        final StringWriter writer = new StringWriter();
        th.printStackTrace(new PrintWriter(writer));
        logger.error(writer.toString());
    }
}
