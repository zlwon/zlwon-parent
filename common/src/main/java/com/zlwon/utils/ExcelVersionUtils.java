package com.zlwon.utils;

/**
 * 判断excel版本工具类
 * @author yangy
 *
 */

public class ExcelVersionUtils {

	/**
	 * 判断导入excel版本为2003，即xls
	 * @param filePath
	 * @return
	 */
	public static boolean isExcel2003(String filePath){
        return filePath.matches("^.+\\.(?i)(xls)$");
    }
	
	/**
	 * 判断导入excel版本为2007，即xlsx
	 * @param filePath
	 * @return
	 */
	public static boolean isExcel2007(String filePath){
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
