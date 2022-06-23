package com.company.softwaremaker.entity.qb;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;
import static com.company.softwaremaker.entity.qb.ErrorSeverity.FATAL;
import static com.company.softwaremaker.entity.qb.ErrorSeverity.WARNING;

public enum ErrorType implements EnumClass<String> {

    INVOICE_NOT_PRINTED("INVOICE_NOT_PRINTED", WARNING),
    INVOICE_COMPANY_NOT_FOUND("INVOICE_COMPANY_NOT_FOUND", WARNING),
    INVOICE_FILE_NOT_FOUND("INVOICE_FILE_NOT_FOUND", WARNING),
    WORKDOCS_UPLOAD_FAILED("WORKDOCS_UPLOAD_FAILED", FATAL),
    WORKDOCS_FOLDER_NOT_CONFIGURED("WORKDOCS_FOLDER_NOT_CONFIGURED", FATAL),
    QUICKBOOKS_REALM_NOT_CONFIGURED("QUICKBOOKS_REALM_NOT_CONFIGURED", FATAL),
    QUICKBOOKS_TOKEN_REFRESH_FAILED("QB_TOKEN_REFRESH_FAILED", FATAL),
    QUICKBOOKS_PUBLISH_FAILED("QUICKBOOKS_PUBLISH_FAILED", FATAL);

    private String id;
    private ErrorSeverity severity;

    ErrorType(String value, ErrorSeverity severity) {
        this.id = value;
        this.severity = severity;
    }

    public String getId() {
        return id;
    }

    public ErrorSeverity getSeverity() {
        return severity;
    }

    @Nullable
    public static ErrorType fromId(String id) {
        for (ErrorType at : ErrorType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}