package com.android.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.food.client.ApiUtils;
import com.android.food.manager.AccountManager;
import com.android.food.models.ForgotPasswordRequest;
import com.android.food.models.ForgotPasswordResponse;
import com.android.food.services.YummyFoodService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordFragment extends Fragment {
    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        YummyFoodService mService = ApiUtils.getFoodService();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        // xử lí nút tìm mật khẩu
        Button btnSend = (Button) v.findViewById(R.id.button_send);
        TextInputEditText emailEditText = v.findViewById(R.id.et_email);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(emailEditText.getText()).toString();

                ForgotPasswordRequest data = new ForgotPasswordRequest();
                data.setEmail(email);

                mService.sendOtp(data).enqueue(new Callback<ForgotPasswordResponse>() {
                    @Override
                    public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getActivity(), "Email không tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "Đã gửi OTP đến email của bạn!", Toast.LENGTH_SHORT).show();
                            AccountManager.getInstance().setEmail(email);
                            OtpFragment otpFragment = new OtpFragment();
                            FragmentManager manager = getFragmentManager();
                            assert manager != null;
                            manager.beginTransaction()
                                    .replace(R.id.container, otpFragment)
                                    .commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {

                    }
                });
            }
        });

        // xử lí nút back
        Button toolBarBackButton = (Button) v.findViewById(R.id.toolbarbtn);
        toolBarBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                UserFragment userFragment = new UserFragment();
                FragmentManager manager = getFragmentManager();
                assert manager != null;
                manager.beginTransaction()
                        .replace(R.id.container, userFragment)
                        .commit();
            }
        });

        return v;
    }
}