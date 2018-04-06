package wang.crick.business.pharos.entity;

/**
 * controller 通用返回值
 */
public class Restfulesponse<T> {

    private static final Integer SUCCESS_CODE = 10000;
    private static final Integer ERROR_CODE = 10001;

    private T data;
    private Integer code;
    private String message;

    public static <T> Restfulesponse<T> success(T data){
        Restfulesponse<T> response = new Restfulesponse<>();
        response.setCode(SUCCESS_CODE);
        response.setData(data);
        return response;
    }

    public static <T> Restfulesponse<T> success(){
        Restfulesponse<T> response = new Restfulesponse<>();
        response.setCode(SUCCESS_CODE);
        return response;
    }

    public static <T> Restfulesponse<T> error(String message){
        Restfulesponse<T> response = new Restfulesponse<>();
        response.setCode(ERROR_CODE);
        response.setMessage(message);
        return response;
    }

    public static <T> Restfulesponse<T> error(Integer errorCode, String message){
        Restfulesponse<T> response = new Restfulesponse<>();
        response.setCode(errorCode);
        response.setMessage(message);
        return response;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}