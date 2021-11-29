package com.android.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.food.client.ApiUtils;
import com.android.food.manager.AccountManager;
import com.android.food.services.YummyFoodService;

public class AccountManagerFragment extends Fragment {
    public AccountManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        YummyFoodService mService = ApiUtils.getFoodService();
        View v = inflater.inflate(R.layout.fragment_account_manager, container, false);

        // chỗ này là hiện cái dòng chữ welcome + username sau khi đã đăng nhập nè
        TextView welcomeTextView = (TextView) v.findViewById(R.id.text_welcome);
        welcomeTextView.setText(String.format("Chào bạn, %s!", AccountManager.getInstance().getUsername()));

        // chỗ này là load giao diện thông tin cá nhân khi nhấn vào trang thông tin cá nhân nè
        Button btnInformation = (Button) v.findViewById(R.id.btn_information);
        btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InformationFragment informationFragment = new InformationFragment();
                FragmentManager manager = getFragmentManager();
                assert manager != null;
                manager.beginTransaction()
                        .replace(R.id.container, informationFragment)
                        .commit();
            }
        });

        return v;
    }
}