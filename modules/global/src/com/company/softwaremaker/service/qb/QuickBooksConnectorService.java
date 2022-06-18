package com.company.softwaremaker.service.qb;

import com.intuit.ipp.services.DataService;

public interface QuickBooksConnectorService {
    String NAME = "softwaremaker_QuickBooksConnectorService";
    /**
     * Generates OAuth2 connection URI for QuickBooks service. Stores CSRF in the database for further security check
     * before token exchange.
     *
     * @return QuickBooks OAuth2 URI
     */
    String getOAuth2ConnectUri();

    /**
     * Exchanges QuickBooks Authorization Code for Bearer Token and saves it to database.
     *
     * @param authCode QuickBooks Authorization Code
     * @param state    CSRF (must match with one stored in database)
     * @param realmId  Realm id (aka Company id)
     */
    void exchangeToken(String authCode, String state, String realmId);

    /**
     * Refreshes access token.
     * <p>
     * Throws an exception in case if token does not exist in database or refresh token is expired.
     *
     * @param realmId Realm id (aka Company id)
     * @return Refreshed access token
     */
    String refreshAccessToken(String realmId);

    /**
     * Gets QuickBooks data service. Refreshes token before this.
     *
     * @param realmId Realm id (aka Company id)
     * @return Data Service
     */
    DataService getDataService(String realmId);
}