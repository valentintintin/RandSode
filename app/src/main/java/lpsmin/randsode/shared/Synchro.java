package lpsmin.randsode.shared;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lpsmin.randsode.R;
import lpsmin.randsode.models.Session;
import lpsmin.randsode.requests.account.MySeriesRequest;
import lpsmin.randsode.requests.login.AuthenticationNewTokenRequest;

public class Synchro {

    public static void connect(String username, String password, Response.Listener<Session> listener, Closure<VolleyError> errorListener) {
        new AuthenticationNewTokenRequest(username, password, listener, errorListener);
    }

    public static void execute(final Activity activity) {
        String[] ident = getUsernameAndPasswordAndSessionID(activity);
//
//        connect(ident[0], ident[1], new Response.Listener<Session>() {
//            @Override
//            public void onResponse(Session response) {
//                Toast.makeText(activity, R.string.sychro_executed, Toast.LENGTH_SHORT).show();
//            }
//        }, new Closure<VolleyError>() {
//            @Override
//            public void go(VolleyError data) {
//                Toast.makeText(activity, R.string.sychro_executed_error, Toast.LENGTH_LONG).show();
//            }
//        });

        new MySeriesRequest(ident[2]);
    }

    public static void setAutoEnable(Activity activity, boolean enable) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(activity.getString(R.string.synchro_auto_pref), enable);
        editor.apply();
    }

    public static boolean isAutoEnable(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(activity.getString(R.string.synchro_auto_pref), false);
    }

    public static void setUsernameAndPasswordAndSessionID(Activity activity, String username, String password, String sessionId) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(activity.getString(R.string.username_pref), username);
        editor.putString(activity.getString(R.string.password_pref), password);
        editor.putString(activity.getString(R.string.sessionid_pref), sessionId);
        editor.apply();
    }

    public static String[] getUsernameAndPasswordAndSessionID(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return new String[]{
                sharedPref.getString(activity.getString(R.string.username_pref), null),
                sharedPref.getString(activity.getString(R.string.password_pref), null),
                sharedPref.getString(activity.getString(R.string.sessionid_pref), null)
        };
    }
}
