package snax2poreair.developer.cnmstudios.com.storeexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "STOREEXAMPLE";

    RecyclerView rViewAva, rViewBack, rViewTable;
    ProductAdapter pAAva, pABack, pATable;

    List<Product> pListAva, pListBack, pListTable;

    String url = "http://cnmstudios.com/v3rify.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "0NCREATE");

        pListAva = new ArrayList<>();
        pListBack = new ArrayList<>();
        pListTable = new ArrayList<>();

        setuplists();
        rNInfo();

        findViewById(R.id.btnbuysub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Monthly", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.btnbuyday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Daily", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setuplists() {
        pListAva = new ArrayList<>();
        rViewAva = findViewById(R.id.rviewava);
        rViewAva.setHasFixedSize(true);
        rViewAva.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        pListBack = new ArrayList<>();
        rViewBack = findViewById(R.id.rviewback);
        rViewBack.setHasFixedSize(true);
        rViewBack.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));


        pListTable = new ArrayList<>();
        rViewTable = findViewById(R.id.rviewtable);
        rViewTable.setHasFixedSize(true);
        rViewTable.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    public void rNInfo() {

        StringRequest strreq = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        poplist(Response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "ERROR: Reaching Server", Toast.LENGTH_SHORT).show();

            }
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(strreq);
    }

    private void poplist(String r){

        String temp[] = r.split("@sep@");

        for(int i = 0; i < temp.length; i++){

            String tmp[] = temp[i].split("@");

            switch (tmp[0]){
                case "avatar":
                    pListAva.add(
                            new Product(tmp[1], tmp[2], tmp[3])
                    );
                    break;
                case "boc":
                    pListBack.add(
                            new Product(tmp[1], tmp[2], tmp[3])
                    );
                    break;
                case "table":
                    pListTable.add(
                            new Product(tmp[1], tmp[2], tmp[3])
                    );
                    break;
                default:
                    break;
            }

        }
        showlist();
    }

    private void showlist(){
        Log.d(TAG, "SList");

        pABack = new ProductAdapter(this, pListBack);
        rViewBack.setAdapter(pABack);

        pAAva = new ProductAdapter(this, pListAva);
        rViewAva.setAdapter(pAAva);

        pATable = new ProductAdapter(this, pListTable);
        rViewTable.setAdapter(pATable);
    }
}
