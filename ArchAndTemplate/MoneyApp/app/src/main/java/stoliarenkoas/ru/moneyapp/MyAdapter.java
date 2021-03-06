package stoliarenkoas.ru.moneyapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import stoliarenkoas.ru.moneyapp.entity.Order;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final List<Order> orders;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YY", Locale.ENGLISH);
    private OnClickListener onClickListener;

    public MyAdapter(List<Order> orderList) {
        orders = orderList;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView type;
        public TextView description;
        public TextView price;
        public TextView date;

        public MyViewHolder(CardView v) {
            super(v);
            type = v.findViewById(R.id.card_order_text_type);
            description = v.findViewById(R.id.card_order_text_description);
            price = v.findViewById(R.id.card_order_text_price);
            date = v.findViewById(R.id.card_order_text_date);
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClickListener.onLongClick(v, getAdapterPosition());
                    return false;
                }
            });
            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onShortClick(v, getAdapterPosition());
                }
            });
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_order, parent, false);
        //...
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.type.setText(orders.get(position).getType().toString());
        holder.description.setText(orders.get(position).getDescription());
        holder.price.setText(String.valueOf(orders.get(position).getPrice()));
        holder.date.setText(dateFormat.format(orders.get(position).getTime()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public interface OnClickListener {
        void onLongClick(View view, int position);
        void onShortClick(View view, int position);
    }

}
