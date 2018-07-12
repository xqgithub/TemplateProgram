package example.com.templateprogram.runtimepermissions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Administrator on 2018/2/26 0026.
 */

public class RegexUtils {
    /**
     * 验证手机号正则
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isPhoneNum(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
