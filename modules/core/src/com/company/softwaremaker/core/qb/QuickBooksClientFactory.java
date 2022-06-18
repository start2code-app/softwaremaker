package com.company.softwaremaker.core.qb;

import com.company.softwaremaker.config.QuickBooksConfig;
import com.haulmont.cuba.core.sys.events.AppContextStartedEvent;
import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.config.Environment;
import com.intuit.oauth2.config.OAuth2Config;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component(QuickBooksClientFactory.NAME)
public class QuickBooksClientFactory {
    public static final String NAME = "softwaremaker_QuickBooksClientFactory";
    @Inject
    private QuickBooksConfig quickBooksConfig;

    private OAuth2PlatformClient client;
    private OAuth2Config oauth2Config;

    @EventListener
    private void init(AppContextStartedEvent event) {
        refreshQuickBooksClient();
    }

    public void refreshQuickBooksClient() {
        oauth2Config = new OAuth2Config.OAuth2ConfigBuilder(quickBooksConfig.getOAuth2AppClientId(),
                quickBooksConfig.getOAuth2ClientSecret())
                .callDiscoveryAPI(Environment.fromValue(quickBooksConfig.getEnvironment()))
                .buildConfig();
        client = new OAuth2PlatformClient(oauth2Config);
    }

    public OAuth2PlatformClient getOAuth2PlatformClient() {
        return client;
    }

    public OAuth2Config getOAuth2Config() {
        return oauth2Config;
    }
}