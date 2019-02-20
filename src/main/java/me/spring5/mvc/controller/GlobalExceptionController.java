package me.spring5.mvc.controller;

import lombok.extern.log4j.Log4j;
import me.spring5.mvc.vo.BM_COM_GETAPRVNO_OUTSTR;
import me.spring5.mvc.vo.CustomException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * @ Author: minikuma
 * @ Date: 19/02/07
 * @ Version: 1.0
 * @ Commenct:
 *     1) Controller에서 발생한 Exception 처리
 *        http Header response setting: AP_XXXX
 */
@ControllerAdvice
@ResponseBody
@Log4j
public class GlobalExceptionController {
    @Autowired
    private ConversionService conversionService;

    @ExceptionHandler(CustomException.class)
    public <T> ResponseEntity<T> handleCustomGenericException(CustomException e) {
        log.error("Biz. Error: ans_cd: " + e.getAns_cd() + ", ans_msg: " + e.getAns_msg());

        //Create Output VO
        BM_COM_GETAPRVNO_OUTSTR outStr = conversionService.convert("output", BM_COM_GETAPRVNO_OUTSTR.class);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","false");
        jsonObject.put("apprvoeNumber", outStr);

        JSONObject res = new JSONObject();
        res.put("ans_cd", e.getAns_cd());
        res.put("ans_msg", e.getAns_msg());
        jsonObject.put("error", res);

        return new <JSONObject>ResponseEntity(jsonObject, HttpStatus.OK);
    }
}
