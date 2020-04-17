package cn.hdj.hdjblog.service;

import cn.hdj.hdjblog.HdjBlogApplicationTests;
import cn.hdj.hdjblog.entity.CategoryDO;
import cn.hdj.hdjblog.entity.TagArticleDO;
import cn.hdj.hdjblog.entity.TagDO;
import cn.hdj.hdjblog.model.params.ArticleForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * @author hdj
 * @Description:
 * @date 10/6/19
 */
@SuppressWarnings({"-Xlint:unchecked"})
public class ArticleServiceTest extends HdjBlogApplicationTests {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;


    @Autowired
    private TagArticleService tagArticleService;

    @Test
    public void insertTagArticle() {

        TagArticleDO articleDO = new TagArticleDO();
        articleDO.setArticleId(4L);
        articleDO.setTagId(1L);
        tagArticleService.save(articleDO);
    }

    @Test
    public void test() {


        //添加分类
        CategoryDO categoryDO = new CategoryDO();
        categoryDO.setCategoryName("后台");
        categoryService.save(categoryDO);


        //添加标签
        TagDO tagDO = new TagDO();
        tagDO.setTagName("java");
        tagService.save(tagDO);


        //添加文章
        ArticleForm form = new ArticleForm();
        form.setTagList(Arrays.asList(tagDO));
        form.setCategoryId(categoryDO.getId());
        form.setContent("测试");
        form.setTitle("标题");
        articleService.saveArticle(form);
    }

    @Test
    public void deleteArticle() {

        articleService.deleteBatch(Arrays.asList(6L));
    }
}
