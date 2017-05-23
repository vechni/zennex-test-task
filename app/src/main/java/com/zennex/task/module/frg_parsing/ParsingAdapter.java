package com.zennex.task.module.frg_parsing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zennex.task.R;
import com.zennex.task.model.ParsingDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParsingAdapter extends RecyclerView.Adapter<ParsingAdapter.ParsingHolder> {

    private List<ParsingDetail> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public ParsingAdapter(Context context, List<ParsingDetail> list) {
        this.list = list;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ParsingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.li_parsing_item, parent, false);
        return new ParsingHolder(view);
    }

    @Override
    public void onBindViewHolder(ParsingHolder holder, int position) {
        ParsingDetail item = list.get(position);

        String id = String.valueOf(item.getId());
        String desc = Html.fromHtml(item.getDescription()).toString();
        String time = item.getTime();
        String rating = String.valueOf(item.getRating());

        holder.txtId.append(id);
        holder.txtDesc.append(desc);
        holder.txtTime.append(time);
        holder.txtRating.append(rating);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ParsingHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.li_parsing_item_txt_id) TextView txtId;
        @BindView(R.id.li_parsing_item_txt_description) TextView txtDesc;
        @BindView(R.id.li_parsing_item_txt_time) TextView txtTime;
        @BindView(R.id.li_parsing_item_txt_rating) TextView txtRating;

        public ParsingHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
