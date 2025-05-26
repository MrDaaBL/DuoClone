package com.example.duoclone.adapters;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duoclone.R;
import com.example.duoclone.models.ImageQuestion;
import java.util.List;

public class ImageQuizAdapter extends RecyclerView.Adapter<ImageQuizAdapter.ImageQuizViewHolder> {
    private final List<ImageQuestion> questions;
    private final OnAnswerListener listener;
    private final MediaPlayer correctSound;
    private final MediaPlayer wrongSound;

    public ImageQuizAdapter(List<ImageQuestion> questions, OnAnswerListener listener,
                            MediaPlayer correctSound, MediaPlayer wrongSound) {
        this.questions = questions;
        this.listener = listener;
        this.correctSound = correctSound;
        this.wrongSound = wrongSound;
    }

    @NonNull
    @Override
    public ImageQuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_quiz, parent, false);
        return new ImageQuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageQuizViewHolder holder, int position) {
        holder.bind(questions.get(position));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class ImageQuizViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final Button btnOption1, btnOption2;

        ImageQuizViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.quiz_image);
            btnOption1 = itemView.findViewById(R.id.btn_option1);
            btnOption2 = itemView.findViewById(R.id.btn_option2);
        }

        void bind(ImageQuestion question) {
            imageView.setImageResource(question.getImageResId());
            btnOption1.setText(question.getOption1());
            btnOption2.setText(question.getOption2());

            View.OnClickListener answerListener = v -> {
                Button clickedBtn = (Button) v;
                boolean isCorrect = clickedBtn.getText().equals(question.getCorrectAnswer());

                clickedBtn.setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);
                if (isCorrect) {
                    correctSound.start();
                } else {
                    wrongSound.start();
                }

                if (listener != null) {
                    listener.onAnswer(isCorrect, question.getXpReward());
                }
            };

            btnOption1.setOnClickListener(answerListener);
            btnOption2.setOnClickListener(answerListener);
        }
    }

    public interface OnAnswerListener {
        void onAnswer(boolean isCorrect, int xpEarned);
    }
}