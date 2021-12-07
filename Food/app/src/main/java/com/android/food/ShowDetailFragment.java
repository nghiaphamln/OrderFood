package com.android.food;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.food.manager.AccountManager;
import com.android.food.manager.CartManager;
import com.android.food.manager.ProductManager;
import com.android.food.models.ProductsRespone;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ShowDetailFragment extends Fragment {
    public ShowDetailFragment() {
        // hihi
    }
    TextView title, price, desciption, addToCart;
    ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_detail, container, false);

        ProductsRespone product = ProductManager.getInstance().getSelectedProduct();

        title = v.findViewById(R.id.et_title);
        price = v.findViewById(R.id.et_price);
        desciption = v.findViewById(R.id.et_description);
        image = v.findViewById(R.id.iv_image);
        addToCart = v.findViewById(R.id.btn_add);

        title.setText(product.getName());
        price.setText(product.getPrice().toString());
        desciption.setText(product.getDescription());
        Picasso.get().load(product.getImage()).into(image);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountManager.getInstance().isLogin()) {
                    ArrayList<ProductsRespone> a = new ArrayList<>();
                    a.add(product);
                    CartManager.getInstance().setCartList(a);
                    Toast.makeText(v.getContext(), "Đã thêm sản phẩm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(v.getContext(), "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    public void setArguments(ProductsRespone productsRespone) {
    }
}