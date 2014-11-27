package muntaserahmed.ricerooms;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RoomsActivity extends Activity {

    ArrayList<Room> roomsList = new ArrayList<Room>();
//    ArrayAdapter<Room> arrayAdapter;

    Room r1 = new Room(1, 104, 0);
    Room r2 = new Room(2, 204, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
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
            try {
                getAllRooms();
                for (Room r : roomsList) {
                    Log.d("ROOM: ", r.number + "");
                }
            } catch(JSONException e) {
                Log.d("JSONException: ", "onOptionsItemSelected");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getAllRooms() throws JSONException {
        RestClient.getAll(null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray roomArray = response.getJSONArray("nodes");
                    for (int i = 0; i < roomArray.length(); i++) {
                        JSONObject roomObj = roomArray.getJSONObject(i);
                        int floor = roomObj.getInt("node-floor");
                        int number = roomObj.getInt("node-id");
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
