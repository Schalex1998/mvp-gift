package io.alexanderschaefer.u2764.presenter.activities.implementations;

import android.os.Bundle;

import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.presenter.activities.DefaultNavigationActivity;

public class SplashActivity extends DefaultNavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        navigateTo(MainActivity.class, true);
    }
}
