package io.alexanderschaefer.u2764.screens.common.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.alexanderschaefer.u2764.construction.presentation.BaseFragment;
import io.alexanderschaefer.u2764.screens.common.activity.FragmentContainerActivity;
import io.alexanderschaefer.u2764.screens.common.view.EncapsulatedFragmentView;

public abstract class DefaultFragment extends BaseFragment {

    private FragmentContainerActivity fragmentContainerActivity;

    @Nullable
    public abstract EncapsulatedFragmentView getEncapsulatedView();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            fragmentContainerActivity = (FragmentContainerActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentContainerActivity");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        invalidateActionBar();
    }

    protected void invalidateActionBar() {
        if (getEncapsulatedView() != null) {
            fragmentContainerActivity.setTitle(getEncapsulatedView().getTitle());
            fragmentContainerActivity.showActionBar(getEncapsulatedView().shouldShowActionBar());
            setHasOptionsMenu(getEncapsulatedView().getOptionsMenu() != 0);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(Objects.requireNonNull(getEncapsulatedView()).getOptionsMenu(), menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Bundle viewState = Objects.requireNonNull(getEncapsulatedView()).getViewState();
        if (viewState == null)
            viewState = outState;
        super.onSaveInstanceState(viewState);
    }

    protected void navigateTo(Fragment fragment, boolean addToBackStack, boolean clearBackStack, boolean animate) {
        fragmentContainerActivity.navigateTo(fragment, addToBackStack, clearBackStack, animate);
    }

    protected void navigateTo(Class<? extends Activity> clazz, boolean clearBackStack) {
        fragmentContainerActivity.navigateTo(clazz, clearBackStack);
    }
}
