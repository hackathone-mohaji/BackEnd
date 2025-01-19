package com.mohaji.hackathon.common.error.enums;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "유효하지 않은 입력 값입니다"),
    METHOD_NOT_ALLOWED(405, "C002", "지원하지 않는 메서드입니다"),
    ENTITY_NOT_FOUND(404, "C003", "엔티티를 찾을 수 없습니다"),
    INTERNAL_SERVER_ERROR(500, "C004", "서버에서 에러가 발생했습니다"),
    INVALID_TYPE_VALUE(400, "C005", "유효하지 않은 형식의 값입니다"),
    HANDLE_ACCESS_DENIED(403, "C006", "접근 권한이 없습니다"),
    URL_NOT_FOUND(404, "C007", "요청한 주소의 API를 찾을 수 없습니다"),
    MISSING_PARAMETER(400, "C008", "필수 값인 매개변수를 찾을 수 없습니다"),
    DATA_INTEGRITY_VIOLATION(400, "C008", "잘못된 데이터에 접근하였습니다, 요청 값을 확인해주세요"),
    HTTP_MESSAGE_NOT_READABLE(400, "C009", "잘못된 JSON 요청 형식입니다"),
    ILLEGAL_ARGUMENT(400, "C010", "잘못된 인수 값이 포함된 요청입니다"),
    NICKNAME_DUPLICATE(400, "C012", "이미 사용중인 닉네임입니다"),
    DATE_TIME_PARSE_FAILURE(400, "C013", "잘못된 DateTime 형식입니다"),
    HTTP_MESSAGE_CONVERSION(500, "C014", "요청 데이터 변환에 실패했습니다. 고객센터로 문의해주세요"),
    //    APP_OAUTH2_LOGIN_FAIL(500, "C015", "앱에서 OAuth2 로그인에 실패했습니다. 고객센터로 문의해주세요"),
//    IMAGE_RESIZE_FAIL(500, "C016", "이미지 리사이징에 실패하였습니다."),
    MISSING_SERVLET_REQUEST_PART(400, "C017", "MultipartFile의 필수 파라미터가 존재하지 않습니다"),
    INVALID_REQUEST(400,"C018", "잘못된 요청값입니다."),
    MULTIPART_FILE_NOT_FOUND(400,"C019","MultipartFile을 찾을 수 없습니다."),
    REQUEST_FAILED(400,"C020","요청에 실패했습니다 다시 시도해주세요"),
    UNAUTHORIZED(400, "C021", "인증되지 않은 사용자입니다. 다시 로그인해주세요."),



    // Member
    MEMBER_NOT_FOUND(404, "M001", "존재하지 않는 회원입니다"),
    MEMBER_PROFILE_DUPLICATION(400, "M002", "이미 존재하는 회원입니다"),
    COACH_NOT_FOUND(400, "M004", "코치가 존재하지 않습니다"),
    INVALID_ROLE(400,"M005", "일치하는 역할이 아닙니다"),
    WRONG_STATUS(400,"M006", "이미 신청했거나 담당 코치가 정해진 상태입니다."),
    INVALID_MEMBER_ID(400,"M007"," 잘못된 멤버ID입니다."),


    // Token
    MISMATCH_REFRESH_TOKEN(401, "T001", "유효하지 않은 리프레시 토큰입니다"),
    NO_PERMISSION(401, "T002", "요청에 대한 권한이 없습니다"),
    TOKEN_ERROR(403,"T003","토큰 관련 오류입니다"),
    INVALID_JWT_TOKEN(401,"T004","잘못된 JWT 토큰입니다"),
    EXPIRED_ACCESS_TOKEN(401, "T005", "만료된 엑세스 토큰"),
    EXPIRED_REFRESH_TOKEN(401, "T006", "만료된 리프레시 토큰"),
    //Firebase Custom Token
    FAIL_TO_MAKE_FIREBASE_CUSTOM_TOKEN(401,"T004","Firebase Custom Token을 발급하는데 실패했습니다."),


    // Post
//    INVALID_ORDER_NUMBER(400, "P002", "잘못된 순서 번호입니다"),
//    MAX_POSTS_PER_DAY(400, "P003", "하루에 작성 가능한 최대 게시글 개수에 도달했습니다"),
    TOO_FREQUENT_POST(400, "P004", "게시글을 작성한 지 얼마 지나지 않았으므로 잠시 후 작성해주세요"),
    WEAR_NULL(404, "P005", "옷 없음"),
    NOT_WEAR_OWN(409, "P006", "옷의 소유자가 아닙니다."),
    COMBINATION_NULL(409, "P007", "조합이 없습니다. 충분한 옷을 등록해 주세요"),
    COMBINATION_NON(500, "P008", "조합이 없습니다."),


    // Multipart
    MULTIPART_FILE_EXCEPTION(400, "MP001", "파일을 찾을 수 없습니다"),
    STORAGE_UPLOAD_FAILURE(400, "MP002", "스토리지로 업로드를 실패했습니다"),
    MAX_UPLOAD_SIZE_EXCEEDED(400, "MP003", "파일 최대 크기를 초과했습니다"),
    NO_SUCH_KEY(400, "MP004", "잘못된 파일 이름으로 스토리지에 업로드를 요청했습니다"),

    //Login
    INCOMPLETE_SIGNUP_INFO(400,"L001","회원가입 정보 누락! 누락된 정보를 입력해주세요"),
    INVALID_PASSWORD(400,"L002","비밀번호가 일치하지 않습니다"),
    NOT_PENDING_STATUS(400,"L003","신청 상태가 아닙니다"),
    NOT_STUDENT(400,"L004", "역할이 학생이 아닙니다"),
    NOT_APPROVED_STATUS(400,"L005","담당 코치-학생 상태가 아닙니다"),
    NOT_COACH(400, "L006", "코치가 아닙니다"),

    //Reservation
    RESERVATION_NOT_FOUND(400, "R001", "예약정보를 찾을 수 없습니다."),
    RESERVATION_TIME_CONFLICT(400, "R002", "동일 시간에 예약이 존재합니다."),
    PAST_RESERVATION_NOT_ALLOWED(400,"R003","과거의 예약은 진행할 수 없습니다."),
    NOT_AVAILABLE_RESERVATION_TIME(400,"R004","예약 가능 시간이 아닙니다."),
    RESERVATION_NOT_FOUND_OR_ALREADY_DELETED(400, "R005", "예약정보를 찾을 수 없거나 이미 삭제된 예약입니다."),

    //Lesson
    LESSONNOTE_NOT_FOUND(400,"LN001","레슨 노트를 찾을 수 없습니다"),
    LESSONNOTE_NOT_FOUND_OR_ALREADY_DELETED(400,"LN002","레슨노트를 찾을 수 없거나 이미 삭제된 노트입니다."),
    LESSONNOTE_NOT_UPDATED(400,"LN003","레슨 노트가 변경되지 않았습니다"),

    //날씨정보
    WEATHER_INFORMATION_NOT_FOUND(400,"WI001","날씨 정보를 찾을 수 없습니다."),
    WRONG_IMAGE(400,"WI001","잘못된 이미지를 입력했습니다. 올바른 이미지를 입력해주세요"),

    //FCM 관련
    FAIL_TO_SEND_PUSH_NOTIFICATION(400,"F001","푸시알림 전송 요청을 실패했습니다");



    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
