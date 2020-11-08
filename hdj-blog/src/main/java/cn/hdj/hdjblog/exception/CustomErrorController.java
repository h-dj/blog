package cn.hdj.hdjblog.exception;


import cn.hdj.hdjblog.constaint.enums.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * @author hdj
 */
@Slf4j
@Controller
public class CustomErrorController extends BasicErrorController {

    public CustomErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes, new ErrorProperties());
    }


    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);

        Map<String, Object> model = Collections
                .unmodifiableMap(getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML)));

        log.error("error "+ model);
        if(status == HttpStatus.NOT_FOUND){
           throw new MyException(ResponseCodeEnum.PATH_NOT_FOUND);
        }else {
            throw new MyException(ResponseCodeEnum.UNKNOWN);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);

        Map<String, Object> body = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
        log.error("error "+ body);
        if(status == HttpStatus.NOT_FOUND){
            throw new MyException(ResponseCodeEnum.PATH_NOT_FOUND);
        }else {
            throw new MyException(ResponseCodeEnum.UNKNOWN);
        }
    }


}
