package com.example.finalproject.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.models.NewsArticle;
import com.example.finalproject.utils.TimeUtils;

public class ArticleDetailActivity extends AppCompatActivity {
    private NewsArticle article;
    private ImageView articleImage;
    private TextView articleTitle;
    private TextView articleAuthor;
    private TextView articleCategory;
    private TextView articleTime;
    private TextView articleLikes;
    private TextView articleContent;
    private TextView sourceLogo;
    private ImageView likeButton;
    private ImageView bookmarkButton;
    private TextView likeCount;
    private TextView commentCount;
    private boolean isLiked = false;
    private boolean isBookmarked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        article = (NewsArticle) getIntent().getSerializableExtra("article");
        if (article == null) {
            finish();
            return;
        }

        initializeViews();
        setupClickListeners();
        displayArticle();
    }

    private void initializeViews() {
        articleImage = findViewById(R.id.articleImage);
        articleTitle = findViewById(R.id.articleTitle);
        articleAuthor = findViewById(R.id.articleAuthor);
        articleCategory = findViewById(R.id.articleCategory);
        articleTime = findViewById(R.id.articleTime);
        articleLikes = findViewById(R.id.articleLikes);
        articleContent = findViewById(R.id.articleContent);
        sourceLogo = findViewById(R.id.sourceLogo);
        likeButton = findViewById(R.id.likeButton);
        bookmarkButton = findViewById(R.id.bookmarkButton);
        likeCount = findViewById(R.id.likeCount);
        commentCount = findViewById(R.id.commentCount);
    }

    private void setupClickListeners() {
        ImageView backButton = findViewById(R.id.backButton);
        ImageView shareButton = findViewById(R.id.shareButton);

        backButton.setOnClickListener(v -> finish());

        likeButton.setOnClickListener(v -> {
            isLiked = !isLiked;
            updateLikeButton();
        });

        bookmarkButton.setOnClickListener(v -> {
            isBookmarked = !isBookmarked;
            updateBookmarkButton();
        });

        shareButton.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, article.getTitle());
            shareIntent.putExtra(Intent.EXTRA_TEXT, article.getUrl());
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
    }

    private void displayArticle() {
        // Set source name in appbar
        String sourceName = article.getSourceName();
        if (sourceName != null && !sourceName.isEmpty()) {
            sourceLogo.setText(sourceName);
        } else {
            sourceLogo.setText("News");
        }
        
        articleTitle.setText(article.getTitle());
        String authorText = article.getAuthor() != null ? article.getAuthor() : "Unknown";
        articleAuthor.setText("By " + authorText + " For " + article.getSourceName());
        articleCategory.setText(article.getCategory() != null ? article.getCategory() : "General");
        
        if (article.getPublishedAt() != null) {
            articleTime.setText(TimeUtils.getTimeAgo(article.getPublishedAt()));
        }
        
        articleLikes.setText(article.getLikes() + "k liked");
        likeCount.setText(String.valueOf(article.getLikes()));
        commentCount.setText(String.valueOf(article.getComments()));
        
        // Show full article content - use description as it contains the summary
        String content = "";
        if (article.getDescription() != null && !article.getDescription().isEmpty()) {
            content = article.getDescription();
            // If description is short, add more context
            if (content.length() < 200) {
                content += "\n\n" + article.getTitle() + " - " + article.getSourceName() + 
                          " brings you the latest updates on this developing story.";
            }
        } else if (article.getTitle() != null) {
            content = article.getTitle() + "\n\nThis article is available from " + 
                     article.getSourceName() + ". Click 'Read Full Article' to view the complete story.";
        } else {
            content = "Article content is available. Click 'Read Full Article' to view the complete story from " + 
                     article.getSourceName() + ".";
        }
        
        articleContent.setText(content);

        if (article.getUrlToImage() != null && !article.getUrlToImage().isEmpty()) {
            Glide.with(this)
                    .load(article.getUrlToImage())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(articleImage);
        }
    }

    private void updateLikeButton() {
        if (isLiked) {
            likeButton.setImageResource(android.R.drawable.btn_star_big_on);
            int currentLikes = article.getLikes();
            article.setLikes(currentLikes + 1);
            likeCount.setText(String.valueOf(article.getLikes()));
        } else {
            likeButton.setImageResource(android.R.drawable.btn_star_big_off);
            int currentLikes = article.getLikes();
            if (currentLikes > 0) {
                article.setLikes(currentLikes - 1);
            }
            likeCount.setText(String.valueOf(article.getLikes()));
        }
    }

    private void updateBookmarkButton() {
        article.setBookmarked(isBookmarked);
        if (isBookmarked) {
            bookmarkButton.setImageResource(android.R.drawable.star_big_on);
        } else {
            bookmarkButton.setImageResource(android.R.drawable.star_big_off);
        }
    }
}

