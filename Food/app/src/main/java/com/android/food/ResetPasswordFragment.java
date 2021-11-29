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
import com.android.food.models.ResetPasswordRequest;
import com.android.food.models.ResetPasswordResponse;
import com.android.food.services.YummyFoodService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordFragment extends Fragment {
    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        YummyFoodService mService = ApiUtils.getFoodService();
        View v = inflater.inflate(R.layout.fragment_reset_password, container, false);

        // Xử lí nút trở về nè
        Button toolBarBackButton = (Button) v.findViewById(R.id.toolbarbtn);
        toolBarBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                OtpFragment otpFragment = new OtpFragment();
                FragmentManager manager = getFragmentManager();
                assert manager != null;
                manager.beginTransaction()
                        .replace(R.id.container, otpFragment)
                        .commit();
            }
        });

        TextInputEditText passwordEditText = v.findViewById(R.id.et_password);
        TextInputEditText confirmPasswordEditText = v.findViewById(R.id.et_confirm_password);
        Button btnSend = (Button) v.findViewById(R.id.button_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = Objects.requireNonNull(passwordEditText.getText()).toString();
                String comfirmPassword = Objects.requireNonNull(confirmPasswordEditText.getText().toString());

                if (!password.equals(comfirmPassword)) {
                    Toast.makeText(getActivity(), "Mật khẩu đã nhập không trùng khớp!", Toast.LENGTH_SHORT).show();
                }
                else {
                    ResetPasswordRequest data = new ResetPasswordRequest();
                    data.setEmail(AccountManager.getInstance().getEmail());
                    data.setOtp(AccountManager.getInstance().getOtp());
                    data.setPassword(password);
                    mService.resetPassword(data).enqueue(new Callback<ResetPasswordResponse>() {
                        @Override
                        public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getActivity(), "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getActivity(), "Đã cập nhật mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                UserFragment userFragment = new UserFragment();
                                FragmentManager manager = getFragmentManager();
                                assert manager != null;
                                manager.beginTransaction()
                                        .replace(R.id.container, userFragment)
                                        .commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });

        return v;
    }
}