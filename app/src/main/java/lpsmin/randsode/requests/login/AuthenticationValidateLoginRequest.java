package lpsmin.randsode.requests.login;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lpsmin.randsode.models.Session;
import lpsmin.randsode.models.Token;
import lpsmin.randsode.shared.Closure;
import lpsmin.randsode.shared.JSONGetRequest;

public class AuthenticationValidateLoginRequest extends JSONGetRequest<Token> {

    public AuthenticationValidateLoginRequest(String username, String password, String requestToken, final Response.Listener<Session> listener, final Closure<VolleyError> errorListener) {
        super("https://api.themoviedb.org/3/authentication/token/validate_with_login?username=" + username + "&password=" + password + "&request_token=" + requestToken, Token.class, new Response.Listener<Token>() {
            @Override
            public void onResponse(Token response) {
                new AuthenticationNewSessionRequest(response.request_token, listener, errorListener);
            }
        }, null, null, null, errorListener);
    }
}
