package com.zyx.android.phonerecords.db.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class ContactsNameDao {

    /**
     * 通过电话号码，查询联系人数据库，返回姓名
     * @param context 上下文
     * @param phoneNum 要查询的电话号码
     * @return
     * 当数据库中存在联系人时返回姓名，否则返回原号码
     */
    public static String getContactNameByPhoneNumber(Context context, String phoneNum) {
        String contactName = phoneNum;
        ContentResolver cr = context.getContentResolver();

        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/"+phoneNum);

        Cursor pCur = cr.query(uri, new String[] { "display_name" },null, null, null);
        if (pCur.moveToFirst()) {
            contactName = pCur.getString(0);
        }
        return contactName;
    }


}
