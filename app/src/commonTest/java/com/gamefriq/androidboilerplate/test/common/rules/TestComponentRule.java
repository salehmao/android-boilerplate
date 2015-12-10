package com.gamefriq.androidboilerplate.test.common.rules;

import android.content.Context;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.gamefriq.androidboilerplate.BoilerplateApplication;
import com.gamefriq.androidboilerplate.data.local.DatabaseHelper;
import com.gamefriq.androidboilerplate.data.local.PreferencesHelper;
import com.gamefriq.androidboilerplate.data.remote.RibotsService;
import com.gamefriq.androidboilerplate.test.common.TestDataManager;
import com.gamefriq.androidboilerplate.test.common.injection.component.DaggerTestComponent;
import com.gamefriq.androidboilerplate.test.common.injection.component.TestComponent;
import com.gamefriq.androidboilerplate.test.common.injection.module.ApplicationTestModule;

/**
 * Test rule that creates and sets a Dagger TestComponent into the application overriding the
 * existing application component.
 * Use this rule in your test case in order for the app to use mock dependencies.
 * It also exposes some of the dependencies so they can be easily accessed from the tests, e.g. to
 * stub mocks etc.
 */
public class TestComponentRule implements TestRule {

    private TestComponent mTestComponent;
    private Context mContext;
    private boolean mMockableDataManager;

    /**
     * If mockableDataManager is true, it will crate a data manager using Mockito.spy()
     * Spy objects call real methods unless they are stubbed. So the DataManager will work as
     * usual unless an specific method is mocked.
     * A full mock DataManager is not an option because there are several methods that still
     * need to return the real value, i.e dataManager.getSubscribeScheduler()
     */
    public TestComponentRule(Context context, boolean mockableDataManager) {
        init(context, mockableDataManager);
    }

    public TestComponentRule(Context context) {
        init(context, false);
    }

    private void init(Context context, boolean mockableDataManager) {
        mContext = context;
        mMockableDataManager = mockableDataManager;
    }

    public TestComponent getTestComponent() {
        return mTestComponent;
    }

    public Context getContext() {
        return mContext;
    }

    public TestDataManager getDataManager() {
        return (TestDataManager) mTestComponent.dataManager();
    }

    public RibotsService getMockRibotsService() {
        return getDataManager().getRibotsService();
    }

    public DatabaseHelper getDatabaseHelper() {
        return getDataManager().getDatabaseHelper();
    }

    public PreferencesHelper getPreferencesHelper() {
        return getDataManager().getPreferencesHelper();
    }

    private void setupDaggerTestComponentInApplication() {
        BoilerplateApplication application = BoilerplateApplication.get(mContext);
        ApplicationTestModule module = new ApplicationTestModule(application,
                mMockableDataManager);
        mTestComponent = DaggerTestComponent.builder()
                .applicationTestModule(module)
                .build();
        application.setComponent(mTestComponent);
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    setupDaggerTestComponentInApplication();
                    base.evaluate();
                } finally {
                    mTestComponent = null;
                }
            }
        };
    }
}
