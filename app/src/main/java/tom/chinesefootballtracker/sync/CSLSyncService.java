package tom.chinesefootballtracker.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CSLSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static CSLSyncAdapter sCSLSyncAdapter = null;

    @Override
    public void onCreate() {
        //Log.d("CSLSyncService", "onCreate - CSLSyncService");
        synchronized (sSyncAdapterLock) {
            if (sCSLSyncAdapter == null) {
                sCSLSyncAdapter = new CSLSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sCSLSyncAdapter.getSyncAdapterBinder();
    }
}
