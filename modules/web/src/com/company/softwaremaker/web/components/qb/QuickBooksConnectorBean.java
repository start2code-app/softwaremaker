package com.company.softwaremaker.web.components.qb;

import com.company.softwaremaker.service.qb.QuickBooksConnectorService;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.web.AppUI;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component(QuickBooksConnectorBean.NAME)
public class QuickBooksConnectorBean {
    public static final String NAME = "softwaremaker_QuickBooksConnectorBean";

    @Inject
    private QuickBooksConnectorService quickBooksConnectorService;

    /**
     * Opens QuickBooks authorization page.
     */
    public void openOAuth2Page() {
        String connectUri = quickBooksConnectorService.getOAuth2ConnectUri();
        AppUI.getCurrent().getWebBrowserTools().showWebPage(connectUri, ParamsMap.of("target", "_self"));
    }
}