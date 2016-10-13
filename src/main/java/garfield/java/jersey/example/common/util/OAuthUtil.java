package garfield.java.jersey.example.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

public class OAuthUtil {
    
    // common value for all oauth2 provider state parameter
    private static final byte[] STATE = "the OAuth2 state provided and verified by client, authz server just return it.".getBytes(StandardCharsets.UTF_8);
    
    public static String generateState() {
        return Base64.encodeBase64String(STATE);
    }
    
    public static boolean verifyState(String state) {
        byte[] sta = Base64.decodeBase64(state);
        return Arrays.deepEquals(ArrayUtils.toObject(sta), ArrayUtils.toObject(STATE));
    }
}
