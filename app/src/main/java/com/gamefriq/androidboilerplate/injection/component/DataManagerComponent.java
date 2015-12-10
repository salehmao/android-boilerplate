package com.gamefriq.androidboilerplate.injection.component;

import dagger.Component;
import com.gamefriq.androidboilerplate.data.DataManager;
import com.gamefriq.androidboilerplate.injection.module.DataManagerModule;
import com.gamefriq.androidboilerplate.injection.scope.PerDataManager;

@PerDataManager
@Component(dependencies = ApplicationComponent.class, modules = DataManagerModule.class)
public interface DataManagerComponent {

    void inject(DataManager dataManager);
}
