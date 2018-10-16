package io.alexanderschaefer.u2764.presenter.activities;

import android.app.Activity;

public interface NavigationActivity {

    void navigateTo(Class<? extends Activity> clazz, boolean clearBackStack);
}
