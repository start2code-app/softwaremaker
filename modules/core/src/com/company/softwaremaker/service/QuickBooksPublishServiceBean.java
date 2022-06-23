package com.company.softwaremaker.service;

import com.company.softwaremaker.entity.qb.PublishError;
import com.company.softwaremaker.exception.QuickBooksException;
import com.company.softwaremaker.exception.QuickBooksTokenRefreshException;
import com.company.softwaremaker.service.qb.QuickBooksConnectorService;
import com.haulmont.cuba.core.global.FileStorageException;
import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.util.List;

import static com.company.softwaremaker.entity.qb.ErrorType.INVOICE_FILE_NOT_FOUND;
import static com.company.softwaremaker.entity.qb.ErrorType.INVOICE_COMPANY_NOT_FOUND;
import static com.company.softwaremaker.entity.qb.ErrorType.INVOICE_NOT_PRINTED;
import static com.company.softwaremaker.entity.qb.ErrorType.QUICKBOOKS_PUBLISH_FAILED;
import static com.company.softwaremaker.entity.qb.ErrorType.QUICKBOOKS_REALM_NOT_CONFIGURED;
import static com.company.softwaremaker.entity.qb.ErrorType.QUICKBOOKS_TOKEN_REFRESH_FAILED;


@Service(QuickBooksPublishService.NAME)
public class QuickBooksPublishServiceBean implements QuickBooksPublishService {


    private static final String BROKERAGE_ITEM_NAME = "Brokerage";
    private static final String TAX_CODE_NAME = "Exempt";
    private static final String TERM_NAME = "30 days";


    @Inject
    private Logger log;
    @Inject
    private QuickBooksConnectorService quickBooksConnectorService;

    @Override
    public PublishError publishToQB() {

        try {
            DataService ds = quickBooksConnectorService.getDataService("193514293163217");
            //publish(invoice, ds);
            getBrokerage(ds);
        } catch (QuickBooksTokenRefreshException e) {
            log.warn("QuickBooks needs reauthorize", e);
            return PublishError.of(QUICKBOOKS_TOKEN_REFRESH_FAILED);

        } catch (Exception e) {
            log.error("QuickBooks upload failed", e);
            return PublishError.of(QUICKBOOKS_PUBLISH_FAILED);
        }

        return null;
    }

    private ReferenceType getBrokerage(DataService ds) throws FMSException {
        QueryResult result = ds.executeQuery(
                String.format("select * from item where name = '%s'", BROKERAGE_ITEM_NAME));
        List<? extends IEntity> entities = result.getEntities();
        if (entities.isEmpty()) {
            throw new QuickBooksException("Item with name '" + BROKERAGE_ITEM_NAME + "' was not found");
        }
        Item item = (Item) entities.get(0);
        ReferenceType itemRef = new ReferenceType();
        itemRef.setName(item.getName());
        itemRef.setValue(item.getId());
        return itemRef;
    }
}