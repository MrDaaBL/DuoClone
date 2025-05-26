package com.example.duoclone.adapters;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duoclone.R;
import com.example.duoclone.models.QuizQuestion;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {
    private final Context context;
    private final List<QuizQuestion> questions;
    private final OnAnswerListener listener;
    private MediaPlayer correctSound;
    private MediaPlayer wrongSound;
    private int currentPosition = 0;

    public QuizAdapter(Context context, List<QuizQuestion> questions, OnAnswerListener listener) {
        this.context = context;
        this.questions = questions;
        this.listener = listener;
        initSounds();
    }

    private void initSounds() {
        correctSound = MediaPlayer.create(context, R.raw.correct_sound);
        wrongSound = MediaPlayer.create(context, R.raw.wrong_sound);
    }

    public void resetQuiz() {
        currentPosition = 0;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quiz_pager, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        holder.bind(questions.get(position));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuizViewHolder extends RecyclerView.ViewHolder {
        private final TextView questionText;
        private final Button btnOption1, btnOption2, btnNext;
        private QuizQuestion currentQuestion;
        private boolean answerSelected = false;

        QuizViewHolder(View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.tv_question);
            btnOption1 = itemView.findViewById(R.id.btn_option1);
            btnOption2 = itemView.findViewById(R.id.btn_option2);
            btnNext = itemView.findViewById(R.id.btn_next);

            btnNext.setOnClickListener(v -> {
                if (listener != null) {
                    listener.moveToNextQuestion();
                }
            });
        }

        void bind(QuizQuestion question) {
            currentQuestion = question;
            questionText.setText(question.getQuestionText());
            btnOption1.setText(question.getOption1());
            btnOption2.setText(question.getOption2());

            resetButtons();
            btnNext.setVisibility(View.GONE);
            answerSelected = false;

            btnOption1.setOnClickListener(v -> handleAnswer(btnOption1));
            btnOption2.setOnClickListener(v -> handleAnswer(btnOption2));
        }

        private void handleAnswer(Button selectedButton) {
            if (answerSelected) return;

            boolean isCorrect = selectedButton == btnOption1 &&
                    currentQuestion.getCorrectOptionIndex() == 0;

            selectedButton.setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);
            playSound(isCorrect);

            if (listener != null) {
                listener.onAnswerSelected(isCorrect);
            }

            answerSelected = true;
            btnNext.setVisibility(View.VISIBLE);
        }

        private void resetButtons() {
            int defaultColor = Color.parseColor("#6200EE");
            btnOption1.setBackgroundColor(defaultColor);
            btnOption2.setBackgroundColor(defaultColor);
        }

        private void playSound(boolean isCorrect) {
            try {
                MediaPlayer sound = isCorrect ? correctSound : wrongSound;
                if (sound != null) {
                    sound.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnAnswerListener {
        void onAnswerSelected(boolean isCorrect);
        void moveToNextQuestion();
    }
}