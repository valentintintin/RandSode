package lpsmin.randsode.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lpsmin.randsode.R;
import lpsmin.randsode.models.Session;
import lpsmin.randsode.requests.login.AuthenticationNewTokenRequest;
import lpsmin.randsode.shared.Closure;
import lpsmin.randsode.shared.Synchro;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private View loading;
    private View formView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        usernameEditText = (EditText) findViewById(R.id.login_username);
        passwordEditText = (EditText) findViewById(R.id.login_password);

        String[] ident = Synchro.getUsernameAndPasswordAndSessionID(this);
        usernameEditText.setText(ident[0]);
        passwordEditText.setText(ident[1]);

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        formView = findViewById(R.id.login_form);
        loading = findViewById(R.id.login_progress);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void attemptLogin() {
        usernameEditText.setError(null);
        passwordEditText.setError(null);

        final String username = usernameEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        boolean cancel = false;

        if (username.equals("")) {
            usernameEditText.setError(getString(R.string.error_field_required));

            cancel = true;
        }

        if (password.equals("")) {
            passwordEditText.setError(getString(R.string.error_field_required));

            cancel = true;
        }

        if (cancel) {
            formView.requestFocus();
        } else {
            showProgress(true);
            new AuthenticationNewTokenRequest(username, password, new Response.Listener<Session>() {
                @Override
                public void onResponse(Session response) {
                    showProgress(false);

                    Synchro.setUsernameAndPasswordAndSessionID(LoginActivity.this, username, password, response.session_id);
                    Synchro.execute(LoginActivity.this);

                    finish();
                }
            }, new Closure<VolleyError>() {
                @Override
                public void go(VolleyError data) {
                    showProgress(false);

                    usernameEditText.setError(getString(R.string.error_field));
                    passwordEditText.setError("Possible wrong field");
                    formView.requestFocus();
                }
            });
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            formView.setVisibility(show ? View.GONE : View.VISIBLE);
            formView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    formView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            loading.setVisibility(show ? View.VISIBLE : View.GONE);
            loading.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loading.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            loading.setVisibility(show ? View.VISIBLE : View.GONE);
            formView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

