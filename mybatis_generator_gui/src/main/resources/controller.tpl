package ${packa};

<#list importSet as import>
import ${import};
</#list>

import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 本类由mybatis_generator_gui生成
 * Created on ${createDate}
 * @author XiaoBai
 */
@Validated
@RestController
@RequestMapping("api/${obj}")
public class ${clazz}Controller{
    @Autowired
    private ${clazz}Service ${obj}Service;

    @GetMapping("get")
    public Result get(${pk.fmt_Type} ${pk.fmt_name}){
        ${clazz} ${obj} = ${obj}Service.selectOneByExample(new ${clazz}(${pk.fmt_name}));
        return Result.data(${obj});
    }

    @GetMapping("get/list")
    public Result getList(Integer pageNum,Integer pageSize) {
        Page page = Page.getPage(pageNum,pageSize);
        List<${clazz}> ${obj}List = ${obj}Service.selectListByExample(null,page);
        return Result.data(${obj}List,page);
    }

    @PostMapping("add")
    public Result add(<#list columnList as col>${col.fmt_Type} ${col.fmt_name}<#if col_has_next>,</#if></#list>) {
        ${clazz} ${obj} = new ${clazz}();
        <#list columnList as col>
        ${obj}.set${col.fmt_Name}(${col.fmt_name});
        </#list>
        ${obj}Service.insert(${obj});
        return Result.suc("新增成功");
    }

    @PostMapping("update")
    public Result update(<#list columnList as col>${col.fmt_Type} ${col.fmt_name}<#if col_has_next>,</#if></#list>) {
        ${clazz} ${obj} = new ${clazz}();
        <#list columnList as col>
        ${obj}.set${col.fmt_Name}(${col.fmt_name});
        </#list>
        ${obj}Service.updateByPk(${obj});
        return Result.suc("修改成功");
    }

    @PostMapping("delete")
    public Result delete(${pk.fmt_Type} ${pk.fmt_name}) {
        ${obj}Service.deleteByPk(new ${clazz}(${pk.fmt_name}));
        return Result.suc("删除成功");
    }
}