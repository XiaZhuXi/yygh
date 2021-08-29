package com.atguigu.yygh.common.utils;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.result.ResultCodeEnum;

import java.util.HashMap;
import java.util.Map;

public class CheckMD5 {
    public static boolean checkKey(Map<String, Object> parameterMap, String signKey, String checkChar) {
        //获取医院系统的传递的签名，签名MD5加密
        String hospSign = (String) parameterMap.get(checkChar);
        //调用service方法
        String signMD5key = MD5.encrypt(signKey);
        if (!signMD5key.equals(hospSign)) {

            throw new YyghException(ResultCodeEnum.SIGN_ERROR);

        }
        return true;
    }
}
