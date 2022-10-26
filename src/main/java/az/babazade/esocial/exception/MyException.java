package az.babazade.esocial.exception;

public class MyException extends RuntimeException{

    private Integer code;

    public MyException(){

    }

    public MyException(Integer code,String message){
        super(message);
        this.code = code;
    }

    public Integer getCode(){
        return code;
    }
}
