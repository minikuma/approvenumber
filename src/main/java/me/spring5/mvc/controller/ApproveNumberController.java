package me.spring5.mvc.controller;

import com.google.common.base.Strings;
import lombok.extern.log4j.Log4j;
import me.spring5.mvc.service.BM_COM_GETAPRVNO;
import me.spring5.mvc.vo.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * @ Author: minikuma
 * @ Date: 19/02/07
 * @ Version: 1.0
 * @ Commenct:
 *     1) /appr_no URL Mapping
 *     2) 입력 정보에 대한 필수 값 check
 *     3) 서비스에 필요한 정보 요청
 *        - 승인일자 (20190211)
 *        - 승인시간 (110911)
 *        - 온라인 승인번호 or 오프라인 승인번호
 */
@Controller
@Log4j
public class ApproveNumberController {

    @Autowired
    private BM_COM_GETAPRVNO bm_com_getaprvno;

    @Autowired
    private ConversionService conversionService;

    @PostMapping(value = "/aprv_no", produces = "application/json; charset=UTF-8")
    public @ResponseBody <T> ResponseEntity<T> getApprove (@RequestBody BM_COM_GETAPRVNO_INSTR bm_com_getaprvno_instr) {

        log.debug("INPUT: " + bm_com_getaprvno_instr);

        //Create Output VO
        BM_COM_GETAPRVNO_OUTSTR outStr = conversionService.convert("output", BM_COM_GETAPRVNO_OUTSTR.class);

        if ( (isNullorSpaceCheck(bm_com_getaprvno_instr.getSvc_modu_id()) == 0)
           ||(isInputDualCheck(bm_com_getaprvno_instr.getOnoff_mcht_fg(),bm_com_getaprvno_instr.getMcht_no()))) {
            throw new CustomException("AP_7777", "필수값 미 존재");
        }

        if (isInputSingleCheck(bm_com_getaprvno_instr.getOnoff_mcht_fg())) {
            MCT_MASTER_MST_TPCOM_VS2002_OUTSTR mct_outstr = bm_com_getaprvno.getMchtChk(bm_com_getaprvno_instr.getMbrsh_pgm_id(), bm_com_getaprvno_instr.getMcht_no());
            if (mct_outstr.getMcht_fg().equals("2")) {
                bm_com_getaprvno_instr.setOnoff_mcht_fg(mct_outstr.getMcht_fg());
                log.debug("New onoff_mcht_fg setting: " + bm_com_getaprvno_instr.getOnoff_mcht_fg());
            }
        }

        //승인일시 조회
        DUAL_TPCOM_VS2005_OUTSTR dualList = bm_com_getaprvno.getApproveDateAndTime();
        outStr.setAprv_dy(dualList.getAprv_dy());
        outStr.setAprv_tm(dualList.getAprv_tm());
        log.debug("dualList: + " + dualList.hashCode());

        //온라인 승인번호 조회
        if (bm_com_getaprvno_instr.getOnoff_mcht_fg().equals("2")) {
            APRV_NO_OCBONL_SEQ_VS2001_OUTSTR onList = bm_com_getaprvno.getApproveNumberOnline();
            outStr.setAprv_no(onList.getAprv_no());
            outStr.setRep_aprv_no(onList.getRep_aprv_no());
        }
        //오프라인 승인번호 조회
        else {
            APRV_NO_OCBOFF_SEQ_VS2001_OUTSTR offList = bm_com_getaprvno.getApproveNumberOffline();
            outStr.setAprv_no(offList.getAprv_no());
            outStr.setRep_aprv_no(offList.getRep_aprv_no());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "true");
        jsonObject.put("apprvoeNumber", outStr);
        return new <JSONObject>ResponseEntity(jsonObject, HttpStatus.OK);
    }

    //null or empty check dual
    private boolean isInputDualCheck(String onoff_mcht_fg, String mcht_no) {
        return Strings.isNullOrEmpty(onoff_mcht_fg) && Strings.isNullOrEmpty(mcht_no);
    }

    //null or empty check single
    private boolean isInputSingleCheck(String str) {
        return Strings.isNullOrEmpty(str);
    }

    //null or space check
    private int isNullorSpaceCheck(String str) {
        return str.trim().length();
    }
}
