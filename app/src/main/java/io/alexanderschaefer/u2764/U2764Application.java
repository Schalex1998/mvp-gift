package io.alexanderschaefer.u2764;

import android.app.Application;

import io.alexanderschaefer.u2764.construction.application.ApplicationComponent;
import io.alexanderschaefer.u2764.construction.application.ApplicationModule;
import io.alexanderschaefer.u2764.construction.application.DaggerApplicationComponent;

public class U2764Application extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
