package shafir.irena.serviceandnotifications;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;

/**
 * Created by irena on 11/07/2017.
 */

public class MyJobService extends com.firebase.jobdispatcher.JobService {


    private static final String TAG = "Ness";

    @Override
    public boolean onStartJob(JobParameters job) {
        // work......
        showNotification();
        return false; //is there ongoing progress?
    }

    private void showNotification() {
        Log.d(TAG, "showNotification");
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false; // should this job be retired?
    }
}
