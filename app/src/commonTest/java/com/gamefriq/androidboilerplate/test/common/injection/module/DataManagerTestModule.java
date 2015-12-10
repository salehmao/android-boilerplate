package com.gamefriq.androidboilerplate.test.common.injection.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import com.gamefriq.androidboilerplate.data.local.DatabaseHelper;
import com.gamefriq.androidboilerplate.data.local.PreferencesHelper;
import com.gamefriq.androidboilerplate.data.remote.RibotsService;
import com.gamefriq.androidboilerplate.injection.scope.PerDataManager;

import static org.mockito.Mockito.mock;

/**
 * Provides dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary
 */
@Module
public class DataManagerTestModule {

    private final Context mContext;

    public DataManagerTestModule(Context context) {
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
        return mock(RibotsService.class);
    }
}
