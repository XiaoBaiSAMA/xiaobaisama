package ${packa};

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 本分页类由mybatis_generator_gui生成
 * Created on ${createDate}
 * @author XiaoBai
 */
public class Page {
    @JsonIgnore
    private Integer start;
    private Integer pageNum;
    private Integer pageSize;
    private Long count;

    public static Page getPage(Integer pageNum,Integer pageSize) {
        if (pageNum == null || pageSize == null) return null;
        if (pageNum < 0 || pageSize <= 0) return null;
        return new Page(pageNum,pageSize);
    }

    private Page(Integer pageNum,Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Integer getStart() {
        start = pageNum * pageSize;
        return start;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}