package com.gamefriq.androidboilerplate.injection.module;

import android.content.Context;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;
import com.gamefriq.androidboilerplate.injection.scope.PerActivity;
import com.gamefriq.androidboilerplate.ui.main.MainPresenter;

/**
 * This module provides instances of Presenters.
 */
@Module
public class PresentersModule {

    private WeakReference<Context> mContextWeakRef;

    public PresentersModule(Context context) {
        mContextWeakRef = new WeakReference<>(context);
    }

    @Provides
    @PerActivity
    MainPresenter providesMainPresenter() {
        return new MainPresenter(mContextWeakRef.get());
    }

}
