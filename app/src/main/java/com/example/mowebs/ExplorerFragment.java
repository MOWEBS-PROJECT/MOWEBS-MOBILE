package com.example.mowebs;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ExplorerFragment extends Fragment {

    private static final String ENDPOINT = "https://ap-southeast-1.aws.data.mongodb-api.com/app/rentalmobil-qrwuq/endpoint/get_all_mobil";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Context parentContext;
    RecyclerView recyclerView;
    ProductCardViewAdapter adapter;
    LinearLayout filterButtonLowPrice, filterButtonFavorite, filterButtonBestSeller, filterButtonPromo;
    private ArrayList<MobilObject> listMobil = new ArrayList<>();
    private RequestQueue requestQueue;

    public ExplorerFragment() {
        // Required empty public constructor
    }

    public ExplorerFragment(Context parentContext) {
        this.parentContext = parentContext;
    }

    public static ExplorerFragment newInstance(String param1, String param2) {
        ExplorerFragment fragment = new ExplorerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_explorer, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewContentExplorer);

        adapter = new ProductCardViewAdapter(listMobil, parentContext);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(parentContext, 2, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new SpaceItemDecorationRecyclerView(50,70));

        EditText editTextSearch = view.findViewById(R.id.editTextSearch);
        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_SEARCH){
//                    getMobilByCloserCharacter(editTextSearch.getText().toString());
                    return true;
                };
                return false;
            }
        });

        getAllMobil();

        return view;
    }



    private void getAllMobil() {

        requestQueue = Volley.newRequestQueue(parentContext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ENDPOINT, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i < response.length(); i++) {
                    MobilObject mobilObject = new MobilObject();
//                    MobilSpesifikasiObject mobilSpesifikasiObject = new MobilSpesifikasiObject();
//                    ReviewsMobilObject reviewsMobilObject = new ReviewsMobilObject();
                    JSONObject jsonObject = null;
//                    JSONObject jsonObjectSpesifikasi = null;
//                    JSONArray  jsonArrayReviews = null;

                    try {
                        jsonObject = response.getJSONObject(i);
                    } catch (Exception e) {}
                    if (jsonObject == null) {return;}

//                    try {
//                        jsonArrayReviews        = jsonObject.getJSONArray("reviews");
//                        jsonObjectSpesifikasi   = jsonObject.getJSONObject("spesifikasi");
//                    } catch(Exception e) {}

                    try {
//                        Toast.makeText(parentContext, jsonObject.getString("url_gambar"), Toast.LENGTH_SHORT).show();
                        mobilObject.setId(jsonObject.getString("_id"));
                        mobilObject.setMerk(jsonObject.getString("merk"));
                        mobilObject.setJenis(jsonObject.getString("jenis"));
                        mobilObject.setHarga(jsonObject.getString("harga"));
                        mobilObject.setDeskripsi(jsonObject.getString("deskripsi"));
                        mobilObject.setPlat(jsonObject.getString("plat"));
                        mobilObject.setUrl_gambar(jsonObject.getString("url_gambar"));
                    } catch (JSONException e) {throw new RuntimeException(e);}

                    listMobil.add(mobilObject);

//                    if (jsonObjectSpesifikasi != null) {
//                        try {
//                            mobilSpesifikasiObject.setAkselerasi(jsonObjectSpesifikasi.getString("akselerasi"));
//                            mobilSpesifikasiObject.setTransmisi(jsonObjectSpesifikasi.getString("transmisi"));
//                            mobilSpesifikasiObject.setKursi(jsonObjectSpesifikasi.getString("kursi"));
//                            mobilSpesifikasiObject.setWarna(jsonObjectSpesifikasi.getString("warna"));
//                            mobilSpesifikasiObject.setFuel(jsonObjectSpesifikasi.getString("fuel"));
//
//                            mobilObject.setSpesifikasi(mobilSpesifikasiObject);
//                        } catch(Exception e) {}
//                    }

                }

                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(parentContext, "" + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getMobilByCloserCharacter(String searchCar) {

    }
}