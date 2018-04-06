package wang.crick.business.pharos.aop;

import wang.crick.business.pharos.directory.RestfulErrorCode;
import wang.crick.business.pharos.entity.Restfulesponse;
import wang.crick.business.pharos.exception.RestfulException;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestfulExceptionHandler {

    @ExceptionHandler(RestfulException.class)
    public ResponseEntity<Restfulesponse> handler(HttpServletRequest request, RestfulException e) {
        try {
            return new ResponseEntity<>(Restfulesponse.error(e.getErrorCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WxErrorException.class)
    public ResponseEntity<Restfulesponse> handler(HttpServletRequest request, WxErrorException e) {
        return new ResponseEntity<>(Restfulesponse.error(RestfulErrorCode.WECHAT_ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
