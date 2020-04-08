package si.uni_lj.fri.lrk.asynctaskexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new PostTask(this).execute("fake_url");

    }

    private static class PostTask extends AsyncTask<String, Integer, String> {

        private static final String TAG = "PostTask";

        private WeakReference<MainActivity> mReference;

        PostTask(MainActivity context) {
            mReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "in preExecute");
            super.onPreExecute();
            MainActivity instance = (MainActivity) mReference.get();
            ProgressBar bar = (ProgressBar) instance.findViewById(R.id.progressBar);
            bar.setVisibility(View.VISIBLE);
            bar.setProgress(0);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "in doInBackground");
            String url=strings[0];
            for (int i = 0; i <= 100; i += 10) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return "All Done!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d(TAG, "in progressUpdate");

            super.onProgressUpdate(values);
            MainActivity instance = (MainActivity) mReference.get();
            ProgressBar bar = (ProgressBar) instance.findViewById(R.id.progressBar);
            bar.setVisibility(View.VISIBLE);
            bar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "in onPostExecute");

            super.onPostExecute(s);
            MainActivity instance = (MainActivity) mReference.get();
            TextView text = (TextView) instance.findViewById(R.id.status);
            text.setText(s);
            ProgressBar bar = (ProgressBar) instance.findViewById(R.id.progressBar);
            bar.setVisibility(View.GONE);
        }
    }



}