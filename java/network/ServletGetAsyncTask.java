package network;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Gaby on 16/10/2015.
 */
public class ServletGetAsyncTask extends AsyncTask<ParamsAsyncTask, Void, String> {

    private Context context;

    @Override
    protected String doInBackground(ParamsAsyncTask... params) {
        context = params[0].getContext();

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://hellomoongae-10.appspot.com/ContactList?respType=json"); // 10.0.2.2 is localhost's IP address in Android emulator
        try {
            // Add name data to request
            //List<NameValuePair> nameValuePairs = params[0].getListPair();
            //nameValuePairs.add(new BasicNameValuePair("name", name));
            //httpGet.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity());
            }
            return "Error: " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase();

        } catch (ClientProtocolException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
