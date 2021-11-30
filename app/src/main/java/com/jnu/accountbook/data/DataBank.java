package com.jnu.accountbook.data;

import com.jnu.accountbook.AccountItem;
import com.jnu.accountbook.R;

import java.util.ArrayList;
import java.util.List;

/*数据仓库 */
public class DataBank {
    public List<AccountItem> loadData() {
        List<AccountItem> accountItems =new ArrayList<AccountItem>();
        accountItems.add(new AccountItem("生活", R.drawable.life,50));
        accountItems.add(new AccountItem("学习",R.drawable.study,30));
        accountItems.add(new AccountItem("食物",R.drawable.food,20));
        accountItems.add(new AccountItem("其他",R.drawable.others,10));
        accountItems.add(new AccountItem("收入",R.drawable.income,30));
        return accountItems;
    }
}
