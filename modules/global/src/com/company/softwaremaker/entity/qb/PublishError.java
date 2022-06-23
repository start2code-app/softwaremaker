package com.company.softwaremaker.entity.qb;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;

@MetaClass(name = "softwaremaker_PublishError")
public class PublishError extends BaseUuidEntity {
    private static final long serialVersionUID = -2382419331118298189L;


    @MetaProperty
    private String errorType;

    public static PublishError of(ErrorType errorType) {
        Metadata metadata = AppBeans.get(Metadata.class);
        PublishError error = metadata.create(PublishError.class);

        error.setErrorType(errorType);
        return error;
    }

    public ErrorType getErrorType() {
        return errorType == null ? null : ErrorType.fromId(errorType);
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType == null ? null : errorType.getId();
    }

}