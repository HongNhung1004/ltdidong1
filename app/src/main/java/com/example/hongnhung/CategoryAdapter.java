package com.example.hongnhung;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class CategoryAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] names;
    private final int[] images;

    public CategoryAdapter(Activity context, String[] names, int[] images) {
        super(context, R.layout.item_home, names);
        this.context = context;
        this.names = names;
        this.images = images;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_home, null, true);

        TextView itemName = rowView.findViewById(R.id.itemName);
        ImageView itemImage = rowView.findViewById(R.id.itemImage);

        itemName.setText(names[position]);
        itemImage.setImageResource(images[position]);

        return rowView;
    }
}
