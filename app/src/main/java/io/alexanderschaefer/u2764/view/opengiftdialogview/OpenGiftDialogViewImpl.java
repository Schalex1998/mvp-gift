package io.alexanderschaefer.u2764.view.opengiftdialogview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.alexanderschaefer.u2764.R;
import io.alexanderschaefer.u2764.view.DefaultEncapsulatedFragmentView;
import io.alexanderschaefer.u2764.view.formatter.FormattedChallenge;
import io.alexanderschaefer.u2764.view.formatter.FormattedGift;

public class OpenGiftDialogViewImpl extends DefaultEncapsulatedFragmentView<OpenGiftDialogView.OpenGiftDialogViewListener> implements OpenGiftDialogView {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ChallengeAdapter challengeAdapter;

    public OpenGiftDialogViewImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.dialog_open_gift, container, false));
        initialize();

        challengeAdapter = new ChallengeAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(challengeAdapter);
    }

    private void initialize() {
        recyclerView = findViewById(R.id.rv);
        progressBar = findViewById(R.id.pb_progress);
    }

    @Override
    public Bundle getViewState() {
        ArrayList<String> answers = new ArrayList<>();
        for (int i = 0; i < challengeAdapter.getItemCount(); i++) {
            answers.add(((ChallengeAdapter.ChallengeViewHolder) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(i))).getAnswer());
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
        challengeAdapter.setChallenges(gift.getChallenges(), initial);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder> {

        private List<FormattedChallenge> challenges;
        private boolean inital = true;

        void setChallenges(List<FormattedChallenge> challenges, boolean inital) {
            this.challenges = challenges;
            this.inital = inital;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_challenge, parent, false);
            return new ChallengeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChallengeViewHolder holder, int position) {
            holder.bind(challenges.get(position), inital);
        }

        @Override
        public int getItemCount() {
            return challenges == null ? 0 : challenges.size();
        }

        class ChallengeViewHolder extends RecyclerView.ViewHolder {

            private TextInputLayout textInputLayout;
            private TextInputEditText textInputEditText;

            ChallengeViewHolder(@NonNull View itemView) {
                super(itemView);
                textInputEditText = itemView.findViewById(R.id.et_question);
                textInputLayout = itemView.findViewById(R.id.til_question);
            }

            void bind(FormattedChallenge formattedChallenge, boolean initial) {
                textInputLayout.setHint(formattedChallenge.getQuestion());
                if (formattedChallenge.isAnswered()) {
                    if (!initial) {
                        textInputLayout.setErrorEnabled(false);
                    }
                    textInputEditText.setText(formattedChallenge.getGivenAnswer());
                    textInputEditText.setEnabled(false);
                } else {
                    if (!initial) {
                        textInputLayout.setErrorEnabled(true);
                        textInputLayout.setError(getContext().getString(R.string.challenge_wrong_error));
                    }
                }
            }

            String getAnswer() {
                return Objects.requireNonNull(textInputEditText.getText()).toString();
            }
        }
    }
}
