package com.company.softwaremaker.service.qb;

import com.company.softwaremaker.entity.qb.QuickBooksToken;
import com.haulmont.cuba.core.global.View;

import javax.annotation.Nullable;

public interface QuickBooksTokenService {
    String NAME = "softwaremaker_QuickBooksTokenService";

    /**
     * Searches for a QuickBooks token with corresponding realm. Returns null if no such exist.
     *
     * @param realmId QuickBooks realm id
     * @param view    View
     * @return QuickBooks token or null
     */
    @Nullable
    QuickBooksToken findLatestToken(String realmId, View view);

    /**
     * Gets latest CSRF token. Throws an exception if no such exist.
     *
     * @return CSRF token
     */
    String getLatestCsrf();
}