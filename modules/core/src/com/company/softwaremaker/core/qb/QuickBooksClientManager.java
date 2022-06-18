package com.company.softwaremaker.core.qb;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("softwaremaker_QuickBooksClientManager")
public class QuickBooksClientManager implements QuickBooksClientManagerMBean {


    @Inject
    private QuickBooksClientFactory quickBooksClientFactory;

    @Override
    public String refreshQuickBooksClient() {
        quickBooksClientFactory.refreshQuickBooksClient();
        return "Refreshed successfully";
    }
}