package io.alexanderschaefer.u2764.construction.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.alexanderschaefer.u2764.common.AppExecutors;
import io.alexanderschaefer.u2764.model.formatter.FormattedGiftFactory;

@Module
public class ApplicationModule {

    private final Application application;
    private static final String DEFAULT_SHARED_PREF = "default_shared_pref";

    ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    FormattedGiftFactory provideFormattedGiftFactory(Application application) {
        return new FormattedGiftFactory(application);
    }

    @Singleton
    @Provides
    AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }

    @Provides
    SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(DEFAULT_SHARED_PREF, Context.MODE_PRIVATE);
    }
}
