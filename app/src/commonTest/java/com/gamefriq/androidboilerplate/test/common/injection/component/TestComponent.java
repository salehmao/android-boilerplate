package com.gamefriq.androidboilerplate.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import com.gamefriq.androidboilerplate.injection.component.ApplicationComponent;
import com.gamefriq.androidboilerplate.test.common.injection.module.ApplicationTestModule;
import com.gamefriq.androidboilerplate.test.common.injection.module.DefaultSchedulersTestModule;

@Singleton
@Component(modules = {ApplicationTestModule.class, DefaultSchedulersTestModule.class})
public interface TestComponent extends ApplicationComponent {

}
