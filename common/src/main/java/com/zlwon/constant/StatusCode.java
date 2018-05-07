package com.zlwon.constant;

/**
 * Created by fred on 2017/12/5.
 */
public enum StatusCode {

    SUCCESS("000000", "成功"),
    SYS_ERROR("000001", "系统错误"),
    INVALID_PARAM("000002", "参数错误"),
    USER_NOT_EXIST("000003", "用户不存在"),
    USER_EXIST("000004", "用户已存在"),
    PASSWORD_INVALID("000005", "用户名或密码错误"),
    ACTIVE_CODE_INVALID("000006", "短信验证码错误"),
    ACTIVE_CODE_EXPIRED("000007", "短信验证码过期"),
    MANAGER_CODE_NOLOGIN("000008", "未登陆"),
    MANAGER_CODE_EXISTLOGIN("000009", "该账号已在别处登陆"),
    DATA_NOT_EXIST("000010", "要操作的数据不存在"),
    MOBILE_IS_REGISTER("000011", "手机号已被注册"),
    MAIL_IS_REGISTER("000012", "邮箱已被注册"),
    MOBILE_FORMAT_ERROR("000013", "手机号格式错误"),
    MAIL_FORMAT_ERROR("000014", "邮箱格式错误"),
    MESSAGE_SEND_FAIL("000015", "短信发送失败"),
    DATA_IS_EXIST("000016", "数据已存在"),
    USER_NOT_ENGINEER("000017", "用户非知料师用户"),
    DATA_IS_EXAMINE("000018", "已审核通过，请勿重复提交"),
    VOTE_IS_EXIST("000019", "用户今日已投票，请明日再来"),
    VOTE_RECORD_OVER("000020", "投票已截止，欢迎下次参与"),
    VOTE_MESSAGE_OVER("000021", "点评已截止，欢迎下次参与"),
    OLD_PASSWORD_INVALID("000022", "原密码错误"),
	ATTENTION_IS_EXIST("000023", "已关注，请不要重复关注"),
	ATTENTION_NOT_EXIST("000024", "未关注"),
	WECHAT_IS_USE("000025", "该微信号已绑定账户，请勿重复使用"),
	APP_IS_HOT("000026", "该案例已是热门案例，请勿重复设置"),
	APP_IS_NOT_HOT("000027", "该案例不是热门案例，请勿重复设置"),
	APP_IS_MAX("000028", "设置失败，热门案例最大个数为5个"),
	COLLECTION_IS_EXIST("000029", "收藏已存在，请勿重复添加"),
	USER_NOT_PERMIT("000030", "用户没有该数据操作权限"),
	DATE_EXAMINE_SUCCESS("000031", "已审核通过，请勿重复操作"),
	DATE_EXAMINE_FAILED("000032", "已驳回，请勿重复操作"),
	DATE_NOT_EXAMINE_FAILED("000033", "不是驳回状态"),
	CHARACTERISTIC_IS_EXIST("000034", "物性标签已存在，请勿重复添加"),
	EDITCASE_IS_FAILED("000035", "已驳回，无法执行审核通过"),
	MAIL_NOT_EXIST("000036", "用户尚未填写邮箱，无法使用系统邮箱操作"),
	DEALERDQUOTATION_IS_EXIST("000037", "该规格色号材料报价单已存在，请勿重复添加"),
	SPECIFICATION_NOT_EXIST("000038", "物性规格不存在"),

    PERMISSION_ERROR("100001", "认证错误"),
    WECHAT_REQUEST_ERROR("100002", "微信开放接口请求错误");

    private String code;
    private String message;

    StatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static StatusCode getByCode(String code) {
        for (StatusCode statusCode : StatusCode.values()) {
            if (statusCode.getCode().equals(code)) {
                return statusCode;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
