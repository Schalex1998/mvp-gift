package io.alexanderschaefer.u2764.screens.common.activity;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

public interface FragmentContainerActivity extends NavigationActivity {

    void navigateTo(Fragment fragment, boolean addToBackStack, boolean clearBackStack, boolean animate);

    void setTitle(String title);

    void showActionBar(boolean show);

    @IdRes
    int getContainer();
}
