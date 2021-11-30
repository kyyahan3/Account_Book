package com.jnu.accountbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.jnu.accountbook.InputActivity.InputActivity;
import com.jnu.accountbook.data.DataBank;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int RESULT_CODE_ADD_DATA = 996;
    public static final int REQUEST_CODE_ADD =123;
    public static final int REQUEST_CODE_EDIT = REQUEST_CODE_ADD+1;
    public static final int REQUEST_CODE_DELETE = REQUEST_CODE_ADD+2;
    private List<AccountItem> accountItems;
    //private DataBank dataBank;
    private MyRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD){
            if(resultCode== RESULT_CODE_ADD_DATA){
                String name=data.getStringExtra("name");
                Double money=data.getDoubleExtra("money",0);
                int position=data.getIntExtra("position",accountItems.size());
                accountItems.add(new AccountItem(name,R.drawable.others,money));
                recyclerViewAdapter.notifyItemInserted(position);
            }
        }

        if(requestCode == REQUEST_CODE_EDIT){
            if(resultCode== RESULT_CODE_ADD_DATA){
                String name=data.getStringExtra("name");
                Double money=data.getDoubleExtra("money",0);
                int position=data.getIntExtra("position",accountItems.size());
                accountItems.get(position).setName(name);
                accountItems.get(position).setMoney(money);
                recyclerViewAdapter.notifyItemChanged(position);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        RecyclerView mainRecycleView=findViewById(R.id.recycle_view_types);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecycleView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new MyRecyclerViewAdapter(accountItems);
        mainRecycleView.setAdapter(recyclerViewAdapter);
    }

    public void initData(){
        //dataBank = new DataBank();
        //accountItems = dataBank.loadData();
        accountItems = new ArrayList<AccountItem>();
        accountItems.add(new AccountItem("生活", R.drawable.life,50));
        accountItems.add(new AccountItem("学习",R.drawable.study,30));
        accountItems.add(new AccountItem("食物",R.drawable.food,20));
        accountItems.add(new AccountItem("其他",R.drawable.others,10));
        accountItems.add(new AccountItem("收入",R.drawable.income,30));
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private List<AccountItem> accountItems;

        public MyRecyclerViewAdapter(List<AccountItem> accountItems) {this.accountItems=accountItems;}

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

        private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
            public static final int CONTEXT_MENU_ID_ADD = 1;
            public static final int CONTEXT_MENU_ID_EDIT = CONTEXT_MENU_ID_ADD+1;
            public static final int CONTEXT_MENU_ID_DELETE = CONTEXT_MENU_ID_ADD+2;
            private final ImageView imageView;
            private final TextView textViewName;
            private final TextView textViewMoney;

            public MyViewHolder(View view) {
                super(view);

                this.imageView=itemView.findViewById(R.id.image_view_account_type);
                this.textViewName=itemView.findViewById(R.id.text_view_account_type);
                this.textViewMoney=itemView.findViewById(R.id.text_view_account_money);

                itemView.setOnCreateContextMenuListener(this);
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

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                int position=getAdapterPosition();

                MenuItem menuItemAdd=contextMenu.add(Menu.NONE,CONTEXT_MENU_ID_ADD,CONTEXT_MENU_ID_ADD,"Add"+position );
                MenuItem menuItemEdit=contextMenu.add(Menu.NONE,CONTEXT_MENU_ID_EDIT,CONTEXT_MENU_ID_EDIT,"Edit"+position);
                MenuItem menuItemDelete=contextMenu.add(Menu.NONE,CONTEXT_MENU_ID_DELETE,CONTEXT_MENU_ID_DELETE,"Delete"+position);

                menuItemAdd.setOnMenuItemClickListener(this);
                menuItemEdit.setOnMenuItemClickListener(this);
                menuItemDelete.setOnMenuItemClickListener(this);
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int position= getAdapterPosition();
                Intent intent;
                switch(menuItem.getItemId())
                {
                    case CONTEXT_MENU_ID_ADD:
                        /*
                        View dialogueView =LayoutInflater.from(MainActivity.this).inflate(R.layout.dialogue_input_record,null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setView(dialogueView);

                        alertDialogBuilder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialogBuilder.setCancelable(false).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialogBuilder.create().show();
                        accountItems.add(position,new AccountItem("生活",R.drawable.life,50));
                        MyRecyclerViewAdapter.this.notifyItemChanged(position);
*/
                        intent =new Intent(MainActivity.this,InputActivity.class);
                        intent.putExtra("position",position);
                        MainActivity.this.startActivityForResult(intent, REQUEST_CODE_ADD);
                        break;

                    case CONTEXT_MENU_ID_EDIT:
                        intent= new Intent(MainActivity.this,InputActivity.class);
                        intent.putExtra("position",position);
                        intent.putExtra("name",accountItems.get(position).getName());
                        intent.putExtra("money",accountItems.get(position).getMoney());
                        MainActivity.this.startActivityForResult(intent, REQUEST_CODE_EDIT);
                        //MyRecyclerViewAdapter.this.notifyItemChanged(position);
                        break;

                    case CONTEXT_MENU_ID_DELETE:
                        accountItems.remove(position);
                        //dataBank.saveData();
                        MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                        break;
                }
                return false;
            }
        }
    }
}