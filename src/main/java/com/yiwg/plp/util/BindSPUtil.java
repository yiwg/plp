package com.yiwg.plp.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yiwg.plp.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yiweiguo on 2016/12/15.
 */
public class BindSPUtil {

    protected static  Logger logger = LoggerFactory.getLogger(BindSPUtil.class);

    public static void setRelative(String spNum, List<String> deviceNums, String queue) throws ServiceException {
        String url ="http://103.25.23.99/ACDCenter/ACDCenterService?service={0}&params={1}";// ConfigUtil.getString(ConfigUtil.BUTEL_WEBSERVICE);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nubeSN", spNum);
        map.put("nubeANList", deviceNums);
        map.put("isQueue", queue);
        map.put("nickname", "");
        map.put("headUrl", "");
        map.put("isCallAuth", "1");
        String json = JSON.toJSONString(map);
        json = URLEncoder.encode(json);
        Object[] os = new Object[]{ConfigUtil.BUTEL_WEBSERVICE_SETMULTAGENT, json};
        url = MessageFormat.format(url, os);
        HttpPost httpost = new HttpPost(url);
        String ret = null;
        try {
            ret = HttpClientUtil.getSafeHttpClient().post(url, httpost);
            logger.info("调用butel为单接入号码设置分机接口，url=" + url + ",结果为：" + ret);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("调用httpClient出错。。。");
        }
        if (StringUtils.isBlank(ret)) {
            throw new ServiceException("调用httpClient返回内容为空。。。");
        }
        JSONObject jsonObject = JSON.parseObject(ret);
        String status = jsonObject.getString("status");
        if ("0".equals(status)) {
            //�ɹ�
            return;
        } else {
            throw new ServiceException(jsonObject.getString("message"));
        }
    }
}
