package ${packa};

<#list importSet as import>
import ${import};
</#list>

/**
 * 本结果类由mybatis_generator_gui生成
 * Created on ${createDate}
 * @author XiaoBai
 */
public class Result<T> {
    private int code;
    private String message;
    private T data;
    private Page page;

    private Result() {}

    public static Result suc(String message) {
        Result result = new Result();
        result.code = Code.SUCCESS;
        result.message = message;
        return result;
    }

    public static Result err(int code,String message) {
        Result result = new Result();
        result.code = code;
        result.message = message;
        return result;
    }

    public static <T> Result data(T t,Page page) {
        Result result = new Result();
        result.code = Code.SUCCESS;
        result.message = "获取成功";
        result.data = t;
        result.page = page;
        return result;
    }

    public static <T> Result data(T t) {
        Result result = new Result();
        result.code = Code.SUCCESS;
        result.message = "获取成功";
        result.data = t;
        return result;
    }

    public MyException parseMyException(){
        return new MyException(code,message);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Page getPage() {
        return page;
    }
}