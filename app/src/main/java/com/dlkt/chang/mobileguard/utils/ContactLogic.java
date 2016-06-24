package com.dlkt.chang.mobileguard.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import com.dlkt.chang.mobileguard.domain.UserInfo;

import java.util.ArrayList;
import java.util.List;


public class ContactLogic {
	
	
	// These are the Contacts rows that we will retrieve.
    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_ID,
    };
	
	 /**
     * 获取手机系统联系人信息
     * @return
     */
    public static List<UserInfo> getPhoneContacts(Context ctx) {
    	List<UserInfo> contacts = new ArrayList<>();
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";
        try {
            Cursor cursor = ctx.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                    CONTACTS_SUMMARY_PROJECTION,
                    select,
                    null, ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
            while (cursor.moveToNext()) {
                int indexId = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                String contactId = cursor.getString(indexId);
                int indexDisplayName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name = cursor.getString(indexDisplayName);

                Cursor phones = ctx.getContentResolver().query(Phone.CONTENT_URI, null,
                        Phone.CONTACT_ID + " = " + contactId, null, null);
                UserInfo data = new UserInfo();
                data.setId(contactId);
                data.setName(name);

                //设置拼音首字母-->为了显示排序等
                CharacterParser characterParser=new CharacterParser();
                String pinyin=characterParser.getSelling(name);
                String sortString = pinyin.substring(0, 1).toUpperCase();
                // 正则表达式，判断首字母是否是英文字母
                if (sortString.matches("[A-Z]")) {
                    data.setSortLetters(sortString.toUpperCase());
                } else {
                    data.setSortLetters("#");
                }
                
                //data.setRemark(ContactLogic.CONVER_PHONTO[ContactSqlManager.getIntRandom(4, 0)]);
               //一个人电话可能不止一个，所以可能是一个集合，因此在设置实体类的时候，手机号那里可以设置成一个集合，集合中放置Phone的实体类
                while (phones.moveToNext()) {
                    String phoneNumber = phones.getString(phones.getColumnIndex(Phone.NUMBER));
                    data.setPhoneNum(phoneNumber);
                }
                contacts.add(data);
                phones.close();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }

}
