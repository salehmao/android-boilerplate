package com.gamefriq.androidboilerplate.ui.main;

import java.util.List;

import com.gamefriq.androidboilerplate.data.model.Ribot;
import com.gamefriq.androidboilerplate.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showRibots(List<Ribot> ribots);

    void showRibotsEmpty();

    void showError(String errorMessage);

}
