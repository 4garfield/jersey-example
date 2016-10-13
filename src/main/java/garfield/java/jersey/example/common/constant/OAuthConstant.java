package garfield.java.jersey.example.common.constant;

import org.apache.oltu.oauth2.common.OAuthProviderType;

public final class OAuthConstant {
    
    // common value for oauth2 token expire value
    public static final String EXPIRESIN = "3600";
    
    public static final String GOOGLE = OAuthProviderType.GOOGLE.getProviderName();
    public static final String GOOGLE_AUTHZ = OAuthProviderType.GOOGLE.getAuthzEndpoint();
    public static final String GOOGLE_REDIRECT_URL = "http://localhost:8080/JerseyExample/login/google/oauth2callback";
    public static final String GOOGLE_SCOPE = "profile";
    public static final String GOOGLE_TOKEN = OAuthProviderType.GOOGLE.getTokenEndpoint();
    public static final String GOOGLE_CLIENT_ID = "424047167845-2iqqd7udlkiia19n1g8ptevdp8jkcmpd.apps.googleusercontent.com";
    public static final String GOOGLE_CLIENT_SECRET = "m5II76ToDqO1CgS1ZUqs9sxH";
    // validate token URL: https://www.googleapis.com/oauth2/v1/tokeninfo?access_token={token}
    public static final String GOOGLE_RESOURCE_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    
    public static final String GITHUB = OAuthProviderType.GITHUB.getProviderName();
    public static final String GITHUB_AUTHZ = OAuthProviderType.GITHUB.getAuthzEndpoint();
    public static final String GITHUB_REDIRECT_URL = "http://localhost:8080/JerseyExample/login/github/oauth2callback";
    public static final String GITHUB_SCOPE = "user";
    public static final String GITHUB_TOKEN = OAuthProviderType.GITHUB.getTokenEndpoint();
    public static final String GITHUB_CLIENT_ID = "54158c25269b827b6342";
    public static final String GITHUB_CLIENT_SECRET = "ba13b08b239cfe2a66df6b8993db6a931f055597";
    public static final String GITHUB_RESOURCE_URL = "https://api.github.com/user";
    
}
