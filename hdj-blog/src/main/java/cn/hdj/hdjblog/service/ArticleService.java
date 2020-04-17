package cn.hdj.hdjblog.service;

import cn.hdj.hdjblog.entity.ArticleDO;
import cn.hdj.hdjblog.model.params.ArticleForm;
import cn.hdj.hdjblog.model.vo.TimeLineVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 文章信息表 服务类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
public interface ArticleService extends IService<ArticleDO> {

    /**
     * 保存文章
     *
     * @param form
     */
    void saveArticle(ArticleForm form);

    /**
     * 修改文章
     *
     * @param id
     * @param form
     */
    void editArticle(Long id, ArticleForm form);

    /**
     * 批量删除
     *
     * @param articleIds
     */
    void deleteBatch(List<Long> articleIds);

    /**
     * 文章详情
     *
     * @return
     */
    ArticleForm detail(String slug);

    /**
     * 归档
     *
     * @return
     */
    List<TimeLineVO> archive(String tag);

}
