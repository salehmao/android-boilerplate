package com.gamefriq.androidboilerplate.ui.main;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;
import com.gamefriq.androidboilerplate.BoilerplateApplication;
import com.gamefriq.androidboilerplate.R;
import com.gamefriq.androidboilerplate.data.DataManager;
import com.gamefriq.androidboilerplate.data.model.Ribot;
import com.gamefriq.androidboilerplate.ui.base.BasePresenter;
import com.gamefriq.androidboilerplate.util.SchedulerAppliers;

public class MainPresenter extends BasePresenter<MainMvpView> {

    @Inject protected DataManager mDataManager;
    private Subscription mSubscription;

    public MainPresenter(Context context) {
        super(context);
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
        BoilerplateApplication.get(getContext()).getComponent().inject(this);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadRibots() {
        checkViewAttached();
        mSubscription = mDataManager.getRibots()
                .compose(SchedulerAppliers.<List<Ribot>>defaultSchedulers(getContext()))
                .subscribe(new Subscriber<List<Ribot>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the ribots.");
                        String errorString = getContext().getString(R.string.error_loading_ribots);
                        getMvpView().showError(errorString);
                    }

                    @Override
                    public void onNext(List<Ribot> ribots) {
                        if (ribots.isEmpty()) {
                            getMvpView().showRibotsEmpty();
                        } else {
                            getMvpView().showRibots(ribots);
                        }
                    }
                });
    }

}
