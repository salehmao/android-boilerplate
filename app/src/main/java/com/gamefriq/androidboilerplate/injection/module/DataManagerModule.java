package com.gamefriq.androidboilerplate.injection.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import com.gamefriq.androidboilerplate.data.local.DatabaseHelper;
import com.gamefriq.androidboilerplate.data.local.PreferencesHelper;
import com.gamefriq.androidboilerplate.data.remote.RibotsService;
import com.gamefriq.androidboilerplate.injection.scope.PerDataManager;

/**
 * Provide dependencies to the DataManager, mainly Helper classes and Retrofit services.
 */
@Module
public class DataManagerModule {

    private final Context mContext;

    public DataManagerModule(Context context) {
        mContext = context;
    }

    @Provides
    @PerDataManager
    DatabaseHelper provideDatabaseHelper() {
        return new DatabaseHelper(mContext);
    }

    @Provides
    @PerDataManager
    PreferencesHelper providePreferencesHelper() {
        return new PreferencesHelper(mContext);
    }

    @Provides
    @PerDataManager
    RibotsService provideRibotsService() {
        return RibotsService.Creator.newRibotsService();
    }
}
