package com.gamefriq.androidboilerplate.data;

import android.content.Context;

import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import com.gamefriq.androidboilerplate.BoilerplateApplication;
import com.gamefriq.androidboilerplate.data.local.DatabaseHelper;
import com.gamefriq.androidboilerplate.data.local.PreferencesHelper;
import com.gamefriq.androidboilerplate.data.model.Ribot;
import com.gamefriq.androidboilerplate.data.remote.RibotsService;
import com.gamefriq.androidboilerplate.injection.component.DaggerDataManagerComponent;
import com.gamefriq.androidboilerplate.injection.module.DataManagerModule;

public class DataManager {

    @Inject protected RibotsService mRibotsService;
    @Inject protected DatabaseHelper mDatabaseHelper;
    @Inject protected PreferencesHelper mPreferencesHelper;
    @Inject protected Bus mBus;

    public DataManager(Context context) {
        injectDependencies(context);
    }

    protected void injectDependencies(Context context) {
        DaggerDataManagerComponent.builder()
                .applicationComponent(BoilerplateApplication.get(context).getComponent())
                .dataManagerModule(new DataManagerModule(context))
                .build()
                .inject(this);
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Ribot> syncRibots() {
        return mRibotsService.getRibots()
                .concatMap(new Func1<List<Ribot>, Observable<Ribot>>() {
                    @Override
                    public Observable<Ribot> call(List<Ribot> ribots) {
                        return mDatabaseHelper.setRibots(ribots);
                    }
                });
    }

    public Observable<List<Ribot>> getRibots() {
        return mDatabaseHelper.getRibots().distinct();
    }

}
