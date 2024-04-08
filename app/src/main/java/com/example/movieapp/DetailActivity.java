package com.example.movieapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        TextView tvDetailTitle = findViewById(R.id.tvDetailTitle);

        String title = getIntent().getStringExtra("intent_title");
        String image = getIntent().getStringExtra("intent_image");

        Picasso.get()
                .load(image)
                .fit().centerCrop()
                .into((ImageView) findViewById(R.id.imgDetailMovie));

        tvDetailTitle.setText(title);
    }
}