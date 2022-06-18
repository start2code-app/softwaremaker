package com.company.softwaremaker.web.controller;


import com.company.softwaremaker.service.qb.QuickBooksConnectorService;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.core.sys.SecurityContext;
import com.haulmont.cuba.security.app.TrustedClientService;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.auth.WebAuthConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;

@Controller(QuickBooksCallbackController.NAME)
public class QuickBooksCallbackController {

    public static final String NAME = "softwaremaker_QuickBooksCallbackController";

    @Inject
    private QuickBooksConnectorService quickBooksConnectorService;

    @Inject
    private TrustedClientService trustedClientService;
    @Inject
    private WebAuthConfig webAuthConfig;

    /**
     * This is the redirect handler you configure in your app on developer.intuit.com.
     *
     * Exchanges the Authorization Code for BearerToken.
     */
    @RequestMapping("/qb/callback")
    public String callBackFromOAuth(@RequestParam("code") String authCode, @RequestParam("state") String state,
                                    @RequestParam("realmId") String realmId) {
        String trustedClientPassword = webAuthConfig.getTrustedClientPassword();
        UserSession systemSession = trustedClientService.getSystemSession(trustedClientPassword);
        SecurityContext securityContext = new SecurityContext(systemSession);
        SecurityContext previousSecurityContext = AppContext.getSecurityContext();
        AppContext.setSecurityContext(securityContext);
        try {
            quickBooksConnectorService.exchangeToken(authCode, state, realmId);
        } finally {
            AppContext.setSecurityContext(previousSecurityContext);
        }
        return "redirect:/";
    }
}
