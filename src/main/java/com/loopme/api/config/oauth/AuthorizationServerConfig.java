package com.loopme.api.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String CLIEN_ID = "loopme";
    private static final String CLIENT_SECRET = "loopme-secret";
    private static final String GRANT_TYPE_PASSWORD = "password";
    private static final String REFRESH_TOKEN = "refresh_token";

    private static final String SCOPE_READ = "read";
    private static final String SCOPE_WRITE = "write";
    private static final String TRUST = "trust";

    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24; //24h
    private static final int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 48; //48h

    private static final String ROLE_CLIENT = "ROLE_CLIENT";
    private static final String ROLE_TRUSTED_CLIENT = "ROLE_TRUSTED_CLIENT";

    private static final String RESOURCE_ID = "resource_id";

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserApprovalHandler userApprovalHandler;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .allowFormAuthenticationForClients()
                // we're allowing access to the token only for clients with 'ROLE_TRUSTED_CLIENT' authority
                .tokenKeyAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
                .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer
                .inMemory()
                .withClient(CLIEN_ID)
                .secret(CLIENT_SECRET)
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, REFRESH_TOKEN)
                .authorities(ROLE_CLIENT, ROLE_TRUSTED_CLIENT)
                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
                .resourceIds(RESOURCE_ID)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS).
                refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager);
    }
}