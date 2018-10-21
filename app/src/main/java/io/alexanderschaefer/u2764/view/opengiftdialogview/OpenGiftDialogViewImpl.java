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
import io.alexanderschaefer.u2764.view.DefaultEncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.formatter.FormattedChallenge;
import io.alexanderschaefer.u2764.view.formatter.FormattedGift;

public class OpenGiftDialogViewImpl extends DefaultEncapsulatedFragmentView<OpenGiftDialogView.OpenGiftDialogViewListener> implements OpenGiftDialogView {

    private List<EditText> editTextsChallenge;
    private List<TextInputLayout> textInputLayoutsChallenge;
    private ProgressBar progressBar;

    public OpenGiftDialogViewImpl(LayoutInflater inflater, ViewGroup container) {
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
    public void bind(FormattedGift gift, boolean initial) {
        for (int i = gift.getChallenges().size() - 1; i >= 0; i--) {
            FormattedChallenge formattedChallenge = gift.getChallenges().get(i);
            textInputLayoutsChallenge.get(i).setHint(formattedChallenge.getQuestion());
            textInputLayoutsChallenge.get(i).setVisibility(View.VISIBLE);
            if (formattedChallenge.isAnswered()) {
                if (!initial) {
                    textInputLayoutsChallenge.get(i).setErrorEnabled(false);
                }
                editTextsChallenge.get(i).setText(formattedChallenge.getGivenAnswer());
                editTextsChallenge.get(i).setEnabled(false);
            } else {
                if (!initial) {
                    textInputLayoutsChallenge.get(i).setErrorEnabled(true);
                    textInputLayoutsChallenge.get(i).setError(getContext().getString(R.string.challenge_wrong));
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
