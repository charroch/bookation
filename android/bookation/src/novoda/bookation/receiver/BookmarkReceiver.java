
package novoda.bookation.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class BookmarkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TEST", intent.toString());
        context.getContentResolver().query(Uri.parse("content://novoda.bookation/Bookation"), null,
                null, null, null);
    }
}
