package io.alexanderschaefer.u2764.construction.application;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import io.alexanderschaefer.u2764.view.formatter.FormattedGiftFactory;

@Module
class ApplicationModule {

    private final Application application;

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
}
