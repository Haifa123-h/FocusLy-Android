package com.focusly.android;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Welcome / Startup Activity for FocusLy.
 * Displays brand logo, tagline, "Get Started" action and link to Log In.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGetStarted = findViewById(R.id.btnGetStarted);
        TextView tvAlreadyHaveAccount = findViewById(R.id.tvAlreadyHaveAccount);

        // Explicit Intent to navigate to RegisterActivity (Registration)
        btnGetStarted.setOnClickListener(v -> {
            Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        // Format footer string: "Already have an account? Log In" with styled link
        String prefix = getString(R.string.text_already_have_account);
        String link = getString(R.string.text_login_link);
        String fullText = prefix + link;

        SpannableString spannableString = new SpannableString(fullText);
        int startIndex = fullText.indexOf(link);
        if (startIndex != -1) {
            spannableString.setSpan(new UnderlineSpan(), startIndex, fullText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndex, fullText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.focusly_text_primary)), startIndex, fullText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tvAlreadyHaveAccount.setText(spannableString);

        // Explicit Intent to navigate to LoginActivity (Login)
        tvAlreadyHaveAccount.setOnClickListener(v -> {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });
    }
}
