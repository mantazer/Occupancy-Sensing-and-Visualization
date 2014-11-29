package muntaserahmed.ricerooms;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RoomsActivity extends Activity {

    ListView roomsListView;

    ArrayList<Room> roomsList = new ArrayList<Room>();
    ArrayAdapter<Room> roomArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        roomsListView = (ListView) findViewById(R.id.roomsListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rooms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            refresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refresh() {
        try {
            getAllRooms();
        } catch (JSONException e) {
            Log.d("JSONException", "refresh");
        }

        roomArrayAdapter = new ArrayAdapter<Room>(
                this,
                R.layout.row,
                roomsList
        );
        roomsListView.setAdapter(roomArrayAdapter);
    }

    public void getAllRooms() throws JSONException {
        RestClient.getAll(null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray roomArray = response.getJSONArray("nodes");
                    roomsList.clear();
                    for (int i = 0; i < roomArray.length(); i++) {
                        JSONObject roomObj = roomArray.getJSONObject(i);

                        String floor = roomObj.getString("nodeFloor");
                        int number = roomObj.getInt("nodeId");
                        int vacant = roomObj.getInt("vacant");

                        Room room = new Room(floor, number, vacant);
                        roomsList.add(room);
                    }
                } catch(Exception e) {
                    Log.d("JSONException", "getAllRooms");
                }
            }
        });
    }
}
