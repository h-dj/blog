package cn.hdj.hdjblog.model.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hdj
 * @date 2019/7/14 21:09
 * @Version 1.0
 * @Description:
 */
@Data
public class PageVO implements Serializable {

    private static final long serialVersionUID = 9070685683925007913L;
    /**
     * 总记录数
     */
    private Integer totalCount;
    /**
     * 每页记录数
     */
    private Integer pageSize;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 当前页数
     */
    private Integer currPage;
    /**
     * 列表数据
     */
    private List<?> list;

    public PageVO() {
    }

    /**
     * 分页
     *
     * @param list       列表数据
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currPage   当前页数
     */
    private PageVO(List<?> list, long totalCount, long pageSize, long currPage) {
        this.list = list;
        this.totalCount = (int)totalCount;
        this.pageSize = (int)pageSize;
        this.currPage = (int)currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    /**
     * 分页
     */
    private PageVO(IPage<?> page) {
        this.list = page.getRecords();
        this.totalCount = (int)page.getTotal();
        this.pageSize =(int) page.getSize();
        this.currPage = (int)page.getCurrent();
        this.totalPage = (int)page.getPages();
    }

    public static PageVO build(IPage<?> page) {
        return new PageVO(page);
    }

    public static PageVO build(List<?> list, long totalCount, long pageSize, long currPage) {
        return new PageVO(list, totalCount, pageSize, currPage);
    }

}
