package com.focusly.android;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Registration Activity for FocusLy.
 * Handles user input for account creation and validates fields before proceeding.
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etEmail;
    private EditText etPassword;
    private CheckBox cbTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Bind Views
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbTerms = findViewById(R.id.cbTerms);

        TextView tvTermsLabel = findViewById(R.id.tvTermsLabel);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        TextView tvAlreadyHaveAccount = findViewById(R.id.tvAlreadyHaveAccount);

        // Format Terms Label ("I agree to the terms & conditions")
        setupTermsLabel(tvTermsLabel);

        // Format Footer Link ("Already have an account? Log In")
        setupFooterLink(tvAlreadyHaveAccount);

        // Sign Up Button Listener
        btnSignUp.setOnClickListener(v -> validateAndRegister());

        // Footer Navigation Listener (Explicit Intent to LoginActivity)
        tvAlreadyHaveAccount.setOnClickListener(v -> {
            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });
    }

    /**
     * Styles the Terms and Conditions text label.
     */
    private void setupTermsLabel(TextView tvTermsLabel) {
        String prefix = getString(R.string.terms_agree_prefix);
        String link = getString(R.string.terms_link);
        String fullText = prefix + link;

        SpannableString spannableString = new SpannableString(fullText);
        int startIndex = fullText.indexOf(link);
        if (startIndex != -1) {
            spannableString.setSpan(new UnderlineSpan(), startIndex, fullText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndex, fullText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.focusly_primary)), startIndex, fullText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tvTermsLabel.setText(spannableString);
    }

    /**
     * Styles the footer link text for navigation to Login.
     */
    private void setupFooterLink(TextView tvAlreadyHaveAccount) {
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
    }

    /**
     * Validates input fields and handles local form submission.
     */
    private void validateAndRegister() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // 1. Full Name Validation
        if (fullName.isEmpty()) {
            etFullName.setError(getString(R.string.error_name_required));
            etFullName.requestFocus();
            return;
        }

        // 2. Email Address Validation
        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.error_email_required));
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.error_email_invalid));
            etEmail.requestFocus();
            return;
        }

        // 3. Password Validation
        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.error_password_required));
            etPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            etPassword.setError(getString(R.string.error_password_short));
            etPassword.requestFocus();
            return;
        }

        // 4. Terms Checkbox Validation
        if (!cbTerms.isChecked()) {
            Toast.makeText(this, R.string.error_terms_required, Toast.LENGTH_SHORT).show();
            return;
        }

        // Success - Local Validation Passed
        Toast.makeText(this, "Form valid! Account creation will be implemented with persistence later.", Toast.LENGTH_LONG).show();
    }
}
