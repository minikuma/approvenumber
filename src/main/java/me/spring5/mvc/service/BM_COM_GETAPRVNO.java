package me.spring5.mvc.service;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import me.spring5.mvc.mapper.ApproveNumberMapper;
import me.spring5.mvc.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @ Author: minikuma
 * @ Date: 19/02/07
 * @ Version: 1.0
 * @ Commenct:
 *     1) myBatis mapper를 통해 실제 DB 값을 Select, Update
 */

@Log4j
@Service
public class BM_COM_GETAPRVNO {

    @Setter(onMethod_ = {@Autowired})
    private ApproveNumberMapper approveNumberMapper;
    /**
     * @param mbrsh_pgm_id
     * @param mcht_no
     * @return MCT_MASTER_MST_TPCOM_VS2002_OUTSTR
     * @author minikuma
     * @version 1.0
     * @comment 가맹점 조회
     */
    public MCT_MASTER_MST_TPCOM_VS2002_OUTSTR getMchtChk(String mbrsh_pgm_id, String mcht_no) {
        log.info("가맹점 조회 pass");
        try {
            return approveNumberMapper.getMchtChk(mbrsh_pgm_id, mcht_no);
        }
        catch (NullPointerException ne) {
            throw new CustomException("AP_8830", "가맹점 미 등록");
        }
        catch (Exception e) {
            log.debug(e.getClass() + e.getMessage());
            throw new CustomException("AP_9080", "DB Fail");
        }
    }
    /**
     * @return DUAL_TPCOM_VS2005_OUTSTR
     * @author minikuma
     * @version 1.0
     * @comment 현재 승인일자, 승인시간 조회
     */
    public DUAL_TPCOM_VS2005_OUTSTR getApproveDateAndTime() {
        log.info("승인일시 조회 pass");
        try {
            return approveNumberMapper.getApproveDateAndTime();
        }
        catch (Exception e) {
            //e.printStackTrace();
            log.debug(e.getClass() + e.getMessage());
            throw new CustomException("AP_9080", "DB Fail");
        }
    }

    /**
     * @return APRV_NO_OCBOFF_SEQ_VS2001_OUTSTR
     * @author minikuma
     * @version 1.0
     * @comment 오프라인 가맹점 승인번호 조회(채번-ex)Fxxxxxxxx)
     */
    public APRV_NO_OCBOFF_SEQ_VS2001_OUTSTR getApproveNumberOffline() {
        log.info("오프라인가맹점 승인번호 pass");
        try {
            approveNumberMapper.getApproveCurrentOffline();
            APRV_NO_OCBOFF_SEQ_VS2001_OUTSTR offLine = approveNumberMapper.getApproveNumberOffline();
            StringBuilder sb = new StringBuilder(offLine.getAprv_no());
            sb.setCharAt(0, 'F');
            offLine.setAprv_no(sb.toString());
            offLine.setRep_aprv_no(sb.toString());
            return offLine;
        }
        catch (Exception e) {
            //e.printStackTrace();
            log.debug(e.getClass() + e.getMessage());
            throw new CustomException("AP_9080", "DB Fail");
        }
    }
    /**
     * @return APRV_NO_OCBONL_SEQ_VS2001_OUTSTR
     * @author minikuma
     * @version 1.0
     * @comment 온라인 가맹점 승인번호 조회 (채번-ex)69xxxxxxx)
     */
    public APRV_NO_OCBONL_SEQ_VS2001_OUTSTR getApproveNumberOnline() {
        log.info("온라인가맹점 승인번호 pass");
        try {
            approveNumberMapper.getApproveCurrentOnline();
            return approveNumberMapper.getApproveNumberOnline();
        }
        catch (Exception e) {
            //e.printStackTrace();
            log.debug(e.getClass() + e.getMessage());
            throw new CustomException("AP_9080", "DB Fail");
        }
    }
}
