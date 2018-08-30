package ${packa};

<#list importSet as import>
import ${import};
</#list>

/**
 * 本实体类由mybatis_generator_gui生成
 * Created on ${createDate}
 * @author XiaoBai
 */
public class ${clazz} {
    <#list columnList as col>
    private ${col.fmt_Type} ${col.fmt_name};
    </#list>

    public ${clazz}(){}

    <#list columnList as col><#if col.pk>
    public ${clazz}(${col.fmt_Type} ${col.fmt_name}){
        this.${col.fmt_name} = ${col.fmt_name};
    }
    </#if></#list>

    <#list columnList as col>
    public ${col.fmt_Type} get${col.fmt_Name}(){
        return ${col.fmt_name};
    }

    public void set${col.fmt_Name}(${col.fmt_Type} ${col.fmt_name}){
        this.${col.fmt_name} = ${col.fmt_name};
    }

    </#list>
    /*下方为自定义代码区*/
}