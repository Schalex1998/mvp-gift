package io.alexanderschaefer.u2764.construction.presentation;

import java.util.Objects;

import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import io.alexanderschaefer.u2764.construction.application.ApplicationComponent;
import io.alexanderschaefer.u2764.U2764Application;

public abstract class BaseFragment extends Fragment {

    private boolean isInjectorUsed;

    private ApplicationComponent getApplicationComponent() {
        return ((U2764Application) Objects.requireNonNull(getActivity()).getApplication()).getApplicationComponent();
    }

    @UiThread
    protected PresentationComponent getPresentationComponent() {
        if (isInjectorUsed)
            throw new RuntimeException("only inject once");
        isInjectorUsed = true;
        return getApplicationComponent().newPresentationComponent(new PresentationModule(getActivity()));
    }
}
