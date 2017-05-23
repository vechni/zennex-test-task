package com.zennex.task.module.frg_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zennex.task.R;
import com.zennex.task.common.interfaces.OnItemClickListener;
import com.zennex.task.common.interfaces.OnItemLongClickListener;
import com.zennex.task.model.ListItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {

    private List<ListItem> list;
    private LayoutInflater layoutInflater;

    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;
    private CompoundButton.OnCheckedChangeListener itemClickListenerCheckBoxItem;

    public ListAdapter(Context context, List<ListItem> list, OnItemClickListener itemClickListener, OnItemLongClickListener itemLongClickListener, CompoundButton.OnCheckedChangeListener itemClickListenerCheckBoxItem) {
        this.list = list;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.itemClickListener = itemClickListener;
        this.itemLongClickListener = itemLongClickListener;
        this.itemClickListenerCheckBoxItem = itemClickListenerCheckBoxItem;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.li_list_item, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {

        holder.cbSelected.setTag(position);

        ListItem item = list.get(position);

        if (item.isState()) {
            holder.cbSelected.setChecked(true);
            holder.ivImage.setImageResource(R.mipmap.app_ic_item_enable);
        } else {
            holder.cbSelected.setChecked(false);
            holder.ivImage.setImageResource(R.mipmap.app_ic_item_disable);
        }

        String name = String.valueOf(item.getName());
        holder.txtName.setText(name);

        holder.setClickListener(itemClickListener);
        holder.setLongClickListener(itemLongClickListener);
        holder.setClickListenerCheckBox(itemClickListenerCheckBoxItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, CompoundButton.OnCheckedChangeListener {

        @BindView(R.id.li_list_item_iv_logo) public ImageView ivImage;
        @BindView(R.id.li_list_item_txt_name) public TextView txtName;
        @BindView(R.id.li_list_item_cb_selected) public CheckBox cbSelected;

        private OnItemClickListener itemClickListener;
        private OnItemLongClickListener itemLongClickListener;
        private CompoundButton.OnCheckedChangeListener itemClickListenerCheckBoxItem;

        public ListHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            cbSelected.setOnCheckedChangeListener(this);
        }

        public void setClickListener(OnItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public void setLongClickListener(OnItemLongClickListener itemLongClickListener) {
            this.itemLongClickListener = itemLongClickListener;
        }

        public void setClickListenerCheckBox(CompoundButton.OnCheckedChangeListener itemClickListenerCheckBoxItem) {
            this.itemClickListenerCheckBoxItem = itemClickListenerCheckBoxItem;
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (itemLongClickListener != null) itemLongClickListener.onLongClick(v, getAdapterPosition());
            return true;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (itemClickListenerCheckBoxItem != null) {
                itemClickListenerCheckBoxItem.onCheckedChanged(compoundButton, b);
            }
        }
    }
}
