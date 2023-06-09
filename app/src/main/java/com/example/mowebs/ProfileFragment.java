package com.example.mowebs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    LinearLayout orderHistoryButton, personalDataButton, logOutButton;
    TextView tvUserName;
    Context parentContext;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public ProfileFragment(Context parentContext) {
        this.parentContext  = parentContext;
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        orderHistoryButton = view.findViewById(R.id.orderHistoryButton);
        personalDataButton = view.findViewById(R.id.personalDataButton);
        logOutButton       = view.findViewById(R.id.logOutButton);
        tvUserName         = view.findViewById(R.id.tvUserName);

        tvUserName.setText(Preference.getUsername(parentContext));

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SignInActivity.class);
                getActivity().startActivity(intent);
                if (getActivity() != null) {
                    getActivity().finish();
                }

            }
        });

        orderHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parentContext, OrderActivity.class);
                startActivity(intent);
            }
        });

        personalDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parentContext, PersonalDataActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}