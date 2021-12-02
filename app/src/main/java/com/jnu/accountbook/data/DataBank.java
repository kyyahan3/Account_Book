package com.jnu.accountbook.data;

import android.accounts.Account;
import android.content.Context;

import com.jnu.accountbook.MainActivity;
import com.jnu.accountbook.R;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/*数据仓库 */
public class DataBank {
    public static final String DATA_FILE_NAME = "data";
    private final Context context;
    List<AccountItem> accountItemList;

    public DataBank(Context context) {
        this.context = context;
    }

    public List<AccountItem> loadData() {
/*      accountItemList =new ArrayList<AccountItem>();
        accountItemList.add(new AccountItem("生活", R.drawable.life,50));
        accountItemList.add(new AccountItem("学习",R.drawable.study,30));
        accountItemList.add(new AccountItem("食物",R.drawable.food,20));
        accountItemList.add(new AccountItem("其他",R.drawable.others,10));
        accountItemList.add(new AccountItem("收入",R.drawable.income,30));
*/
        accountItemList=new ArrayList<>();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(DATA_FILE_NAME));
            accountItemList = (ArrayList< AccountItem>) objectInputStream.readObject();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return accountItemList;
    }

    public void saveData() {

        ObjectOutputStream objectOutputStream=null;
        try{
            objectOutputStream = new ObjectOutputStream(context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(accountItemList);
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
