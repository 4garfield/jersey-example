package garfield.java.jersey.example.common.model;

import org.apache.commons.lang3.StringUtils;

import garfield.java.jersey.example.common.constant.OAuthConstant;

public class OAuthParam {
    
    private String authzEndpoint;
    private String redirectUrl;
    private String scope;
    private String tokenEndpoint;
    private String cliendId;
    private String clientSecret;
    private String resourceUrl;
    
    public String getAuthzEndpoint() {
        return authzEndpoint;
    }
    public void setAuthzEndpoint(String authzEndpoint) {
        this.authzEndpoint = authzEndpoint;
    }
    public String getRedirectUrl() {
        return redirectUrl;
    }
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
    public String getScope() {
        return scope;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }
    public String getTokenEndpoint() {
        return tokenEndpoint;
    }
    public void setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }
    public String getCliendId() {
        return cliendId;
    }
    public void setCliendId(String cliendId) {
        this.cliendId = cliendId;
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    public String getResourceUrl() {
        return resourceUrl;
    }
    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
    
    public static OAuthParam constructByProvider(String provider) {
        OAuthParam param = new OAuthParam();
        if(StringUtils.equalsIgnoreCase(provider, OAuthConstant.GOOGLE)) {
            param.setAuthzEndpoint(OAuthConstant.GOOGLE_AUTHZ);
            param.setCliendId(OAuthConstant.GOOGLE_CLIENT_ID);
            param.setRedirectUrl(OAuthConstant.GOOGLE_REDIRECT_URL);
            param.setScope(OAuthConstant.GOOGLE_SCOPE);
            param.setTokenEndpoint(OAuthConstant.GOOGLE_TOKEN);
            param.setClientSecret(OAuthConstant.GOOGLE_CLIENT_SECRET);
            param.setResourceUrl(OAuthConstant.GOOGLE_RESOURCE_URL);
        } else if (StringUtils.equalsIgnoreCase(provider, OAuthConstant.GITHUB)) {
            param.setAuthzEndpoint(OAuthConstant.GITHUB_AUTHZ);
            param.setCliendId(OAuthConstant.GITHUB_CLIENT_ID);
            param.setRedirectUrl(OAuthConstant.GITHUB_REDIRECT_URL);
            param.setScope(OAuthConstant.GITHUB_SCOPE);
            param.setTokenEndpoint(OAuthConstant.GITHUB_TOKEN);
            param.setClientSecret(OAuthConstant.GITHUB_CLIENT_SECRET);
            param.setResourceUrl(OAuthConstant.GITHUB_RESOURCE_URL);
        } else {
            throw new IllegalArgumentException("invalid oauth2 provider: " + provider);
        }
        return param;
    }
    
}
