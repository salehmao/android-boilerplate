package com.gamefriq.androidboilerplate.ui.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.gamefriq.androidboilerplate.R;
import com.gamefriq.androidboilerplate.data.SyncService;
import com.gamefriq.androidboilerplate.data.model.Ribot;
import com.gamefriq.androidboilerplate.ui.base.BaseActivity;
import com.gamefriq.androidboilerplate.util.DialogFactory;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject MainPresenter mMainPresenter;
    private RibotsAdapter mRibotsAdapter;

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        startService(SyncService.getStartIntent(this));
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRibotsAdapter = new RibotsAdapter();
        mRecyclerView.setAdapter(mRibotsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMainPresenter.attachView(this);
        mMainPresenter.loadRibots();
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showRibots(List<Ribot> ribots) {
        mRibotsAdapter.setRibots(ribots);
        mRibotsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errorMessage) {
        DialogFactory.createGenericErrorDialog(this, errorMessage).show();
    }

    @Override
    public void showRibotsEmpty() {
        mRibotsAdapter.setRibots(Collections.<Ribot>emptyList());
        mRibotsAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_ribots, Toast.LENGTH_LONG).show();
    }

}
