package network;

import android.content.Context;
import android.util.Pair;

import org.apache.http.NameValuePair;

import java.util.ArrayList;

/**
 * Created by Gaby on 16/10/2015.
 */
public class ParamsAsyncTask {

    private Context context;
    private ArrayList<NameValuePair> listPair;

    public ParamsAsyncTask(Context context, ArrayList<NameValuePair> listPair){
        this.context = context;
        this.listPair = listPair;
    }

    public Context getContext() {
        return context;
    }

    public ArrayList<NameValuePair> getListPair() {
        return listPair;
    }
}
