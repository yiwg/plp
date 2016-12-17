package com.yiwg.plp.controller;

import com.yiwg.plp.po.AjaxPo;
import com.yiwg.plp.util.BindSPUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BindSpController extends BaseController {

	@RequestMapping(value = "/bindSp")
	@ResponseBody
	public AjaxPo bindSp(HttpServletRequest request, HttpServletResponse response,String content) {
		AjaxPo res = new AjaxPo();
		List<String> deviceNums=new ArrayList<>();
		deviceNums.add("45015996");
		deviceNums.add("45015995");
		deviceNums.add("45015994");


		String spNum="80070205";
		try {
			BindSPUtil.setRelative(spNum,deviceNums,"1");
			res.setSuccess(1,"绑定成功");
		} catch (Exception e) {
			logger.info("绑定失败",e);
			res.setFailed(-1,"绑定失败");
			return res;
		}
		return res;
	}
}