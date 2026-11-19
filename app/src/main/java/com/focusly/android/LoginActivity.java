package com.focusly.android;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Login Activity for FocusLy.
 * Handles user sign-in form interaction, password visibility toggle, and validation.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private TextView tvShowHidePassword;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind Views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvShowHidePassword = findViewById(R.id.tvShowHidePassword);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);
        Button btnLogIn = findViewById(R.id.btnLogIn);
        Button btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn);
        TextView tvDontHaveAccount = findViewById(R.id.tvDontHaveAccount);

        // Style Forgot Password link
        setupForgotPasswordLink(tvForgotPassword);

        // Style Footer Navigation link ("Don't have an account? Sign Up")
        setupFooterLink(tvDontHaveAccount);

        // Show/Hide Password Toggle Listener
        tvShowHidePassword.setOnClickListener(v -> togglePasswordVisibility());

        // Log In Action Listener
        btnLogIn.setOnClickListener(v -> validateAndLogin());

        // Forgot Password Placeholder Action
        tvForgotPassword.setOnClickListener(v ->
                Toast.makeText(LoginActivity.this, "Password recovery is not implemented yet.", Toast.LENGTH_SHORT).show()
        );

        // Google Sign-In Placeholder Action
        btnGoogleSignIn.setOnClickListener(v ->
                Toast.makeText(LoginActivity.this, "Google Sign-In is not available in this prototype.", Toast.LENGTH_SHORT).show()
        );

        // Navigation to RegisterActivity (Explicit Intent)
        tvDontHaveAccount.setOnClickListener(v -> {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });
    }

    /**
     * Toggles the password field between hidden and visible text.
     * Preserves the current selection/cursor position at the end of input.
     */
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            tvShowHidePassword.setText(R.string.action_show);
            isPasswordVisible = false;
        } else {
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            tvShowHidePassword.setText(R.string.action_hide);
            isPasswordVisible = true;
        }
        etPassword.setSelection(etPassword.getText().length());
    }

    /**
     * Styles the Forgot Password text as an underlined link.
     */
    private void setupForgotPasswordLink(TextView tvForgotPassword) {
        String text = getString(R.string.forgot_password);
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvForgotPassword.setText(spannableString);
    }

    /**
     * Styles the footer link text for navigation to Sign Up.
     */
    private void setupFooterLink(TextView tvDontHaveAccount) {
        String prefix = getString(R.string.text_dont_have_account);
        String link = getString(R.string.text_sign_up_link);
        String fullText = prefix + link;

        SpannableString spannableString = new SpannableString(fullText);
        int startIndex = fullText.indexOf(link);
        if (startIndex != -1) {
            spannableString.setSpan(new UnderlineSpan(), startIndex, fullText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndex, fullText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.focusly_primary)), startIndex, fullText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tvDontHaveAccount.setText(spannableString);
    }

    /**
     * Validates email and password input fields.
     */
    private void validateAndLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // 1. Email Validation
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

        // 2. Password Validation
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

        // Success - Local Validation Passed
        Toast.makeText(this, "Login form validated. Authentication will be implemented later.", Toast.LENGTH_LONG).show();
    }
}
