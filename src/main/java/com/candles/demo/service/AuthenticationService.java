package com.candles.demo.service;
import com.candles.demo.security.ApiKeyAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final String authTokenHeaderName;
    private final String authToken;

    public AuthenticationService(@Value("${auth.token.name}") String authTokenHeaderName,
                                 @Value("${auth.token}") String authToken) {
        this.authTokenHeaderName = authTokenHeaderName;
        this.authToken = authToken;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(authTokenHeaderName);
        if (request.getDispatcherType().equals(jakarta.servlet.DispatcherType.ERROR)) {
            return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
        }
        if (apiKey == null || !apiKey.equals(authToken)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}
