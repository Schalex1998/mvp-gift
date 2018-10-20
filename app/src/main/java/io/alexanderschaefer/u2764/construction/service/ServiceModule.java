package io.alexanderschaefer.u2764.construction.service;

import android.app.Service;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    private final Service service;

    ServiceModule(Service service) {
        this.service = service;
    }

    @Provides
    Context provideContext() {
        return service;
    }
}
