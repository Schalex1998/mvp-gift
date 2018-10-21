package io.alexanderschaefer.u2764.construction.service;

import android.app.Service;

import androidx.annotation.UiThread;
import io.alexanderschaefer.u2764.construction.application.ApplicationComponent;
import io.alexanderschaefer.u2764.U2764Application;

public abstract class BaseService extends Service {

    private boolean isInjectorUsed;

    private ApplicationComponent getApplicationComponent() {
        return ((U2764Application) getApplication()).getApplicationComponent();
    }

    @UiThread
    protected ServiceComponent getServiceComponent() {
        if (isInjectorUsed)
            throw new RuntimeException("only inject once");
        isInjectorUsed = true;
        return getApplicationComponent().newServiceComponent(new ServiceModule(this));
    }
}
