package io.alexanderschaefer.u2764.presenter.activities.implementations;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.presenter.activities.DefaultFragmentContainerActivity;
import io.alexanderschaefer.u2764.presenter.fragments.implementations.OverviewFragment;

public class MainActivity extends DefaultFragmentContainerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        if (savedInstanceState == null) {
            navigateTo(new OverviewFragment(), false, false, false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager().addOnBackStackChangedListener(this::expandActionBar);
    }

    private void expandActionBar() {
        AppBarLayout appBarLayout = findViewById(R.id.abl);
        appBarLayout.setExpanded(true, false);
    }

    @Override
    public int getContainer() {
        return R.id.container;
    }

    @Override
    public void setTitle(String title) {
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.ctl);
        collapsingToolbarLayout.setTitle(title);
    }

    @Override
    public void showActionBar(boolean show) {
        AppBarLayout appBarLayout = findViewById(R.id.abl);
        if (show) {
            appBarLayout.setVisibility(View.VISIBLE);
        } else {
            appBarLayout.setVisibility(View.GONE);
        }
    }
}
