package cn.hdj.hdjblog.utils;

import cn.hdj.hdjblog.util.MyWebUtils;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author hdj
 * @version 1.0
 * @date 10/31/19 9:08 AM
 * @description:
 */
public class MyWebUtilsTest {

    @Test
    public void test() throws Exception {
        File file = ResourceUtils.getFile("classpath:ip2region.db");
    }

    @Test
    public void getIp() throws FileNotFoundException {
        String cityInfo = MyWebUtils.getCityInfo("212.64.62.174");
        System.out.println(cityInfo);

    }
}
