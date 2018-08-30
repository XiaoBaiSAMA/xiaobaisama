package ${packa};

import java.util.List;
<#list importSet as import>
import ${import};
</#list>

/**
 * 本service抽象类由mybatis_generator_gui生成
 * 本抽象类包含了常用的自动生成的增删改查方法
 * 继承本类并实现getDao方法，即可实现数据库的基本操作
 * Created on ${createDate}
 * @author XiaoBai
 */
public abstract class BaseService<Bean>{
    public abstract BaseDao<Bean> getDao();

    public Bean insert(Bean bean){
        getDao().insert(bean);
        return bean;
    }

    public int deleteByExample(Bean example){
        return getDao().deleteByExample(example);
    }

    public int deleteByPk(Bean bean){
        return getDao().deleteByPk(bean);
    }

    public int updateByExample(Bean bean,Bean example){
        return getDao().updateByExample(bean,example);
    }

    public int updateByPk(Bean bean){
        return getDao().updateByPk(bean);
    }

    public Bean selectOneByExample(Bean example){
        return getDao().selectOneByExample(example);
    }

    public List<Bean> selectListByExample(Bean example,Page page){
        if(page!=null && page.getPageSize()>0){
            long count = countByExample(example);
            page.setCount(count);
        }
        return getDao().selectListByExample(example,page);
    }

    public long countByExample(Bean example){
        return getDao().countByExample(example);
    }
}