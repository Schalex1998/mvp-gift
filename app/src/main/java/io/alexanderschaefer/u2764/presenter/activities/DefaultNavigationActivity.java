package io.alexanderschaefer.u2764.presenter.activities;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public abstract class DefaultNavigationActivity extends AppCompatActivity implements NavigationActivity {

    @Override
    public void navigateTo(Class<? extends Activity> clazz, boolean clearBackStack) {
        Intent intent = new Intent(this, clazz);
        if (clearBackStack)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        if (clearBackStack)
            finish();
    }
}
