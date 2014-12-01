package muntaserahmed.ricerooms;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Typeface lobster = Typeface.createFromAsset(getAssets(), "fonts/Lobster_1.3.otf");
        TextView content = (TextView) findViewById(R.id.fullscreen_content);
        content.setTypeface(lobster);

        try {
            requestAndSend();
        } catch (JSONException e) {
            Log.d("JSONException", "onCreate: requestAndSend");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    public void requestAndSend() throws JSONException {
        RestClient.getAll(null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    ArrayList<Room> roomsToSend = new ArrayList<Room>();
                    JSONArray roomArray = response.getJSONArray("nodes");
                    roomsToSend.clear();
                    for (int i = 0; i < roomArray.length(); i++) {
                        JSONObject roomObj = roomArray.getJSONObject(i);

                        String floor = roomObj.getString("nodeFloor");
                        int number = roomObj.getInt("nodeId");
                        int vacant = roomObj.getInt("vacant");

                        Room room = new Room(floor, number, vacant);
                        roomsToSend.add(room);
                    }
                    SystemClock.sleep(2000);
                    Intent i = new Intent(SplashActivity.this, RoomsActivity.class);
                    i.putParcelableArrayListExtra("rooms", roomsToSend);
                    startActivity(i);

                } catch(Exception e) {
                    Log.d("JSONException", "getAllRooms");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_SHORT).show();
            }

        });
    }

}
