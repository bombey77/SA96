package bombey77.sa96;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG = "myLogs";

    TextView tv1, tv2, tv3;

    BroadcastReceiver br;

    private static final int TASK_1 = 1;
    private static final int TASK_2 = 2;
    private static final int TASK_3 = 3;

    static final int START = 100;
    static final int FINISH = 200;

    static final String BROADCAST_INTENT = "bombey77.poman.30";

    static final String PARAM_TIME = "param_time";
    static final String PARAM_RESULT = "param_result";
    static final String PARAM_TASK = "param_task";
    static final String PARAM_STATUS = "param_status";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tvTask1);
        tv2 = (TextView) findViewById(R.id.tvTask2);
        tv3 = (TextView) findViewById(R.id.tvTask3);

        tv1.setText("Task 1");
        tv2.setText("Task 2");
        tv3.setText("Task 3");

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int status = intent.getIntExtra(PARAM_STATUS, 0);
                int task = intent.getIntExtra(PARAM_TASK, 0);

                if (status == START) {
                    switch (task) {
                        case TASK_1:
                            tv1.setText("Task 1 started");
                            break;
                        case TASK_2:
                            tv2.setText("Task 2 started");
                            break;
                        case TASK_3:
                            tv3.setText("Task 3 started");
                            break;
                        default:
                            break;
                    }
                }

                int result = intent.getIntExtra(PARAM_RESULT, 0);

                if (status == FINISH) {
                    switch (task) {
                        case TASK_1:
                            tv1.setText("Task 1 finished with result " + result);
                            break;
                        case TASK_2:
                            tv2.setText("Task 2 finished with result " + result);
                            break;
                        case TASK_3:
                            tv3.setText("Task 3 finished with result " + result);
                            break;
                        default:
                            break;
                    }
                }
            }
        };

        IntentFilter intFilt = new IntentFilter(BROADCAST_INTENT);
        registerReceiver(br, intFilt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }

    public void onClickStart(View view) {

        Intent intent;

        intent = new Intent(this, MyService.class).putExtra(PARAM_TASK, TASK_1).putExtra(PARAM_TIME, 7);
        startService(intent);

        intent = new Intent(this, MyService.class).putExtra(PARAM_TASK, TASK_2).putExtra(PARAM_TIME, 4);
        startService(intent);

        intent = new Intent(this, MyService.class).putExtra(PARAM_TASK, TASK_3).putExtra(PARAM_TIME, 6);
        startService(intent);
    }
}
