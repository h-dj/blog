package cn.hdj.hdjblog.utils;

import cn.hutool.core.util.NumberUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;


/**
 * @author hdj
 * @version 1.0
 * @date 2019/12/24 下午9:10
 * @description: Bean属性拷贝工具测试
 */
public class BeanUtilTest {

    @Test
    public void testHutoolBeanUtil() throws UnsupportedEncodingException {

//        String s = AES.generateRandomKey();
//
//        String a = AES.encrypt("A", s);
//        System.out.println(a);
//
//
//        SecureUtil.aes(s.getBytes(StandardCharsets.UTF_8));

        BigDecimal add = NumberUtil.add("79211.38", "-9350.01", "-25760.45", "-10017.46");
        System.out.println(add);

    }
}
