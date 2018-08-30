package ${packa};

<#list importSet as import>
import ${import};
</#list>

/**
 * 本接口由mybatis_generator_gui生成
 * 自定义持久层代码写在此处
 * Created on ${createDate}
 * @author XiaoBai
 */
public interface ${clazz}Mapper extends BaseDao<${clazz}> {

}