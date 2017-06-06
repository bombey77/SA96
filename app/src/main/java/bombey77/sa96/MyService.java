package bombey77.sa96;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ромашка on 06.06.2017.
 */

public class MyService extends Service {

    private static final String LOG = "myLogs";

    ExecutorService es;

    private int time;
    private int task;
    private int result;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        es = Executors.newFixedThreadPool(2);

        time = intent.getIntExtra(MainActivity.PARAM_TIME, 0);
        task = intent.getIntExtra(MainActivity.PARAM_TASK, 0);

        MyRun myRun = new MyRun(time, task, startId);
        es.execute(myRun);

        Log.d(LOG, "onStartCommand startId#" + startId + ", task - " + task + ", time = " + time);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class MyRun implements Runnable {

        private int time;
        private int task;
        private int startId;

        MyRun(int time, final int task, final int startId) {
            this.time = time;
            this.task = task;
            this.startId = startId;
        }

        @Override
        public void run() {
            Intent intent = new Intent(MainActivity.BROADCAST_INTENT);

            try {
                intent.putExtra(MainActivity.PARAM_STATUS, MainActivity.START);
                intent.putExtra(MainActivity.PARAM_TASK, task);
                sendBroadcast(intent);

                TimeUnit.SECONDS.sleep(time);

                intent.putExtra(MainActivity.PARAM_STATUS, MainActivity.FINISH);
                intent.putExtra(MainActivity.PARAM_RESULT, time*100);
                sendBroadcast(intent);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d(LOG, "startId#" + startId + ", stopSelfResult = " + stopSelfResult(startId));
        }
    }
}
