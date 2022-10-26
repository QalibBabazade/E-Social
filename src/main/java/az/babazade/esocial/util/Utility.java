package az.babazade.esocial.util;

import az.babazade.esocial.entities.User;
import az.babazade.esocial.exception.ExceptionConstant;
import az.babazade.esocial.exception.MyException;
import org.springframework.stereotype.Component;

@Component
public class Utility {

    public void checkUserr(User user) {
        if (user == null) {
            throw new MyException(ExceptionConstant.USER_NOT_FOUND, "User not found!");
        }
    }

    public void checkUser(String username,String password){
        if(username == null || password == null){
            throw new MyException(ExceptionConstant.INVALID_REQUEST_DATA,"Invalid request data!");
        }
    }

    public void checkId(Long id){
        if(id == null){
            throw new MyException(ExceptionConstant.INVALID_REQUEST_DATA,"Invalid request data!");
        }
    }

    public void checkPost(Long userId, String title, String text) {
        if(userId == null || title == null || text == null){
            throw new MyException(ExceptionConstant.INVALID_REQUEST_DATA,"Invalid request data!");
        }
    }

    public void checkComment(Long userId, Long postId, String text) {
        if(userId == null || postId == null || text == null){
            throw new MyException(ExceptionConstant.INVALID_REQUEST_DATA,"Invalid request data!");
        }
    }

    public void checkUpdate(Long id, String text) {
        if(id == null || text == null){
            throw new MyException(ExceptionConstant.INVALID_REQUEST_DATA,"Invalid request data!");
        }
    }
}
