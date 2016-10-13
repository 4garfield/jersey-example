package garfield.java.jersey.example.common.util;

public class DatabaseUtil {
    
    public static boolean checkExists(Long count) {
        if(count == 0L)
            return false;
        else if(count == 1L)
            return true;
        else
            throw new IllegalStateException("illegal count of exist entity: " + count);
    }
}
