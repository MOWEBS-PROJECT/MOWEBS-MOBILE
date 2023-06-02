package com.example.mowebs;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Context parentContext;
    RecyclerView recyclerViewBestCar, recyclerViewNearbyCar;
    ProductCardViewAdapter adapterBestCar, adapterNearbyCar;
    HorizontalScrollView horizontalScrollViewBrand;
    RequestQueue requestQueue;
    ArrayList<MobilObject> listBestCar   = new ArrayList<>();
    ArrayList<MobilObject> listNearbyCar = new ArrayList<>();

    public DashboardFragment() {
        // Required empty public constructor
    }

    public DashboardFragment(Context parentContext) {
        this.parentContext = parentContext;
    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        requestQueue = Volley.newRequestQueue(parentContext);
        adapterBestCar = new ProductCardViewAdapter(listBestCar, parentContext);
        adapterNearbyCar = new ProductCardViewAdapter(listNearbyCar, parentContext);

        recyclerViewBestCar = view.findViewById(R.id.recyclerViewBestCarContent);
        recyclerViewBestCar.setAdapter(adapterBestCar);
        recyclerViewBestCar.setLayoutManager(new LinearLayoutManager(parentContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewBestCar.addItemDecoration(new SpaceItemDecorationRecyclerView(50, 0));

        recyclerViewNearbyCar = view.findViewById(R.id.recyclerViewNearbyContent);
        recyclerViewNearbyCar.setAdapter(adapterNearbyCar);
        recyclerViewNearbyCar.setLayoutManager(new LinearLayoutManager(parentContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNearbyCar.addItemDecoration(new SpaceItemDecorationRecyclerView(50, 0));

        horizontalScrollViewBrand = view.findViewById(R.id.horizontalScrollViewBrand);
        horizontalScrollViewBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(parentContext, "" + view.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        getAllMobil();
        return view;
    }

    private void getAllMobil() {
        String path = "/get_all_mobil";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(RequestDatabase.ENDPOINT + path, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i < response.length(); i++) {
                    MobilObject mobilObject = new MobilObject();
                    JSONObject jsonObject = null;

                    try {
                        jsonObject = response.getJSONObject(i);
                    } catch (Exception e) {}
                    if (jsonObject == null) {return;}

                    try {
                        mobilObject.setId(jsonObject.getString("_id"));
                        mobilObject.setMerk(jsonObject.getString("merk"));
                        mobilObject.setJenis(jsonObject.getString("jenis"));
                        mobilObject.setHarga(jsonObject.getString("harga"));
                        mobilObject.setDeskripsi(jsonObject.getString("deskripsi"));
                        mobilObject.setPlat(jsonObject.getString("plat"));
                        mobilObject.setUrl_gambar(jsonObject.getString("url_gambar"));
                    } catch (JSONException e) {throw new RuntimeException(e);}

                    listBestCar.add(i, mobilObject);
                    listNearbyCar.add(response.length()-1-i, mobilObject);

                }

                adapterBestCar.notifyDataSetChanged();
                adapterNearbyCar.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(parentContext, "" + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}