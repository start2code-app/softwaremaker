package com.company.softwaremaker.entity.qb;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "SOFTWAREMAKER_QUICK_BOOKS_CSRF")
@Entity(name = "softwaremaker_QuickBooksCsrf")
public class QuickBooksCsrf extends StandardEntity {
    private static final long serialVersionUID = -8915598060002289852L;

    @Column(name = "CSRF", nullable = false)
    @NotNull
    private String csrf;

    public String getCsrf() {
        return csrf;
    }

    public void setCsrf(String csrf) {
        this.csrf = csrf;
    }
}