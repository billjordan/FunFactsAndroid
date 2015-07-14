package info.billjordan.funfacts;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by bill on 7/11/15.
 */
public class FetchFactsTask extends AsyncTask{
    private static final String LOG_TAG = "FetchCategoriesTask";
//    private static final String fqdn = "192.168.1.127";
    private static final String fqdn = "www.billjordan.info";
    private static final String portNumber = "10080";
    private static final String path = "facts";
    private FactListActivity factListActivity;
    private int categoryId;

    /**
     * Creates a new asynchronous task. This constructor must be invoked on the UI thread.
     */
    public FetchFactsTask(FactListActivity factListActivity, int categoryId) {
        super();
        this.factListActivity = factListActivity;
        this.categoryId = categoryId;
    }

    /**
     * Creates a new asynchronous task. This constructor must be invoked on the UI thread.
     */


    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Object doInBackground(Object[] params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        URL url = null;

        // Will contain the raw JSON response as a list of categories.
        ArrayList<Fact> facts = new ArrayList<Fact>();

        String urlStr = "http://" + fqdn + ":" + portNumber + "/" + path + "/" + categoryId;

        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            Log.d(LOG_TAG, inputStream.toString());
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line);
            }

            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }

            String result = buffer.toString();
            try {
                JSONObject json = new JSONObject(result);
                JSONArray factJsonList = json.getJSONArray("facts");
                for (int i = 0; i < factJsonList.length(); i++) {
                    String label = ((JSONObject) factJsonList.get(i)).getString("label");
                    int id = ((JSONObject) factJsonList.get(i)).getInt("ID");
                    facts.add(new Fact(label, id));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            Log.d(LOG_TAG, facts.toString());


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


        return facts;

    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param o The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        ArrayList<Fact> facts = (ArrayList<Fact>) o;
        factListActivity.setFactListViewAdapter(facts);
    }
}
