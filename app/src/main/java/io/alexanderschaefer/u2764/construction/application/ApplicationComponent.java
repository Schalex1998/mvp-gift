package io.alexanderschaefer.u2764.construction.application;

import javax.inject.Singleton;

import dagger.Component;
import io.alexanderschaefer.u2764.construction.presentation.PresentationComponent;
import io.alexanderschaefer.u2764.construction.presentation.PresentationModule;
import io.alexanderschaefer.u2764.construction.service.ServiceComponent;
import io.alexanderschaefer.u2764.construction.service.ServiceModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DataModule.class
})
public interface ApplicationComponent {
    PresentationComponent newPresentationComponent(PresentationModule presentationModule);

    ServiceComponent newServiceComponent(ServiceModule serviceModule);
}
