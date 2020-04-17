package cn.hdj.hdjblog.utils;

import cn.hdj.hdjblog.model.dto.UserDetailDTO;
import cn.hutool.core.bean.BeanUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;


/**
 * @author hdj
 * @version 1.0
 * @date 2019/12/24 下午9:10
 * @description: Bean属性拷贝工具测试
 */
@RunWith(MockitoJUnitRunner.class)
public class BeanUtilTest {

    @Test
    public void testHutoolBeanUtil() {
        Set<String> objects = new HashSet<>();
        objects.add("123");

        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setPermissions(objects);
        userDetailDTO.setId(123L);

        HashMap<Object, Object> map = new HashMap<>();

        BeanUtil.copyProperties(userDetailDTO, map);

        System.out.println(map);


        List<UserDetailDTO> list=new ArrayList<>();
        list.add(userDetailDTO);


        List<HashMap<Object, Object>> mapList=new ArrayList<>();

        BeanUtil.copyProperties(userDetailDTO, mapList);

        System.out.println(mapList);

    }
}
