package com.zennex.task.module.act_navigation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zennex.task.R;
import com.zennex.task.model.DrawerItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationAdapter extends BaseAdapter {

    private List<DrawerItem> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public NavigationAdapter(Context context, List<DrawerItem> list) {
        this.list = list;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        DrawerHolder viewHolder;
        DrawerItem item = list.get(position);

        if (view == null) {
            view = layoutInflater.inflate(R.layout.li_navigation_item, parent, false);
            viewHolder = new DrawerHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (DrawerHolder) view.getTag();
        }

        String name = item.getName();
        Drawable drawable = view.getResources().getDrawable(item.getImgResID());

        viewHolder.txtName.setText(name);
        viewHolder.ivIcon.setImageDrawable(drawable);

        return view;
    }

    class DrawerHolder {

        @BindView(R.id.li_navigation_item_iv_picture) ImageView ivIcon;
        @BindView(R.id.li_navigation_item_txt_name) TextView txtName;

        DrawerHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

