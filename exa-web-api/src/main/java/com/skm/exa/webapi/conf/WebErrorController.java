package com.skm.exa.webapi.conf;

import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.exception.BizException;
import com.skm.exa.common.object.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dhc
 * 2019-03-10 13:32
 */
@ControllerAdvice
@RestController
public class WebErrorController implements ErrorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebErrorController.class);
    private static final String ERROR_PATH = "/error";

    @Autowired
    ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(ERROR_PATH)
    public Result error(HttpServletRequest request, HttpServletResponse response) {
        WebRequest req = new ServletWebRequest(request);
        Map<String, Object> attrs = errorAttributes.getErrorAttributes(req, false);

        if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
            return Result.error(404, "Not found!");
        }
        return Result.error(response.getStatus(), "").setContent(attrs);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Result> bizException(ValidationException e) {
        return new ResponseEntity<>(Result.error(Msg.E40011.code(), e.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();

        List<String> list = new ArrayList<>();
        for (ObjectError error : result.getAllErrors()) {
            FieldError fe = (FieldError) error;
            list.add(String.format("【%s.%s】%s", fe.getObjectName(), fe.getField(), fe.getDefaultMessage()));
        }
        return new ResponseEntity<>(Result.error(Msg.E40011).setMessages(list), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Result> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        Result result = Result.error(Msg.E40012.code(), e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Result> bizException(MaxUploadSizeExceededException e) {
        Result result = Result.error(Msg.E40013.code(), e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<Result> bizException(BizException e) {
        return new ResponseEntity<>(e.toResult(), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> exception(Exception e) {
        LOGGER.error(">>> 未知异常信息：" + e.getMessage(), e);
        Result<Object> result = Result.error(Msg.E40000).setContent(e.getClass().getName());
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
