package com.android.food;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.food.manager.ProductManager;
import com.android.food.models.ProductsRespone;
import com.squareup.picasso.Picasso;


public class ShowDetailFragment extends Fragment {
    public ShowDetailFragment() {
        // hihi
    }
    TextView title, price, desciption;
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

        title.setText(product.getName());
        price.setText(product.getPrice().toString());
        desciption.setText(product.getDescription());
        Picasso.get().load(product.getImage()).into(image);

        return v;
    }

    public void setArguments(ProductsRespone productsRespone) {
    }
}