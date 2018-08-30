package ${packa};

import org.apache.ibatis.annotations.Param;
import java.util.List;
<#list importSet as import>
import ${import};
</#list>

/**
 * 本dao接口由mybatis_generator_gui生成
 * 本接口包含了常用的自动生成的增删改查方法
 * 利用spring反向代理mybatis，xml对应所有子接口
 * Created on ${createDate}
 * @author XiaoBai
 */
public interface BaseDao<Bean> {
    int insert(@Param("bean")Bean bean);

    int deleteByPk(@Param("bean")Bean bean);

    int deleteByExample(@Param("example")Bean example);

    int updateByExample(@Param("bean")Bean bean,@Param("example")Bean example);

    int updateByPk(@Param("bean")Bean bean);

    Bean selectOneByExample(@Param("example")Bean example);

    List<Bean> selectListByExample(@Param("example")Bean example,@Param("page")Page page);

    long countByExample(@Param("example")Bean example);
}