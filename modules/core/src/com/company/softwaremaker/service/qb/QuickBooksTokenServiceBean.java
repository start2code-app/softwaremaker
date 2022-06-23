package com.company.softwaremaker.service.qb;

import com.company.softwaremaker.entity.qb.QuickBooksToken;
import com.company.softwaremaker.exception.QuickBooksException;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.inject.Inject;

@Service(QuickBooksTokenService.NAME)
public class QuickBooksTokenServiceBean implements QuickBooksTokenService {


    @Inject
    private DataManager dataManager;

    @Nullable
    @Override
    public QuickBooksToken findLatestToken(String realmId, View view) {
        return dataManager.load(QuickBooksToken.class)
                .query("select e from softwaremaker_QuickBooksToken e "
                        + "where e.realmId = :realmId "
                        + "order by e.updateTs desc")
                .cacheable(true)
                .parameter("realmId", realmId)
                .view(view)
                .optional()
                .orElse(null);
    }

    @Override
    public String getLatestCsrf() {
        return dataManager.loadValue("select e.csrf from softwaremaker_QuickBooksCsrf e "
                + "order by e.createTs desc", String.class)
                .optional()
                .orElseThrow(() -> new QuickBooksException("CSRF was not found"));
    }
}