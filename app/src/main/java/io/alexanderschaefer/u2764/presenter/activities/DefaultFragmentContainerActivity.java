package io.alexanderschaefer.u2764.presenter.activities;

import android.content.Intent;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public abstract class DefaultFragmentContainerActivity extends DefaultNavigationActivity implements FragmentContainerActivity {

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackStack, boolean clearBackStack, boolean animate) {
        if (clearBackStack)
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (animate) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(getContainer(), fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateHomeButton();
        getSupportFragmentManager().addOnBackStackChangedListener(this::updateHomeButton);
    }

    private void updateHomeButton() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(String title) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }

    @Override
    public void showActionBar(boolean show) {
        if (getSupportActionBar() != null) {
            if (show)
                getSupportActionBar().show();
            else
                getSupportActionBar().hide();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment currFragment = getSupportFragmentManager().findFragmentById(getContainer());
        if (currFragment != null)
            currFragment.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }
}
