package com.example.duoclone.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duoclone.R;
import com.example.duoclone.models.QuizQuestion;
import com.example.duoclone.utils.SoundManager;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {
    private List<QuizQuestion> questions;
    private OnAnswerListener listener;
    private SoundManager soundManager;

    public QuizAdapter(List<QuizQuestion> questions, OnAnswerListener listener, SoundManager soundManager) {
        this.questions = questions;
        this.listener = listener;
        this.soundManager = soundManager;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quiz, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        QuizQuestion question = questions.get(position);
        holder.bind(question);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuizViewHolder extends RecyclerView.ViewHolder {
        private Button btnOption1, btnOption2;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            btnOption1 = itemView.findViewById(R.id.btn_option1);
            btnOption2 = itemView.findViewById(R.id.btn_option2);
        }

        public void bind(QuizQuestion question) {
            btnOption1.setText(question.getOption1());
            btnOption2.setText(question.getOption2());

            View.OnClickListener answerListener = v -> {
                Button clickedBtn = (Button) v;
                int selectedIndex = clickedBtn == btnOption1 ? 0 : 1;
                boolean isCorrect = question.isCorrect(selectedIndex);

                clickedBtn.setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);
                if (isCorrect) {
                    soundManager.playCorrectSound();
                } else {
                    soundManager.playWrongSound();
                }

                if (listener != null) {
                    listener.onAnswerSelected(isCorrect);
                }
            };

            btnOption1.setOnClickListener(answerListener);
            btnOption2.setOnClickListener(answerListener);
        }
    }

    public interface OnAnswerListener {
        void onAnswerSelected(boolean isCorrect);
    }
}