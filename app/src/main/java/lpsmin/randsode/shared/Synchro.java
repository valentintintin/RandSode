package lpsmin.randsode.shared;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import lpsmin.randsode.R;

public class Synchro {

    public static void execute(Context context) {
        Toast.makeText(context, R.string.sychro_executed, Toast.LENGTH_SHORT).show();
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
}
