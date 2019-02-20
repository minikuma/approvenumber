## Approve Number 구현   
### Requirement  
C 기반 Framework를 Java 기반 Spirng으로 Consersion 하여 구현한다. 요청 시 해당 서비스는 승인일자, 승인번호, 승인시간, 대표승인번호(=승인번호)의 값을 응답한다. 승인번호는 Sequence하게 증가하는 값(mariadb 기준)이며, 가맹점이 온라인인 경우는 승인번호가 69XXXXXXX, 오프라인 경우는 F9XXXXXXX의 값으로 각각 응답한다.   
   
---   

### Tech. Spec
1. spring 5.x
2. maven 3.x
3. maria DB (기존에는 Oracle DB 사용)
4. myBatis 3.x   
5. spring MVC   
6. Tomcat 9.x

---   

### API
1. URL: http://url-name/aprv_no
2. 요청 타입: POST
3. Content-Type: application/json
4. API Request Body   
| 필드          | 필드명           | 값       | 필수값여부 | 설명                                     |
|---------------|------------------|----------|------------|------------------------------------------|
| svc_modu_id   | 서비스모듈ID     | APPROVE  | Y          | space or null 허용되지 않음              |
| telgrm_fg     | 전문구분         | ON       | N          |                                          |
| onoff_mcht_fg | 온오프라인구분   | 2        | N          | null 값 미 허용 (1: 오프라인, 2: 온라인) |
| mbrsh_pgm_id  | 멤버쉽프로그램ID | A        | N          | null or space 허용                       |
| mcht_no       | 가맹점번호       | 20072964 | N          | null or space 허용                       |   

5. 에러코드   
* AP_7777: 필수값 미 존재   
  case1) svc_modu_id null or space인 경우   
  case2) onoff_mcht_fg, mcht_no 값이 모두 null or space인 경우   
* AP_8830: 가맹점 미 등록   
  case1) mcht_fg의 값이 "2"이고 mcht_no을 값을 조회했을 때 미 존재하는 경우   
* AP_9080: DB Fail      

---   

### 요청/응답 예시   
1. 정상   
* 요청   
```       
{
    "svc_modu_id": "APPROVE",
    "telgrm_fg": "ON",
    "onoff_mcht_fg": "2",
    "mbrsh_pgm_id": "A",
    "mcht_no": "20072964"
}   
 ```   
* 응답   
```   
{
    "apprvoeNumber": {
        "aprv_dy": "20190220",
        "aprv_tm": "160602",
        "aprv_no": "690002208",
        "rep_aprv_no": "690002208"
    },
    "status": "true"
}   
```   
2. 에러   
* 요청   
```   
{
    "svc_modu_id": "",
    "telgrm_fg": "ON",
    "onoff_mcht_fg": "2",
    "mbrsh_pgm_id": "A",
    "mcht_no": "20072964"
}   
```   
* 응답   
```   
{
    "apprvoeNumber": {
        "aprv_dy": null,
        "aprv_tm": null,
        "aprv_no": null,
        "rep_aprv_no": null
    },
    "error": {
        "ans_cd": "AP_7777",
        "ans_msg": "필수값 미 존재"
    },
    "status": "false"
}   
```   
