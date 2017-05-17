package lpsmin.randsode.requests.login;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lpsmin.randsode.models.Session;
import lpsmin.randsode.shared.Closure;
import lpsmin.randsode.shared.JSONRequest;

public class AuthenticationNewSessionRequest extends JSONRequest<Session> {

    public AuthenticationNewSessionRequest(String requestToken, final Response.Listener<Session> listener, final Closure<VolleyError> errorListener) {
        super("https://api.themoviedb.org/3/authentication/session/new?request_token=" + requestToken, Session.class, listener, null, null, null, errorListener);
    }
}
