package io.alexanderschaefer.u2764.view.opengiftdialogview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.model.viewmodel.ChallengeViewModel;
import io.alexanderschaefer.u2764.model.viewmodel.GiftViewModel;
import io.alexanderschaefer.u2764.view.DefaultEncapsulatedFragmentView;

class OpenGiftDialogViewImpl extends DefaultEncapsulatedFragmentView<OpenGiftDialogView.OpenGiftDialogViewListener> implements OpenGiftDialogView {

    private List<EditText> editTextsChallenge;
    private List<TextInputLayout> textInputLayoutsChallenge;
    private ProgressBar progressBar;

    OpenGiftDialogViewImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.dialog_open_gift, container, false));
        initialize();
    }

    private void initialize() {
        editTextsChallenge = new ArrayList<>();
        textInputLayoutsChallenge = new ArrayList<>();
        editTextsChallenge.add((EditText) findViewById(R.id.et_question1));
        editTextsChallenge.add((EditText) findViewById(R.id.et_question2));
        editTextsChallenge.add((EditText) findViewById(R.id.et_question3));
        editTextsChallenge.add((EditText) findViewById(R.id.et_question4));
        editTextsChallenge.add((EditText) findViewById(R.id.et_question5));
        textInputLayoutsChallenge.add((TextInputLayout) findViewById(R.id.til_question1));
        textInputLayoutsChallenge.add((TextInputLayout) findViewById(R.id.til_question2));
        textInputLayoutsChallenge.add((TextInputLayout) findViewById(R.id.til_question3));
        textInputLayoutsChallenge.add((TextInputLayout) findViewById(R.id.til_question4));
        textInputLayoutsChallenge.add((TextInputLayout) findViewById(R.id.til_question5));
        progressBar = findViewById(R.id.pb_progress);
    }

    @Override
    public Bundle getViewState() {
        ArrayList<String> answers = new ArrayList<>();
        for (EditText editText : editTextsChallenge) {
            answers.add(editText.getText().toString());
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(VIEW_STATE_ANSWERS, answers);
        return bundle;
    }

    @Override
    public int getOptionsMenu() {
        return R.menu.dialog_open_gift_toolbar;
    }

    @Override
    public void bind(GiftViewModel gift, boolean initial) {
        for (int i = gift.getChallenges().size() - 1; i >= 0; i--) {
            ChallengeViewModel challengeViewModel = gift.getChallenges().get(i);
            textInputLayoutsChallenge.get(i).setHint(challengeViewModel.getQuestion());
            textInputLayoutsChallenge.get(i).setVisibility(View.VISIBLE);
            if (challengeViewModel.isAnswered()) {
                if (!initial) {
                    textInputLayoutsChallenge.get(i).setErrorEnabled(false);
                }
                editTextsChallenge.get(i).setText(challengeViewModel.getGivenAnswer());
                editTextsChallenge.get(i).setEnabled(false);
            } else {
                if (!initial) {
                    textInputLayoutsChallenge.get(i).setErrorEnabled(true);
                    textInputLayoutsChallenge.get(i).setError("Falsch!");
                }
                editTextsChallenge.get(i).requestFocus();
            }
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}