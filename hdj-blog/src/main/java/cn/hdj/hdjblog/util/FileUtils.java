package cn.hdj.hdjblog.util;



import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author hdj
 * @version 1.0
 * @date 12/8/19 11:49 AM
 * @description: 文件工具
 */
public class FileUtils {

    private static List<String> pictureTypeList = Arrays.asList(
            "png",
            "jpeg",
            "jpg",
            "gif"
    );

    /**
     * 创建路径
     *
     * @param fileName
     * @return
     */
    public static String makeDirPath(String fileName) {
        Objects.requireNonNull(fileName);
        //通过文件名来算出一级目录和二级目录
        int hashCode = fileName.hashCode();
        int dir1 = hashCode & 0xf;
        int dir2 = (hashCode & 0xf0) >> 4;
        String dir = String.format("%s%s%s%s", dir1, File.separator, dir2, File.separator);
        //返回全路径
        return dir;
    }

    /**
     * 生成文件名
     *
     * @param fileSuffix
     * @return
     */
    public static String genFileName(String fileSuffix) {
        return UUID.randomUUID().toString() + "_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "." + fileSuffix;
    }


    /**
     * 获取文件大小
     *
     * @param size
     * @return
     */
    public static String genFileSize(long size) {

        DecimalFormat formater = new DecimalFormat("#.00");
        if (size < 1024) {
            return size + "bytes";
        }
        if (size < 1024 * 1024) {
            float kbsize = size / 1024f;
            return formater.format(kbsize) + "KB";
        }
        if (size < 1024 * 1024 * 1024) {
            float mbsize = size / 1024f / 1024f;
            return formater.format(mbsize) + "MB";
        }
        float gbsize = size / 1024f / 1024f / 1024f;
        return formater.format(gbsize) + "GB";
    }

    /**
     * 判断文件类型
     *
     * @param fileSuffix
     * @return
     */
    public static boolean isPicture(String fileSuffix) {
        return pictureTypeList.contains(fileSuffix.toLowerCase());
    }

    /**
     * 删除文件
     *
     * @param path
     */
    public static void delete(Path path) {
        if (path != null && path.toFile().exists()) {
            path.toFile().delete();
        }

    }
}
