package com.ecloud.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class FormatUtils {
	
	
	public static double formatDouble(double value,int scale){
		return new BigDecimal(value).setScale(scale, RoundingMode.UP).doubleValue();
	}
	
	public static double formatDouble(String value,int scale){
		return new BigDecimal(value).setScale(scale, RoundingMode.UP).doubleValue();
	}

}
