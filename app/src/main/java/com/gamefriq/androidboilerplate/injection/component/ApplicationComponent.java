package com.gamefriq.androidboilerplate.injection.component;

import android.app.Application;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;
import com.gamefriq.androidboilerplate.data.DataManager;
import com.gamefriq.androidboilerplate.data.SyncService;
import com.gamefriq.androidboilerplate.injection.module.ApplicationModule;
import com.gamefriq.androidboilerplate.injection.module.DefaultSchedulersModule;
import com.gamefriq.androidboilerplate.ui.main.MainPresenter;
import com.gamefriq.androidboilerplate.util.SchedulerApplier;

@Singleton
@Component(modules = {ApplicationModule.class, DefaultSchedulersModule.class})
public interface ApplicationComponent {

    void inject(SyncService syncService);
    void inject(SchedulerApplier.DefaultSchedulers defaultSchedulers);
    void inject(MainPresenter mainPresenter);

    Application application();
    DataManager dataManager();
    Bus eventBus();
}
