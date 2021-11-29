package com.android.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.food.client.ApiUtils;
import com.android.food.manager.AccountManager;
import com.android.food.models.ChangePasswordRequest;
import com.android.food.models.ChangePasswordResponse;
import com.android.food.services.YummyFoodService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        YummyFoodService mService = ApiUtils.getFoodService();
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);

        EditText oldPasswordEditText = (EditText) v.findViewById(R.id.et_old_password);
        EditText newPasswordEditText = (EditText) v.findViewById(R.id.et_new_password);
        EditText comfirmNewPasswordEditText = (EditText) v.findViewById(R.id.et_confirm_new_password);

        Button btnChangePassword = (Button) v.findViewById(R.id.button_change_password);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String comfirmNewPassword = comfirmNewPasswordEditText.getText().toString();

                if (!newPassword.equals(comfirmNewPassword)) {
                    Toast.makeText(getActivity(), "Mật khẩu mới đã nhập không trùng nhau!", Toast.LENGTH_SHORT).show();
                }
                else {
                    ChangePasswordRequest data = new ChangePasswordRequest();
                    data.setUsername(AccountManager.getInstance().getUsername());
                    data.setOldPassword(oldPassword);
                    data.setNewPassword(newPassword);
                    mService.changePassword(data).enqueue(new Callback<ChangePasswordResponse>() {
                        @Override
                        public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getActivity(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                AccountManagerFragment accountManagerFragment = new AccountManagerFragment();
                                FragmentManager manager = getFragmentManager();
                                assert manager != null;
                                manager.beginTransaction()
                                        .replace(R.id.container, accountManagerFragment)
                                        .commit();
                            }
                            else {
                                Toast.makeText(getActivity(), "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });

        return v;
    }
}