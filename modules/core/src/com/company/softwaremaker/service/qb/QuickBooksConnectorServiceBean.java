package com.company.softwaremaker.service.qb;

import com.company.softwaremaker.config.QuickBooksConfig;
import com.company.softwaremaker.core.qb.QuickBooksClientFactory;
import com.company.softwaremaker.entity.qb.QuickBooksCsrf;
import com.company.softwaremaker.entity.qb.QuickBooksToken;
import com.company.softwaremaker.exception.QuickBooksException;
import com.company.softwaremaker.exception.QuickBooksTokenRefreshException;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewBuilder;
import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuth2Authorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Config;
import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.config.OAuth2Config;
import com.intuit.oauth2.config.Scope;
import com.intuit.oauth2.data.BearerTokenResponse;
import com.intuit.oauth2.exception.InvalidRequestException;
import com.intuit.oauth2.exception.OAuthException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service(QuickBooksConnectorService.NAME)
public class QuickBooksConnectorServiceBean implements QuickBooksConnectorService {

    @Inject
    private QuickBooksConfig quickBooksConfig;
    @Inject
    private QuickBooksClientFactory factory;

    @Inject
    private QuickBooksTokenService quickBooksTokenService;
    @Inject
    private DataManager dataManager;

    @Override
    public String getOAuth2ConnectUri() {
        OAuth2Config oAuth2Config = factory.getOAuth2Config();
        String csrf = oAuth2Config.generateCSRFToken();
        QuickBooksCsrf qbCsrf = dataManager.create(QuickBooksCsrf.class);
        qbCsrf.setCsrf(csrf);
        dataManager.commit(qbCsrf);
        List<Scope> scopes = quickBooksConfig.getScopes().stream()
                .map(Scope::fromValue)
                .collect(Collectors.toList());
        try {
            return oAuth2Config.prepareUrl(scopes, quickBooksConfig.getOAuth2RedirectUri(), csrf);
        } catch (InvalidRequestException e) {
            throw new QuickBooksException("Cannot obtain QuickBooks OAuth URI", e);
        }
    }

    @Override
    public void exchangeToken(String authCode, String state, String realmId) {
        String csrfToken = quickBooksTokenService.getLatestCsrf();
        if (!csrfToken.equals(state)) {
            throw new QuickBooksException("CSRF token mismatch");
        }
        OAuth2PlatformClient client = factory.getOAuth2PlatformClient();
        try {
            BearerTokenResponse bearerTokenResponse = client.retrieveBearerTokens(authCode,
                    quickBooksConfig.getOAuth2RedirectUri());
            QuickBooksToken qbToken = dataManager.create(QuickBooksToken.class);
            qbToken.setRealmId(realmId);
            renewQuickBooksToken(qbToken, bearerTokenResponse);
        } catch (OAuthException e) {
            throw new QuickBooksException("An error occurred while generating QB access token", e);
        }
    }

    @Override
    public String refreshAccessToken(String realmId) {
        QuickBooksToken qbToken = quickBooksTokenService.findLatestToken(realmId, ViewBuilder.of(QuickBooksToken.class)
                .addView(View.LOCAL)
                .build());
        if (qbToken == null) {
            throw new QuickBooksTokenRefreshException("Token is not found");
        }

        if (!qbToken.getAccessTokenExpired()) {
            return qbToken.getAccessToken();
        }

        if (qbToken.getAccessTokenExpired() && !qbToken.getRefreshTokenExpired()) {
            refreshAccessToken(qbToken);
            return qbToken.getAccessToken();
        }
        throw new QuickBooksTokenRefreshException("Refresh token expired");
    }

    @Override
    public DataService getDataService(String realmId) {
        String url = quickBooksConfig.getIntuitAccountingApiHost();
        Config.setProperty(Config.BASE_URL_QBO, url);
        String accessToken = refreshAccessToken(realmId);
        OAuth2Authorizer oauth = new OAuth2Authorizer(accessToken);
        try {
            Context context = new Context(oauth, ServiceType.QBO, realmId);
            return new DataService(context);
        } catch (FMSException e) {
            throw new QuickBooksException("Cannot create Data Service", e);
        }
    }

    private void refreshAccessToken(QuickBooksToken qbToken) {
        try {
            OAuth2PlatformClient client = factory.getOAuth2PlatformClient();
            BearerTokenResponse bearerTokenResponse = client.refreshToken(qbToken.getRefreshToken());
            renewQuickBooksToken(qbToken, bearerTokenResponse);
        } catch (OAuthException e) {
            throw new QuickBooksException("Cannot refresh token", e);
        }
    }

    private void renewQuickBooksToken(QuickBooksToken qbToken, BearerTokenResponse bearerTokenResponse) {
        qbToken.setAccessToken(bearerTokenResponse.getAccessToken());
        qbToken.setRefreshToken(bearerTokenResponse.getRefreshToken());
        qbToken.setExpireTs(convertToExpireTs(bearerTokenResponse.getExpiresIn()));
        qbToken.setRefreshTokenExpireTs(convertToExpireTs(bearerTokenResponse.getXRefreshTokenExpiresIn()));
        dataManager.commit(qbToken);
    }

    private Date convertToExpireTs(Long expiresIn) {
        return new Date(new Date().getTime() + TimeUnit.SECONDS.toMillis(expiresIn));
    }
}