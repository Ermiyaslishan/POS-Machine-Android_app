package com.example.pos;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListModel> mCardList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public TextView mItemTextView;
        public TextView mQxUTextView;
        public TextView mTotalTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            mCardView = itemView.findViewById(R.id.cardViewCart);
            mItemTextView = itemView.findViewById(R.id.regItemCard);
            mQxUTextView = itemView.findViewById(R.id.itemQxU);
            mTotalTextView = itemView.findViewById(R.id.itemUnitCard);
        }
    }

    public ListAdapter(List<ListModel> cardList) {
        mCardList = cardList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListModel currentItem = mCardList.get(position);

        holder.mItemTextView.setText(String.format("%s.%s", currentItem.getCode(), currentItem.get_item()));
        holder.mQxUTextView.setText(String.format("%dx%s", currentItem.getQuantity(), currentItem.getUnit_price()));
        holder.mTotalTextView.setText(String.valueOf(currentItem.getCost()));
    }


    @Override
    public int getItemCount() {
        return mCardList.size();
    }
}

