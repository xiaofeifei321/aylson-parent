package com.aylson.dc.owner.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.constants.SysConstant.BillCodePrefix;
import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.owner.search.AppointmentSearch;
import com.aylson.dc.owner.service.AppointmentService;
import com.aylson.dc.owner.vo.AppointmentVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.service.RoleService;
import com.aylson.dc.sys.vo.RoleVo;
import com.aylson.utils.BillNumUtils;
import com.aylson.utils.DateUtil;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;

/**
 * 反馈管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/owner/appointment")
public class AppointmentController extends BaseController {
	
	@Autowired
	private AppointmentService appointmentService;                     //在线预约服务
	@Autowired
	private RoleService roleService;                                   //角色服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/owner/admin/appointment/index";
	}	
	
	/**
	 * 获取列表-分页
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(AppointmentSearch appointmentSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");//缓存信息
			if(sessionInfo != null && sessionInfo.getUser() != null){
				RoleVo roleVo = this.roleService.getById(sessionInfo.getUser().getRoleId());
				if(roleVo != null){
					if("agent".equals(roleVo.getRoleCode())){//如果是代理商，只能看到自己的预约单信息
						appointmentSearch.setByAgentUserId(sessionInfo.getUser().getId());
					}
				}
			}
			appointmentSearch.setIsPage(true);
			List<AppointmentVo> list = this.appointmentService.getList(appointmentSearch);
			result.setTotal(this.appointmentService.getRowCount(appointmentSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 获取列表-不分页
	 * 根据条件获取列表信息
	 * @param appointmentSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<AppointmentVo> getList(AppointmentSearch appointmentSearch) {
		List<AppointmentVo> list = this.appointmentService.getList(appointmentSearch);
		return list;
	}
	
	/**
	 * 后台-添加预约单页面
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd() {
		return "/jsp/owner/admin/appointment/add";
	}	
	
	/**
	 * 后台-添加预约单保存
	 * @param appointmentVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(AppointmentVo appointmentVo) {
		Result result = new Result();
		try{
			//信息校验
			if(!VerificationUtils.isValid(appointmentVo.getMobilePhone(), VerificationUtils.MOBILE)){
				result.setError(ResultCode.CODE_STATE_4006, "请输入有效的预约人手机号码");
				return result;
			}
			/*if(!VerificationUtils.isValid(appointmentVo.getDesignerPhone(), VerificationUtils.MOBILE)){
				result.setError(ResultCode.CODE_STATE_4006, "请输入有效的上门设计师手机号码");
				return result;
			}*/
			if(StringUtil.isNotEmpty(appointmentVo.getHomeTimeStr())){
				appointmentVo.setHomeTime(DateUtil.format(appointmentVo.getHomeTimeStr(),"yyyy-MM-dd HH:mm:ss"));
			}
			if(StringUtil.isNotEmpty(appointmentVo.getDecoratingTimeStr())){
				appointmentVo.setDecoratingTime(DateUtil.format(appointmentVo.getDecoratingTimeStr(),"yyyy-MM-dd"));
			}
			appointmentVo.setAppointDate(new Date());
			appointmentVo.setSource("web"); //添加来源：web后台
			appointmentVo.setBillCode(BillNumUtils.getBillCode(BillCodePrefix.APPOINTMENT,appointmentVo.getMobilePhone()));
			Boolean flag = this.appointmentService.add(appointmentVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功,下单号为：" + appointmentVo.getBillCode());
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 后台-编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toEdit", method = RequestMethod.GET)
	public String toEdit(Integer id, Integer state) {
		if(id != null){
			AppointmentVo appointmentVo = this.appointmentService.getById(id);
			if(appointmentVo.getHomeTime() != null){
				appointmentVo.setHomeTimeStr(DateUtil.format(appointmentVo.getHomeTime()));
			}
			if(appointmentVo.getDecoratingTime() != null){
				appointmentVo.setDecoratingTimeStr(DateUtil.format(appointmentVo.getDecoratingTime(),true));
			}
			appointmentVo.setState(state);
			this.request.setAttribute("appointmentVo",appointmentVo);
		}
		if(3 == state){
			return "/jsp/owner/admin/appointment/cancel";
		}else{
			return "/jsp/owner/admin/appointment/add";
		}
	}	
	
	/**
	 * 后台-编辑保存
	 * @param appointmentVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(AppointmentVo appointmentVo) {
		Result result = new Result();
		try {
			if(StringUtil.isNotEmpty(appointmentVo.getHomeTimeStr())){
				appointmentVo.setHomeTime(DateUtil.format(appointmentVo.getHomeTimeStr(),"yyyy-MM-dd HH:mm:ss"));
			}
			if(StringUtil.isNotEmpty(appointmentVo.getDecoratingTimeStr())){
				appointmentVo.setDecoratingTime(DateUtil.format(appointmentVo.getDecoratingTimeStr(),"yyyy-MM-dd"));
			}
			Boolean flag = this.appointmentService.edit(appointmentVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteById(Integer id) {
		Result result = new Result();
		try{
			Boolean flag = this.appointmentService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 通知代理商报价
	 * @param appointId
	 * @return
	 */
	@RequestMapping(value = "/admin/noticeAgentQuote", method = RequestMethod.POST)
	@ResponseBody
	public Result noticeAgentQuote(Integer appointId) {
		Result result = null;
		try{
			result = this.appointmentService.noticeAgentQuote(appointId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 通知客户确认报价
	 * @param appointId
	 * @return
	 */
	@RequestMapping(value = "/admin/noticeConfirmQuote", method = RequestMethod.POST)
	@ResponseBody
	public Result noticeConfirmQuote(Integer appointId) {
		Result result = null;
		try{
			result = this.appointmentService.noticeConfirmQuote(appointId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 查看预约信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toView", method = RequestMethod.GET)
	public String toView(Integer id) {
		if(id != null){
			AppointmentVo appointmentVo = this.appointmentService.getById(id);
			if(appointmentVo.getHomeTime() != null){
				appointmentVo.setHomeTimeStr(DateUtil.format(appointmentVo.getHomeTime()));
			}
			if(appointmentVo.getDecoratingTime() != null){
				appointmentVo.setDecoratingTimeStr(DateUtil.format(appointmentVo.getDecoratingTime(),true));
			}
			this.request.setAttribute("appointmentVo",appointmentVo);
		}
		return "/jsp/owner/admin/appointment/view";
	}	
	
	@RequestMapping(value = "/admin/sumConfrimDraw", method = RequestMethod.POST)
	@ResponseBody
	public Result sumConfrimDraw(Integer appointId) {
		Result result = null;
		try{
			result = this.appointmentService.sumConfrimDraw(appointId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
}
