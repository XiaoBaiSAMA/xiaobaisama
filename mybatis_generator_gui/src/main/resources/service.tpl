package ${packa};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<#list importSet as import>
import ${import};
</#list>

/**
 * 本接口由mybatis_generator_gui生成
 * Created on ${createDate}
 * @author XiaoBai
 */
@Service
@Transactional
public class ${clazz}Service extends BaseService<${clazz}>{
    @Autowired
    private ${clazz}Mapper ${obj}Mapper;

    @Override
    public BaseDao<${clazz}> getDao() {
        return ${obj}Mapper;
    }
}
