package com.example.mowebs;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExplorerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExplorerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Context parentContext;
    RecyclerView recyclerView;
    LinearLayout filterButtonLowPrice, filterButtonFavorite, filterButtonBestSeller, filterButtonPromo;
    ArrayList<MobilObject> listMobil = new ArrayList<>();

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

        MobilObject mobil = new MobilObject();
        mobil.setHarga("12000");
        mobil.setMerk("Lmaborghini");
        mobil.setJenis("Huracan");
        mobil.setUrl_gambar("https://cdn1-production-images-kly.akamaized.net/eflBDpya4Blz6uOBFPYS6LJIfUc=/640x360/smart/filters:quality(75):strip_icc():format(jpeg)/kly-media-production/medias/3013916/original/099463400_1578299768-Lamborghini-Huracan_Evo_RWD-2021-1024-04.jpg");
        listMobil.add(mobil);
        listMobil.add(mobil);
        listMobil.add(mobil);
        listMobil.add(mobil);
        listMobil.add(mobil);


        View view = inflater.inflate(R.layout.fragment_explorer, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewContentExplorer);

        ExplorerViewAdapter adapter = new ExplorerViewAdapter(listMobil, parentContext);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(parentContext, 2, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new SpaceItemDecorationRecyclerView(50,70));

        return view;
    }

    private void getMobil() {

    }
}