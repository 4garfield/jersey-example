package garfield.java.jersey.example.common.util;

import java.util.Arrays;

public final class ObjectUtil {
    
    public static int hashCode(Object[] arr) {
        return Arrays.deepHashCode(arr);
    }
    
    public static boolean equals(Object[] arr1, Object[] arr2) {
        return Arrays.deepEquals(arr1, arr2);
    }
}
