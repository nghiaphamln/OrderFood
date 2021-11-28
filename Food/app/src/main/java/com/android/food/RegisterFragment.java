package com.android.food;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.food.client.ApiUtils;
import com.android.food.models.RegisterRequest;
import com.android.food.models.RegisterResponse;
import com.android.food.services.YummyFoodService;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private YummyFoodService mService;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mService = ApiUtils.getFoodService();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);


        // Xử lí nút trở về nè
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

        // xử lí nút đăng ký nè
        TextInputEditText emailEditText = v.findViewById(R.id.et_email);
        TextInputEditText usernameEditText = v.findViewById(R.id.et_username_register);
        TextInputEditText phoneNumberEditText = v.findViewById(R.id.et_phone);
        EditText passwordEditText = v.findViewById(R.id.et_password_register);
        EditText rePasswordEditText = v.findViewById(R.id.et_confirm_password);

        Button signinrButton = (Button) v.findViewById(R.id.button_signin);
        signinrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(emailEditText.getText()).toString();
                String username = Objects.requireNonNull(usernameEditText.getText()).toString();
                String phoneNumber = Objects.requireNonNull(phoneNumberEditText.getText()).toString();
                String password = Objects.requireNonNull(passwordEditText.getText()).toString();
                String comfirmPassword = Objects.requireNonNull(rePasswordEditText.getText()).toString();

                if (!password.equals(comfirmPassword)) {
                    Toast.makeText(getActivity(), "Mật khẩu đã nhập không trùng nhau!", Toast.LENGTH_SHORT).show();
                }
                else {
                    RegisterRequest data = new RegisterRequest();
                    data.setEmail(email);
                    data.setUsername(username);
                    data.setPhone(phoneNumber);
                    data.setPassword(password);

                    mService.register(data).enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getActivity(), "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<RegisterResponse> call, Throwable t) {
                            Log.d("UserLogin", "Fail to get api: " + t.getMessage());
                        }
                    });
                }
            }
        });


        return v;
    }
}