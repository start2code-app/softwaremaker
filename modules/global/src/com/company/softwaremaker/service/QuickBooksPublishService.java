package com.company.softwaremaker.service;

import com.company.softwaremaker.entity.qb.PublishError;
public interface QuickBooksPublishService {
    String NAME = "softwaremaker_QuickBooksPublishService";

    PublishError publishToQB();
}