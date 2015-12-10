package com.gamefriq.androidboilerplate.test.common.injection.component;

import dagger.Component;
import com.gamefriq.androidboilerplate.injection.component.DataManagerComponent;
import com.gamefriq.androidboilerplate.injection.scope.PerDataManager;
import com.gamefriq.androidboilerplate.test.common.injection.module.DataManagerTestModule;

@PerDataManager
@Component(dependencies = TestComponent.class, modules = DataManagerTestModule.class)
public interface DataManagerTestComponent extends DataManagerComponent {
}
