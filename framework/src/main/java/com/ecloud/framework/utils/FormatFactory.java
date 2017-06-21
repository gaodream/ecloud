package com.ecloud.framework.utils;

/**
 * date:2013-06-26
 * @author gaogao
 */
public class FormatFactory {
    /**
    *@param number要转换的数值 
    *@desc 完成小数的中文转换
    */
	public static String  NumberToChineseName(String number) {
		
		String result = "";

		String temp =  number;

		if ( number.startsWith("0")) {

			temp = String.valueOf(Integer.parseInt( number));
		}

		StringBuffer retStrBuf = new StringBuffer();// 用来拼接数字串

		int dot = 0; // 小数点位置
		int ivalue = 0;// 保存每一个位上的数
		int len = 0;// 保存数字的长度
		for (int i = 0; i < temp.length(); i++) {
			System.out.print(temp.substring(i, i + 1) + " ");
		}

		System.out.println();
		String num[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[] = { "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };
		len = temp.length();
		if (temp == null || "".equals(temp) || temp.length() <= 0) {
			System.out.println("Input is null");
		} else {
			//(==-1)说明
			dot = temp.indexOf(".");
			if (dot == -1) {
				int i = 0;
				// len-1这个注意,i在前面初始化为0
				for (i = 0; i < len - 1; i++) {
					ivalue = Integer.parseInt(temp.substring(i, i + 1));
					retStrBuf.append(num[ivalue] + (unit[(len - i - 1) % 8]));
				}
				// 单独取最后一位数
				ivalue = Integer.parseInt(String.valueOf(temp.substring(i,i + 1)));

				// 最后一位为零,将不作处理
				if (ivalue != 0) {
					retStrBuf.append(num[ivalue]);
				}
				retStrBuf.append("元整");
				System.out.println(retStrBuf.toString());

			} else {

				String tmpStr1 = temp.substring(0, dot);

				len = tmpStr1.length();

				int i = 0;

				// len-1这个注意,i在前面初始化为0
				for (i = 0; i < len - 1; i++) {

					ivalue = Integer.parseInt(temp.substring(i, i + 1));

					retStrBuf.append(num[ivalue] + (unit[(len - i - 1) % 8]));

				}

				// 单独取最后一位数
				ivalue = Integer.parseInt(String.valueOf(temp.substring(i,i + 1)));

				// 最后一位为零,将不作处理
				if (ivalue != 0) {
					retStrBuf.append(num[ivalue]);
				}

				//跟整数处理不一样的部分
				if (dot < temp.length() - 1) {
					retStrBuf.append("点");
					//从小数点后一位开始取
					String tmpStr2 = temp.substring(dot + 1);
					len = tmpStr2.length();
					//这里不需要len-1
					for (i = 0; i < len; i++) {

						ivalue = Integer.parseInt(tmpStr2.substring(i, i + 1));

						retStrBuf.append(num[ivalue]);
					}
				}
				retStrBuf.append("元整");
				result = retStrBuf.toString();

			}

		}
		return result;

	}
	
	public static void main(String[] args){
		
	}

}
