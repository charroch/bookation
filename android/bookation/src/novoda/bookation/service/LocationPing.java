
package novoda.bookation.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

public class LocationPing extends Service implements LocationListener {
    private volatile Looper mServiceLooper;

    private volatile ServiceHandler mServiceHandler;

    private LocationManager manager;

    class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("ping");
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

        manager = (LocationManager) getBaseContext().getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates("gps", 10000, 0, this);
        Cursor cur = getContentResolver().query(Uri.parse("content://novoda.bookation/Bookation"),
                null, null, null, null);

        getContentResolver().registerContentObserver(
                Uri.parse("content://novoda.bookation/Bookation"), true, new MContentObserver(h));
        broadcast = PendingIntent.getBroadcast(getApplicationContext(), 1, new Intent(
                "novoda.rest.location_in_vicinity"), 1);
        DatabaseUtils.dumpCursor(cur);
//        while (cur.moveToNext()) {
//            manager.addProximityAlert(cur.getDouble(cur.getColumnIndexOrThrow("latitude")),
//                    cur.getDouble(cur.getColumnIndexOrThrow("longitude")), 1000, 10000, broadcast);
//        }
        cur.close();
    }

    Handler h = new Handler() {
    };

    private PendingIntent broadcast;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {
        // manager.add
        mServiceLooper.quit();
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
        Message message = mServiceHandler.obtainMessage();
        message.obj = location;
        mServiceHandler.handleMessage(message);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    class MContentObserver extends ContentObserver {
        public MContentObserver(Handler handler) {
            super(handler);
        }
    }
}
