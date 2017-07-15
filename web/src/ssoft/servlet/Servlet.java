package ssoft.servlet;

import org.apache.log4j.Logger;
import ssoft.utils.CryptUtils;
import ssoft.utils.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mechrevo on 2017/4/18 0018.
 */
public abstract class Servlet extends HttpServlet {

    public void setEncoding(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        // 设置请求的编码格式
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Logger logger =Logger.getLogger(this.getClass());
        logger.info("请求url:"+request.getRequestURI()+",请求参数:"+getRequestParams(request));
    }

    public static String getRequestParams(HttpServletRequest request) {
        String paramString = "";
        //获取参数map
        Map<String, String[]> params = request.getParameterMap();
        if (params == null) {
            return paramString;
        }
        //获取全部参数名
        Set<String> paramNames = params.keySet();
        int index = 0;
        //遍历参数名
        for (String paramName : paramNames) {
            index++;
            paramString += paramName + "=[";
            String[] values = params.get(paramName);
            values = hideValues(paramName, values);
            if (values.length == 1) {
                if (index == paramNames.size()) {
                    paramString += values[0] + "]";
                    continue;
                }
                paramString += values[0] + "],";
                continue;
            }

            for (int i = 0; i < values.length; i++) {
                if (i < values.length - 1) {
                    paramString += values[i] + ",";
                    continue;
                }
                if (index == paramNames.size()) {
                    paramString += values[i] + "]";
                    continue;
                }
                paramString += values[i] + "],";
            }
        }
        return paramString;
    }


    private static final List<String> paramNames = Arrays.asList("password", "pwd", "pass","oldPass","oldpass","oldPassword","oldpassword","newPass","newPassword","newpass","newpassword");

    private static String[] hideValues(String paramName, String[] values) {
        if (paramNames.contains(paramName)) {
            for (int i = 0; i < values.length; i++) {
                String value = CryptUtils.generateDigest(values[i]);
                value = StringUtils.hide(value, 0, 0);
                values[i] = value;
            }
        }
        if ("photo".equals(paramName)){
            values[0]="picture";
        }
        return values;
    }

}
