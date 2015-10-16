package network;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by Gaby on 16/10/2015.
 */
public class ParamsAsyncTask {

    private Context context;
    private ArrayList<Pair<String,String>> listPair;

    public ParamsAsyncTask(Context context, ArrayList<Pair<String,String>> listPair){
        this.context = context;
        this.listPair = listPair;
    }

    public Context getContext() {
        return context;
    }

    public ArrayList<Pair<String, String>> getListPair() {
        return listPair;
    }
}
