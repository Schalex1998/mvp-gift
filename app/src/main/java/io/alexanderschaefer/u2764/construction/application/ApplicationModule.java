package io.alexanderschaefer.u2764.construction.application;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.alexanderschaefer.u2764.common.AppExecutors;
import io.alexanderschaefer.u2764.model.formatter.FormattedGiftFactory;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
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
    AppExecutors provideAppExecutors(){
        return new AppExecutors();
    }
}
