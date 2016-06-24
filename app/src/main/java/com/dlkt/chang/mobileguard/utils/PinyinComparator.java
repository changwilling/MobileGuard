package com.dlkt.chang.mobileguard.utils;


import com.dlkt.chang.mobileguard.domain.UserInfo;

import java.util.Comparator;

/**
 *按照拼音比较，获取先后顺序的类
 */
public class PinyinComparator implements Comparator<UserInfo> {
	public int compare(UserInfo o1, UserInfo o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}