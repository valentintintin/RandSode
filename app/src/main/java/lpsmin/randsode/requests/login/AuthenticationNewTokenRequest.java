package lpsmin.randsode.requests.login;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lpsmin.randsode.models.Session;
import lpsmin.randsode.models.Token;
import lpsmin.randsode.shared.Closure;
import lpsmin.randsode.shared.JSONRequest;

public class AuthenticationNewTokenRequest extends JSONRequest<Token> {

    public AuthenticationNewTokenRequest(final String username, final String password, final Response.Listener<Session> listener, final Closure<VolleyError> errorListener) {
        super("https://api.themoviedb.org/3/authentication/token/new?", Token.class, new Response.Listener<Token>() {
            @Override
            public void onResponse(Token response) {
                new AuthenticationValidateLoginRequest(username, password, response.request_token, listener, errorListener);
            }
        }, null, null, null, errorListener);
    }
}
