package com.company.softwaremaker.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;
import com.haulmont.cuba.core.config.defaults.DefaultString;
import com.haulmont.cuba.core.config.type.Factory;
import com.haulmont.cuba.core.config.type.StringListStringify;
import com.haulmont.cuba.core.config.type.StringListTypeFactory;
import com.haulmont.cuba.core.config.type.Stringify;
import com.haulmont.cuba.core.global.Secret;

import java.util.List;

@Source(type = SourceType.DATABASE)
public interface QuickBooksConfig extends Config {

    /**
     * @return OAuth2 app client id.
     */
    @Property("qb.oauth2.appClientId")
    String getOAuth2AppClientId();

    /**
     * @return OAuth2 app client secret.
     */
    @Secret
    @Property("qb.oauth2.appClientSecret")
    String getOAuth2ClientSecret();

    /**
     * @return OAuth2 redirect uri. Must contain real host and lead to "/qb/callback" rest controller.
     */
    @Property("qb.oauth2.redirectUri")
    @DefaultString("http://localhost:8080/gcsplatform/rest/qb/callback")
    String getOAuth2RedirectUri();

    /**
     * @return Intuit accounting api host. Could be either production or sandbox one.
     */
    @Property("qb.intuitAccountingApiHost")
    @DefaultString("https://sandbox-quickbooks.api.intuit.com/v3/company")
    String getIntuitAccountingApiHost();

    /**
     * @return QuickBooks environment. Could be either 'PRODUCTION' or 'SANDBOX'.
     */
    @Property("qb.environment")
    @DefaultString("SANDBOX")
    String getEnvironment();

    /**
     * @return QuickBooks access scopes.
     */
    @Property("qb.scopes")
    @Factory(factory = StringListTypeFactory.class)
    @Stringify(stringify = StringListStringify.class)
    @Default("ACCOUNTING")
    List<String> getScopes();

    /**
     * @return Counterparty codes that do not require excel invoice to be uploaded to QuickBooks.
     */
    @Property("qb.skipExcelCounterpartyCodes")
    @Factory(factory = StringListTypeFactory.class)
    @Stringify(stringify = StringListStringify.class)
    @Default("ZKB")
    List<String> getSkipExcelCounterpartyCodes();

}