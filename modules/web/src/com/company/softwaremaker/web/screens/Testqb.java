package com.company.softwaremaker.web.screens;

import com.company.softwaremaker.entity.qb.PublishError;
import com.company.softwaremaker.service.QuickBooksPublishService;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.executors.BackgroundTask;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.util.Collection;

@UiController("softwaremaker_Testqb")
@UiDescriptor("TestQb.xml")
public class Testqb extends Screen {
    @Inject
    private QuickBooksPublishService quickBooksPublishService;

    @Subscribe
    public void onAfterInit(AfterInitEvent event) {

        quickBooksPublishService.publishToQB();

    }





}