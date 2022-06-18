package com.company.softwaremaker.core.qb;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import com.haulmont.cuba.core.sys.jmx.JmxBean;

@JmxBean(module = "softwaremaker", alias = "quickBooksClientManager")
@ManagedResource(description = "JMX bean for some settings")
public interface QuickBooksClientManagerMBean {


    @ManagedOperation(description = "Refreshes QuickBooks client")
    String refreshQuickBooksClient();


}