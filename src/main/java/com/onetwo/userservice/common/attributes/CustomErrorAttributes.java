package com.onetwo.userservice.common.attributes;

import com.onetwo.userservice.common.exceptions.TokenValidationException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        Throwable exception = (Throwable) webRequest.getAttribute("TokenValidationException", RequestAttributes.SCOPE_REQUEST);

        if (exception instanceof TokenValidationException) {

            TokenValidationException responseException = (TokenValidationException) exception;

            // Exception JSON 설정
            errorAttributes.remove("timestamp");
            errorAttributes.remove("path");
            errorAttributes.remove("error");
            errorAttributes.remove("exception");
            errorAttributes.remove("trace");

            errorAttributes.put("status", ResponseEntity.badRequest());
            errorAttributes.put("message", responseException.getMessage());
        }

        return errorAttributes;
    }

}
