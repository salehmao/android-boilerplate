package com.gamefriq.androidboilerplate.test.common.injection.module;

import android.app.Application;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.gamefriq.androidboilerplate.data.DataManager;
import com.gamefriq.androidboilerplate.test.common.TestDataManager;

import static org.mockito.Mockito.spy;

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
public class ApplicationTestModule {
    private final Application mApplication;
    private boolean mMockableDataManager;

    public ApplicationTestModule(Application application, boolean mockableDataManager) {
        mApplication = application;
        mMockableDataManager = mockableDataManager;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    DataManager provideDataManager() {
        TestDataManager testDataManager = new TestDataManager(mApplication);
        return mMockableDataManager ? spy(testDataManager) : testDataManager;
    }

    @Provides
    @Singleton
    Bus provideEventBus() {
        return new Bus();
    }
}
