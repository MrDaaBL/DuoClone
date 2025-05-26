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
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {
    private final List<QuizQuestion> questions;
    private final OnAnswerListener listener;

    public QuizAdapter(List<QuizQuestion> questions, OnAnswerListener listener) {
        this.questions = questions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quiz, parent, false);
        return new QuizViewHolder(view, listener); // Передаем listener в ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        holder.bind(questions.get(position));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class QuizViewHolder extends RecyclerView.ViewHolder {
        private final Button btnOption1;
        private final Button btnOption2;
        private final OnAnswerListener listener;

        // Конструктор теперь принимает listener
        QuizViewHolder(View itemView, OnAnswerListener listener) {
            super(itemView);
            this.listener = listener;
            btnOption1 = itemView.findViewById(R.id.btn_option1);
            btnOption2 = itemView.findViewById(R.id.btn_option2);
        }

        void bind(QuizQuestion question) {
            btnOption1.setText(question.getOptions().get(0));
            btnOption2.setText(question.getOptions().get(1));

            View.OnClickListener clickListener = v -> {
                Button clickedBtn = (Button) v;
                boolean isCorrect = (clickedBtn == btnOption1 && question.getCorrectOptionIndex() == 0) ||
                        (clickedBtn == btnOption2 && question.getCorrectOptionIndex() == 1);

                // Визуальная обратная связь
                clickedBtn.setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);

                // Вызываем callback через listener
                if (listener != null) {
                    listener.onAnswer(isCorrect, question.getXpReward());
                }
            };

            btnOption1.setOnClickListener(clickListener);
            btnOption2.setOnClickListener(clickListener);
        }
    }

    public interface OnAnswerListener {
        void onAnswer(boolean isCorrect, int xpEarned);
    }
}