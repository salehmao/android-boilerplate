package com.gamefriq.androidboilerplate.injection.component;

import dagger.Component;
import com.gamefriq.androidboilerplate.injection.module.PresentersModule;
import com.gamefriq.androidboilerplate.injection.scope.PerActivity;
import com.gamefriq.androidboilerplate.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = PresentersModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
