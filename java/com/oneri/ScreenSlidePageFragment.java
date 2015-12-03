package com.oneri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.oneri.Adapters.ItemsAdapter;
import com.oneri.Model.Content;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by quentinleroy on 28/11/15.
 */

public class ScreenSlidePageFragment extends Fragment {


    private Integer last_clicked;
    private boolean content_activity_launched;

    private ListView listViewLeft;
    private ListView listViewRight;

    int[] leftViewsHeights;
    int[] rightViewsHeights;

    private ArrayList<Content> leftItems;
    private ArrayList<Content> rightItems;

    private int position_in_the_tab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);

        this.content_activity_launched = false;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.position_in_the_tab = bundle.getInt(MainActivity.TAG_POSITION_CHOSEN, 0);
        }

        listViewLeft = (ListView) rootView.findViewById(R.id.list_view_left);
        listViewRight = (ListView) rootView.findViewById(R.id.list_view_right);

        /*** On va chercher les contenus dans la base de donnees (avec Retrofit)***/
        loadItems();

        //listViewLeft.setOnScrollListener(scrollListener);
        //listViewRight.setOnScrollListener(scrollListener);
        listViewLeft.setOnTouchListener(touchListener);
        listViewRight.setOnTouchListener(touchListener);

        listViewLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!content_activity_launched) {
                    Intent intent;
                    switch (last_clicked) {
                        case 0:
                            Toast.makeText(getContext(), "Left Position : " + position, Toast.LENGTH_SHORT).show();
                            intent = new Intent(getContext(), ContentActivity.class);
                            intent.putExtra(MyContentActivity.EXTRA_MESSAGE, leftItems.get(position));
                            startActivity(intent);
                            content_activity_launched = true;
                            break;
                        case 1:
                            Toast.makeText(getContext(), "Right Position : " + position, Toast.LENGTH_SHORT).show();
                            intent = new Intent(getContext(), ContentActivity.class);
                            intent.putExtra(MyContentActivity.EXTRA_MESSAGE, rightItems.get(position));
                            startActivity(intent);
                            content_activity_launched = true;
                            break;
                    }
                }
            }
        });

        listViewRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!content_activity_launched) {
                    Intent intent;
                    switch (last_clicked) {
                        case 0:
                            Toast.makeText(getContext(), "Left Position : " + position, Toast.LENGTH_SHORT).show();
                            intent = new Intent(getContext(), ContentActivity.class);
                            intent.putExtra(MyContentActivity.EXTRA_MESSAGE, leftItems.get(position));
                            startActivity(intent);
                            content_activity_launched = true;
                            break;
                        case 1:
                            Toast.makeText(getContext(), "Right Position : " + position, Toast.LENGTH_SHORT).show();
                            intent = new Intent(getContext(), ContentActivity.class);
                            intent.putExtra(MyContentActivity.EXTRA_MESSAGE, rightItems.get(position));
                            startActivity(intent);
                            content_activity_launched = true;
                            break;
                    }
                }
            }
        });
        return rootView;
    }

    private void loadItems() {
        Call<List<Content>> call = GlobalVars.apiService.getContents(GlobalVars.EMAIL_CURRENT_USER, /*URLEncoder.encode(*/GlobalVars.CONTENT_LIST_FLAG_SERVLET.get(position_in_the_tab)/*)*/);
        call.enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Response<List<Content>> response, Retrofit retrofit) {
                int statusCode = response.code();
                List<Content> contents = response.body();

                Log.i("STATUS", "" + response.message());
                Log.i("STATUS", "" + statusCode);
                Log.i("STATUS", "" + response.toString());
                Log.i("STATUS", "" + response.raw());
                Log.i("STATUS", "" + response.isSuccess());

                leftItems = new ArrayList<Content>();
                rightItems = new ArrayList<Content>();


                String contentString = "";
                for (int i = 0; i < contents.size(); i = i + 1) {
                    Log.i("CONTENTS", contents.get(i).getJsonString());
                    if ((i % 2) == 0) {
                        leftItems.add(contents.get(i));
                    } else {
                        rightItems.add(contents.get(i));

                     }
                    contentString = contentString + contents.get(i).getJsonString();
                }

                Log.i("CONTENS", "" + contents.size());


                /*** On envoie a l'adapteur de la listview les contenus recuperes depuis le servlet***/
                ItemsAdapter leftAdapter = new ItemsAdapter(getContext(), R.layout.item, leftItems, GlobalVars.CONTENT_LIST_FLAG_DRAWABLE.get(position_in_the_tab), leftItems.size());
                ItemsAdapter rightAdapter = new ItemsAdapter(getContext(), R.layout.item, rightItems, GlobalVars.CONTENT_LIST_FLAG_DRAWABLE.get(position_in_the_tab), rightItems.size());
                listViewLeft.setAdapter(leftAdapter);
                listViewRight.setAdapter(rightAdapter);

                /*** Utile pour la synchronisation des scroll***/
                leftViewsHeights = new int[leftItems.size()];
                rightViewsHeights = new int[rightItems.size()];

                Log.i("ONRESPONSE RETROFIT", "FINISHED");
                Log.i("LEFTITEMS", "SIZE : " + leftItems.size());
                Log.i("RIGHTITEMS", "SIZE : " + rightItems.size());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }

    /***
     * Quand on clicke sur la liste de gauche (respectivement de droite), on dispatch le
     * click event surla liste de droite (respectivement de gauche)pour lui faire croire
     * qu'on appuie sur les deux, et ainsi les deux listes scrollent
     * même si en fait on en scrolle qu'une sur les deux avec le doigt
     */
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        boolean dispatched = false;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (v.equals(listViewLeft) && !dispatched) {
                last_clicked = 0;
                dispatched = true;
                listViewRight.dispatchTouchEvent(event);
            } else if (v.equals(listViewRight) && !dispatched) {
                last_clicked = 1;
                dispatched = true;
                listViewLeft.dispatchTouchEvent(event);
            }
            dispatched = false;
            return false;
        }

    };

    Integer CLICK_ACTION_THRESHHOLD = 5;

    private boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        if (differenceX > CLICK_ACTION_THRESHHOLD/* =5 */ || differenceY > CLICK_ACTION_THRESHHOLD) {
            return false;
        }
        return true;
    }

    /***
     * Finalement on n'utilise pas ça pour le moment
     * Si on l'utilise : le scroll s'arrête quand on arrive à la fin d'une des deux listviews
     * (et donc la fin de l'autre scrollview ne peut pas s'afficher)
     * Si on ne l'utilise pas : quand on arrive à la fin d'une des deux listviews,
     * l'autre continue de scroller jusqu'à la fin, ce comportement n'est pas parfait mais
     * préférable à l'autre cas (dans lequel on ne peut pas voir la fin d'une des deux list)
     */
    AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView v, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {

            if (view.getChildAt(0) != null) {
                if (view.equals(listViewLeft)) {
                    leftViewsHeights[view.getFirstVisiblePosition()] = view.getChildAt(0).getHeight();
                    int h = 0;
                    for (int i = 0; i < listViewRight.getFirstVisiblePosition(); i++) {
                        h += rightViewsHeights[i];
                    }

                    int hi = 0;
                    for (int i = 0; i < listViewLeft.getFirstVisiblePosition(); i++) {
                        hi += leftViewsHeights[i];
                    }

                    int top = h - hi + view.getChildAt(0).getTop();
                    listViewRight.setSelectionFromTop(listViewRight.getFirstVisiblePosition(), top);
                } else if (view.equals(listViewRight)) {

                    rightViewsHeights[view.getFirstVisiblePosition()] = view.getChildAt(0).getHeight();

                    int h = 0;
                    for (int i = 0; i < listViewLeft.getFirstVisiblePosition(); i++) {
                        h += leftViewsHeights[i];
                    }

                    int hi = 0;
                    for (int i = 0; i < listViewRight.getFirstVisiblePosition(); i++) {
                        hi += rightViewsHeights[i];
                    }

                    int top = h - hi + view.getChildAt(0).getTop();
                    listViewLeft.setSelectionFromTop(listViewLeft.getFirstVisiblePosition(), top);
                }

            }

        }
    };

}
