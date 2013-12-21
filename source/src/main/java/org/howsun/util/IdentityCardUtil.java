/**
 * 版本修订记录
 * 创建：2013-11-27
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.util;

import java.text.ParseException;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2013-11-27
 * 
 */
public class IdentityCardUtil {

	private static final String IDENTITY_NO_REGEX = "([\\d]{15})|([\\d]{17}(\\d|[xX]))";
    private static final String DATE_REGEX = "^((\\d{2}(([02468][048])|([13579][26]))((((0?[13578])|(1[02]))((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))((0?[1-9])|([1-2][0-9])|(30)))|(0?2((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))((((0?[13578])|(1[02]))((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))((0?[1-9])|([1-2][0-9])|(30)))|(0?2((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
    private static char[] VALIDATE_CODE_ARR = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
    private static int[] POWSER_ARR = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
    private static int[] AREA_CODE_ARR = new int[100];
 
    static {
        for (int i = 0; i < 100; i++) {
            AREA_CODE_ARR[i] = 0;
        }
        AREA_CODE_ARR[11] = 1;// 北京
        AREA_CODE_ARR[12] = 1;// 天津
        AREA_CODE_ARR[13] = 1;// 河北
        AREA_CODE_ARR[14] = 1;// 山西
        AREA_CODE_ARR[15] = 1;// 内蒙古
        AREA_CODE_ARR[21] = 1;// 辽宁
        AREA_CODE_ARR[22] = 1;// 吉林
        AREA_CODE_ARR[23] = 1;// 黑龙江
        AREA_CODE_ARR[31] = 1;// 上海
        AREA_CODE_ARR[32] = 1;// 江苏
        AREA_CODE_ARR[33] = 1;// 浙江
        AREA_CODE_ARR[34] = 1;// 安徽
        AREA_CODE_ARR[35] = 1;// 福建
        AREA_CODE_ARR[36] = 1;// 江西
        AREA_CODE_ARR[37] = 1;// 山东
        AREA_CODE_ARR[41] = 1;// 河南
        AREA_CODE_ARR[42] = 1;// 湖北
        AREA_CODE_ARR[43] = 1;// 湖南
        AREA_CODE_ARR[44] = 1;// 广东
        AREA_CODE_ARR[45] = 1;// 广西
        AREA_CODE_ARR[46] = 1;// 海南
        AREA_CODE_ARR[50] = 1;// 重庆
        AREA_CODE_ARR[51] = 1;// 四川
        AREA_CODE_ARR[52] = 1;// 贵州
        AREA_CODE_ARR[53] = 1;// 云南
        AREA_CODE_ARR[54] = 1;// 西藏
        AREA_CODE_ARR[61] = 1;// 陕西
        AREA_CODE_ARR[62] = 1;// 甘肃
        AREA_CODE_ARR[63] = 1;// 青海
        AREA_CODE_ARR[64] = 1;// 宁夏
        AREA_CODE_ARR[65] = 1;// 新疆
        AREA_CODE_ARR[71] = 1;// 台湾
        AREA_CODE_ARR[81] = 1;// 香港
        AREA_CODE_ARR[82] = 1;// 澳门
        AREA_CODE_ARR[91] = 1;// 国外
    }
 
    /**
     * 功能：身份证的有效验证
     * 
     * @param identityNo
     * @return true/false
     */
    public static boolean validate(String identityNo) {
        if (identityNo == null) {
            return false;
        }
        if (!identityNo.matches(IDENTITY_NO_REGEX)) {
            return false;
        }
        if (AREA_CODE_ARR[Integer.parseInt(identityNo.substring(0, 2))] == 0) {
            return false;
        }
        String date = identityNo.length() == 15 ? "19" + identityNo.substring(6, 12) : identityNo.substring(6, 14);
        if (!date.matches(DATE_REGEX)) {
            return false;
        }
        if (identityNo.length() == 15) {
            return true;
        }
        int checksum = 0;
        for (int i = 0; i < 17; i++) {
            checksum = checksum + (identityNo.charAt(i) - 48) * POWSER_ARR[i];
        }
        char verifyCode = VALIDATE_CODE_ARR[checksum % 11];
        if (identityNo.charAt(17) != verifyCode) {
            return verifyCode == 'X' ? identityNo.charAt(17) == 'x' : false;
        }
        return true;
    }
 
    public static char calculateCheckNum(char[] out) {
        int checksum = 0;
        for (int i = 0; i < 17; i++) {
            checksum = checksum + (out[i] - 48) * POWSER_ARR[i];
        }
        return VALIDATE_CODE_ARR[checksum % 11];
    }
 
    public static void main(String[] args) throws ParseException {
        testValid("210102820826411");
        testValid("21010219820826411");
        testValid("210102198208264114");
        testValid("500113198606245216");
        testValid("360428197610043158");
    }
 
    private static void testValid(String c) {
        System.out.println(c + ":" + validate(c));
    }
}
