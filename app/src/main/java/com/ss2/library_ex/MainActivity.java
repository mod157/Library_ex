package com.ss2.library_ex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static com.android.volley.Request.Method.GET;

public class MainActivity extends AppCompatActivity {
    RequestQueue req;
    ImageLoader imageLoader;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    RealmResults<Info_Data> rDB;
    ArrayList<RecyclerItem> itemList =  new ArrayList<>();
    final String url = "https://api.github.com/repos/JakeWharton/";

    private Realm realm = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        req = NetworkSingle.getInstace(this).getReq();
        imageLoader = NetworkSingle.getInstace(this).getImageLoader();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new RecyclerAdapter(itemList,R.layout.item_list, this);

        //정확한 터치를 위한 제스처
        final GestureDetector gestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener(){
           @Override
            public boolean onSingleTapUp(MotionEvent e){
               return true;
           }
        });
        //아이템에 대한 클릭 이벤트
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                //웹 Intent
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child!=null&&gestureDetector.onTouchEvent(e)) {
                    int viewNum = rv.getChildPosition(child);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(rDB.get(viewNum).getWeb_url()));
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }
        });
        NetworkData();
        Databaseinit();
    }

    // 초기화
    private void Databaseinit(){
        RealmConfiguration realmconfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmconfig);
        realm = Realm.getDefaultInstance();
    }

    // id값이 중복 없이 추가
    private void insertData(String id, String title, String img_url, String web_url) {
        realm.beginTransaction();
        RealmResults<Info_Data> db_data = realm.where(Info_Data.class).findAll();
        for(int i = 0; i<db_data.size(); i++){
            if(i == db_data.size()){
                Info_Data data = realm.createObject(Info_Data.class);
                data.setId(Integer.parseInt(id));
                data.setTitle(title);
                data.setImg_url(img_url);
                data.setWeb_url(web_url);
            }
            if(db_data.get(i).getId() == Integer.parseInt(id))
                break;
        }
        realm.commitTransaction();

    }
    //저장된 값을 저장 View에 추가
    private void SelectData(){
        rDB = realm.where(Info_Data.class).findAll();
        for(int i = 0 ; i < rDB.size(); i++){
            Log.v("DB : " + rDB.size(), rDB.get(i).toString());
            //Recycler 추가
            RecyclerItem recyclerItem = new RecyclerItem();
            recyclerItem.setTitle(rDB.get(i).getTitle());
            recyclerItem.setBitmap(rDB.get(i).getImg_url());
            itemList.add(recyclerItem);
            adapter = new RecyclerAdapter(itemList, R.layout.item_list, MainActivity.this);
            recyclerView.setAdapter(adapter);
        }
    }

    // 모든 데이터 삭제
    public void DeleteData(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }
    
    public void NetworkData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(GET, url + "DiskLruCache/issues",(String) null, listenerJson(), listenError());
        req.add(jsonArrayRequest);
    }

    private Response.Listener<JSONArray> listenerJson() {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //DB전체 삭제 후에 다시 삽입
                  //  DeleteData();
                    for(int i = 0 ; i<response.length();i++){
                        JSONObject json = response.getJSONObject(i);
                        String id = json.getString("id");
                        String title = json.getString("title");
                        JSONObject user = json.getJSONObject("user");
                        String img_url = user.getString("avatar_url");
                        String web_url = user.getString("html_url");
                        //DB
                        insertData(id, title, img_url, web_url);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SelectData();
            }
        };
    }

    private void DB_Set(ParsingData data){

    }

    private Response.ErrorListener listenError() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Listener", "error" + error);
            }
        };
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
