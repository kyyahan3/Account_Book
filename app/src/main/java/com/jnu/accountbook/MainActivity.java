package com.jnu.accountbook;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.accountbook.data.AccountItem;
import com.jnu.accountbook.data.DataBank;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static final int RESULT_CODE_ADD_DATA = 996;
    public static final int REQUEST_CODE_ADD =123;
    public static final int REQUEST_CODE_EDIT = REQUEST_CODE_ADD+1;
    public static final int REQUEST_CODE_DELETE = REQUEST_CODE_ADD+2;
    private List<AccountItem> accountItems;
    private DataBank dataBank;
    private MyRecyclerViewAdapter recyclerViewAdapter;


    ActivityResultLauncher<Intent> launcherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if (resultCode == RESULT_CODE_ADD_DATA) {
                if (null == data) return;
                String name = data.getStringExtra("name");
                double money = data.getDoubleExtra("money", 0);
                int position = data.getIntExtra("position", accountItems.size());
                switch(name)
                {
                    case "life":
                        accountItems.add(position,new AccountItem(name, R.drawable.life, money));
                        break;
                    case "food":
                        accountItems.add(position,new AccountItem(name, R.drawable.food, money));
                        break;
                    case "income":
                        accountItems.add(position,new AccountItem(name, R.drawable.income, money));
                        break;
                    case "study":
                        accountItems.add(position,new AccountItem(name, R.drawable.study, money));
                        break;
                    default:
                        accountItems.add(position,new AccountItem(name, R.drawable.others, money));
                }
                dataBank.saveData();
                recyclerViewAdapter.notifyItemInserted(position);
            }
        }
    });

    ActivityResultLauncher<Intent>  launcherEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if(resultCode== RESULT_CODE_ADD_DATA) {
                if(null==data)return;
                String name = data.getStringExtra("name");
                double money = data.getDoubleExtra("money", 0);
                int position = data.getIntExtra("position", accountItems.size());
                accountItems.get(position).setName(name);
                accountItems.get(position).setMoney(money);
                dataBank.saveData();
                recyclerViewAdapter.notifyItemChanged(position);
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initData();

        FloatingActionButton fabAdd=findViewById(R.id.floating_action_button_add);
        fabAdd.setOnClickListener(view -> {
            Intent intent=new Intent(this,InputActivity.class);
            intent.putExtra("position",accountItems.size());
            launcherAdd.launch(intent);
        });

        RecyclerView mainRecycleView=findViewById(R.id.recycle_view_types);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecycleView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new MyRecyclerViewAdapter(accountItems);
        mainRecycleView.setAdapter(recyclerViewAdapter);

        // 获取当前月份的日期号码
        TextView textViewDate=findViewById(R.id.text_view_date);;
        Calendar c = Calendar.getInstance();
        String mYear = String.valueOf(c.get(Calendar.YEAR));
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        textViewDate.setText(mYear + "." + mMonth + "." + mDay );

        //计算资产
        setAsset();
    }

    public void initData(){
        dataBank = new DataBank(MainActivity.this);
        accountItems = dataBank.loadData();

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

                MenuItem menuItemAdd=contextMenu.add(Menu.NONE,CONTEXT_MENU_ID_ADD,CONTEXT_MENU_ID_ADD,MainActivity.this.getResources().getString(R.string.string_menu_add));
                MenuItem menuItemEdit=contextMenu.add(Menu.NONE,CONTEXT_MENU_ID_EDIT,CONTEXT_MENU_ID_EDIT,MainActivity.this.getResources().getString(R.string.string_menu_edit));
                MenuItem menuItemDelete=contextMenu.add(Menu.NONE,CONTEXT_MENU_ID_DELETE,CONTEXT_MENU_ID_DELETE,MainActivity.this.getResources().getString(R.string.string_menu_delete));

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
                        intent.putExtra("name",accountItems.get(position).getName());
                        intent.putExtra("picture",accountItems.get(position).getPictureId());
                        launcherAdd.launch(intent);
                        break;

                    case CONTEXT_MENU_ID_EDIT:
                        intent= new Intent(MainActivity.this,InputActivity.class);
                        intent.putExtra("position",position);
                        intent.putExtra("name",accountItems.get(position).getName());
                        intent.putExtra("money",accountItems.get(position).getMoney());
                        launcherEdit.launch(intent);
                        break;

                    case CONTEXT_MENU_ID_DELETE:
                        AlertDialog.Builder alertDB = new AlertDialog.Builder(MainActivity.this);
                        alertDB.setPositiveButton(MainActivity.this.getResources().getString(R.string.string_confirmation), (dialogInterface, i) -> {
                            accountItems.remove(position);
                            dataBank.saveData();
                            MainActivity.MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                        });
                        alertDB.setNegativeButton(MainActivity.this.getResources().getString(R.string.string_cancel), (dialogInterface, i) -> {

                        });
                        alertDB.setMessage(MainActivity.this.getResources().getString(R.string.string_confirm_delete) +accountItems.get(position).getName()+"？");
                        alertDB.setTitle(MainActivity.this.getResources().getString(R.string.hint)).show();
                        break;
                }

                final Timer timer= new Timer();
                TimerTask task;
                Handler handler= new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        setAsset();
                        super.handleMessage(msg);
                    }
                };

                task= new TimerTask() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                };
                timer.schedule(task,1000,1000);

                return false;
            }

        }
    }

    private void setAsset() {
        //计算资产
        TextView textViewNetAsset=findViewById(R.id.text_view_amount_net_asset);
        TextView textViewIncome=findViewById(R.id.text_view_amount_income);
        TextView textViewSpend=findViewById(R.id.text_view_amount_spend);
        double income = 0.0;
        double spend = 0.0;
        for(int i=0; i<accountItems.size(); i++) {
            if(accountItems.get(i).getName().equals("income"))
                income += accountItems.get(i).getMoney();
            else
                spend += accountItems.get(i).getMoney();
        }
        textViewIncome.setText(String.valueOf(income));
        textViewSpend.setText(String.valueOf(spend));
        textViewNetAsset.setText(String.valueOf(income-spend));
    }

}