package com.jnu.accountbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AccountItem> accountItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        RecyclerView mainRecycleView=findViewById(R.id.recycle_view_types);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecycleView.setLayoutManager(layoutManager);

        mainRecycleView.setAdapter(new MyRecyclerViewAdapter(accountItems));
    }

    public void initData(){
        accountItems =new ArrayList<AccountItem>();
        accountItems.add(new AccountItem("生活",R.drawable.life,50));
        accountItems.add(new AccountItem("学习",R.drawable.study,30));
        accountItems.add(new AccountItem("食物",R.drawable.food,20));
        accountItems.add(new AccountItem("其他",R.drawable.others,10));
        accountItems.add(new AccountItem("收入",R.drawable.income,30));
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private List<AccountItem> accountItems;

        public MyRecyclerViewAdapter(List<AccountItem> accountItems) {this.accountItems=accountItems;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.account_recoder, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder Holder, int position) {
            MyViewHolder holder= (MyViewHolder)Holder;

            holder.getImageView().setImageResource(accountItems.get(position).getPictureId());
            holder.getTextViewName().setText(accountItems.get(position).getName());
            holder.getTextViewMoney().setText(accountItems.get(position).getMoney()+"");

        }

        @Override
        public int getItemCount() { return accountItems.size();  }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            private final TextView textViewName;
            private final TextView textViewMoney;

            public MyViewHolder(View view) {
                super(view);

                this.imageView=itemView.findViewById(R.id.image_view_account_type);
                this.textViewName=itemView.findViewById(R.id.text_view_account_type);
                this.textViewMoney=itemView.findViewById(R.id.text_view_account_money);

                //itemView.setOnCreateContextMenuListener(this);
            }

            public ImageView getImageView() {
                return imageView;
            }

            public TextView getTextViewName() {
                return textViewName;
            }

            public TextView getTextViewMoney() {
                return textViewMoney;
            }
        }
    }
}