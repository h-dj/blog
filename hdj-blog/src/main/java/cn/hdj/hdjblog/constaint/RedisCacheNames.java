package cn.hdj.hdjblog.constaint;

/**
 * @author hdj
 * @Description: redis 缓存名称
 * @date 10/5/19
 */
public interface RedisCacheNames {

    String PROFIX = "BLOG_CACHE:";

    /**
     * 文章缓存空间名称
     */
    String ARTICLE = PROFIX + "ARTICLE";

    /**
     * 标签列表
     */
    String TAG = PROFIX + "TAG";

    /**
     * 分类列表
     */
    String CATEGORY = PROFIX + "CATEGORY";

    /**
     * 归档
     */
    String ARCHIVE = PROFIX + "ARCHIVE";


}
