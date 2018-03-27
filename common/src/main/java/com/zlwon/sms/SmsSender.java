package com.zlwon.sms;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
public class SmsSender {

    private static final Integer APP_ID = 1400073663;
    private static final String APP_KEY = "1ba36f0a34542a7bd763ad3fa01695c7";

    private SmsSingleSender singleSender;

    private SmsMultiSender multiSender;

    private void initSmsSingleSender() throws Exception{
        if(singleSender == null){
            log.info("initSmsSingleSender");
            singleSender = new SmsSingleSender(APP_ID, APP_KEY);
        }
    }

    private void initSmsMultiSender() throws Exception{
        if(multiSender == null){
            log.info("initSmsMultiSender");
            multiSender = new SmsMultiSender(APP_ID, APP_KEY);
        }
    }

    /**
     * 普通单发短信接口，明确指定内容，如果有多个签名，请在内容中以【】的方式添加到信息内容中，否则系统将使用默认签名
     * @param type  短信类型，0 为普通短信，1 营销短信
     * @param nationCode  国家码，如 86 为中国
     * @param phoneNumber  不带国家码的手机号
     * @param msg  信息内容，必须与申请的模板格式一致，否则将返回错误
     * @param extend  扩展码，可填空
     * @param ext  服务端原样返回的参数，可填空
     * @return
     * @throws Exception
     */
    public SmsSingleSenderResult sendSms(Integer type, String nationCode, String phoneNumber, String msg, String extend, String ext) throws Exception{
        initSmsSingleSender();
        return singleSender.send(type, nationCode, phoneNumber, msg, extend, ext);
    }

    /**
     * 指定模板单发
     * @param nationCode  国家码，如 86 为中国
     * @param phoneNumber  不带国家码的手机号
     * @param templId  信息内容
     * @param params  模板参数列表，如模板 {1}...{2}...{3}，那么需要带三个参数
     * @param sign  签名，如果填空，系统会使用默认签名
     * @param extend  扩展码，可填空
     * @param ext  服务端原样返回的参数，可填空
     * @return
     * @throws Exception
     */
    public SmsSingleSenderResult sendSmsWithTemplate(String nationCode, String phoneNumber, Integer templId, ArrayList<String> params, String sign, String extend, String ext) throws Exception{
        initSmsSingleSender();
        return singleSender.sendWithParam(nationCode, phoneNumber, templId, params, sign, extend, ext);
    }

    /**
     * 普通群发，明确指定内容，如果有多个签名，请在内容中以【】的方式添加到信息内容中，否则系统将使用默认签名
     * 【注意】海外短信无群发功能
     * @param type  短信类型，0 为普通短信，1 营销短信
     * @param nationCode  国家码，如 86 为中国
     * @param phoneNumbers  不带国家码的手机号列表
     * @param msg  信息内容，必须与申请的模板格式一致，否则将返回错误
     * @param extend  扩展码，可填空
     * @param ext  服务端原样返回的参数，可填空
     * @return
     * @throws Exception
     */
    public SmsMultiSenderResult multiSendSms(Integer type, String nationCode, ArrayList<String> phoneNumbers, String msg, String extend, String ext) throws Exception{
        initSmsMultiSender();
        return multiSender.send(type, nationCode, phoneNumbers, msg, extend, ext);
    }

    /**
     * 指定模板群发
     * 【注意】海外短信无群发功能
     * @param nationCode  国家码，如 86 为中国
     * @param phoneNumbers  不带国家码的手机号列表
     * @param templId  模板 id
     * @param params  模板参数列表
     * @param sign  签名，如果填空，系统会使用默认签名
     * @param extend  扩展码，可以填空
     * @param ext  服务端原样返回的参数，可以填空
     * @return
     * @throws Exception
     */
    public SmsMultiSenderResult multiSendSmsWithTemplate(String nationCode, ArrayList<String> phoneNumbers, Integer templId, ArrayList<String> params, String sign, String extend, String ext) throws Exception{
        initSmsMultiSender();
        return multiSender.sendWithParam(nationCode, phoneNumbers, templId, params, sign, extend, ext);
    }

}
