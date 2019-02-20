package me.spring5.mvc.mapper;

import me.spring5.mvc.vo.APRV_NO_OCBOFF_SEQ_VS2001_OUTSTR;
import me.spring5.mvc.vo.APRV_NO_OCBONL_SEQ_VS2001_OUTSTR;
import me.spring5.mvc.vo.DUAL_TPCOM_VS2005_OUTSTR;
import me.spring5.mvc.vo.MCT_MASTER_MST_TPCOM_VS2002_OUTSTR;
import org.apache.ibatis.annotations.Param;

/***
 * @ Author: minikuma
 * @ Date: 19/02/07
 * @ Version: 1.0
 * @ Commenct:
 *     myBatis SQL interface
 */

public interface ApproveNumberMapper {
    //가맹점 검증 조회
    MCT_MASTER_MST_TPCOM_VS2002_OUTSTR getMchtChk(@Param("mbrsh_pgm_id") String mbrsh_pgm_id, @Param("mcht_no") String mcht_no) throws Exception;

    //승인일시 조회
    DUAL_TPCOM_VS2005_OUTSTR getApproveDateAndTime() throws Exception;

    //온라인 승인번호 조회
    void getApproveCurrentOnline();
    APRV_NO_OCBONL_SEQ_VS2001_OUTSTR getApproveNumberOnline() throws Exception;

    //오프라인가맹점 승인번호 조회
    void getApproveCurrentOffline();
    APRV_NO_OCBOFF_SEQ_VS2001_OUTSTR getApproveNumberOffline() throws Exception;
}
