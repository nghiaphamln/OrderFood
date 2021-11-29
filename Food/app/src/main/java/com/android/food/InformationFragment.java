package com.android.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.food.client.ApiUtils;
import com.android.food.manager.AccountManager;
import com.android.food.models.GetInformationRequest;
import com.android.food.models.GetInformationResponse;
import com.android.food.services.YummyFoodService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationFragment extends Fragment {
    public InformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        YummyFoodService mService = ApiUtils.getFoodService();
        View v = inflater.inflate(R.layout.fragment_information, container, false);

        // Xử lí nút trở về nè
        Button toolBarBackButton = (Button) v.findViewById(R.id.toolbarbtn);
        toolBarBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AccountManagerFragment accountManagerFragment = new AccountManagerFragment();
                FragmentManager manager = getFragmentManager();
                assert manager != null;
                manager.beginTransaction()
                        .replace(R.id.container, accountManagerFragment)
                        .commit();
            }
        });

        // lấy dữ liệu từ api
        GetInformationRequest data = new GetInformationRequest();
        data.setUsername(AccountManager.getInstance().getUsername());
        mService.getInformation(data).enqueue(new Callback<GetInformationResponse>() {
            @Override
            public void onResponse(Call<GetInformationResponse> call, Response<GetInformationResponse> response) {
                if (response.isSuccessful()) {
                    String username = response.body().getUsername();
                    String email = response.body().getEmail();
                    String phone = response.body().getPhone();
                    System.out.println(username + "\n" + email + "\n" + phone);
                    // đưa thông tin người dùng vào form
                    TextInputEditText emailEditText = (TextInputEditText) v.findViewById(R.id.et_email);
                    emailEditText.setText(email);

                    TextInputEditText usernameEditText = (TextInputEditText) v.findViewById(R.id.et_username);
                    usernameEditText.setText(username);

                    TextInputEditText phoneEditText = (TextInputEditText) v.findViewById(R.id.et_phone);
                    phoneEditText.setText(phone);
                }
                else {
                    Toast.makeText(getActivity(), "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetInformationResponse> call, Throwable t) {

            }
        });

        return v;
    }
}