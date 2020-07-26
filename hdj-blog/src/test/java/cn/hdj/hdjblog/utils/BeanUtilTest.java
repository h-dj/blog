package cn.hdj.hdjblog.utils;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.AES;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;


/**
 * @author hdj
 * @version 1.0
 * @date 2019/12/24 下午9:10
 * @description: Bean属性拷贝工具测试
 */
public class BeanUtilTest {

    @Test
    public void testHutoolBeanUtil() throws UnsupportedEncodingException {

        String s = AES.generateRandomKey();

        String a = AES.encrypt("A", s);
        System.out.println(a);


        SecureUtil.aes(s.getBytes(StandardCharsets.UTF_8));
    }
}
