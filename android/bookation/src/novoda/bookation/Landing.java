
package novoda.bookation;

import com.xtify.android.sdk.PersistentLocationManager;

import novoda.bookation.service.LocationPing;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;

public class Landing extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Context context = this;
        final PersistentLocationManager persistentLocationManager = new PersistentLocationManager(
                context);
        
        Thread xtifyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                persistentLocationManager.setNotificationIcon(R.drawable.icon);
                persistentLocationManager.setNotificationDetailsIcon(R.drawable.icon);
                boolean trackLocation = persistentLocationManager.isTrackingLocation();
                boolean deliverNotifications = persistentLocationManager
                        .isDeliveringNotifications();
                if (trackLocation || deliverNotifications) {
                    persistentLocationManager.startService();
                }
            }
        });
        xtifyThread.start();
        context.getContentResolver().query(Uri.parse("content://novoda.bookation/Bookation"), null,
                null, null, null).close();
        Intent intent = new Intent(this, LocationPing.class);
        startService(intent);
    }

    private String[] getGoogleAccounts() {
        ArrayList<String> accountNames = new ArrayList<String>();
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (account.type.equals("com.google")) {
                accountNames.add(account.name);
            }
        }
        String[] result = new String[accountNames.size()];
        accountNames.toArray(result);
        return result;
    }
}
