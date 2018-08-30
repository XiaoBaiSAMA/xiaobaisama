package ${packa};

/**
 * 本异常类由mybatis_generator_gui生成
 * Created on ${createDate}
 * @author XiaoBai
 */
public class MyException extends RuntimeException {
    private int code;
    private String message;

    public MyException(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public Result parseResult(){
        return Result.err(code,message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
