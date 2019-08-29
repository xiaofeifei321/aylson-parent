package com.aylson.dc.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.easyui.EasyuiDataGridJson;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.sys.search.FeedbackSearch;
import com.aylson.dc.sys.service.FeedbackService;
import com.aylson.dc.sys.vo.FeedbackVo;

/**
 * 反馈管理
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/feedback")
public class FeedbackController extends BaseController {
	
	@Autowired
	private FeedbackService feedbackService;     //反馈服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/feedback/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(FeedbackSearch feedbackSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			feedbackSearch.setIsPage(true);
			List<FeedbackVo> list = this.feedbackService.getList(feedbackSearch);
			result.setTotal(this.feedbackService.getRowCount(feedbackSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取列表信息
	 * @param feedbackSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<FeedbackVo> getList(FeedbackSearch feedbackSearch) {
		List<FeedbackVo> list = this.feedbackService.getList(feedbackSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param feedbackVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(FeedbackVo feedbackVo) {
		this.request.setAttribute("feedbackVo",feedbackVo);
		return "/jsp/sys/admin/feedback/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param feedbackVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(FeedbackVo feedbackVo) {
		Result result = new Result();
		try{
			Boolean flag = this.feedbackService.add(feedbackVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
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
	public String toEdit(Integer id) {
		if(id != null){
			FeedbackVo feedbackVo = this.feedbackService.getById(id);
			this.request.setAttribute("feedbackVo",feedbackVo);
		}
		return "/jsp/sys/admin/feedback/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param feedbackVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(FeedbackVo feedbackVo) {
		Result result = new Result();
		try {
			Boolean flag = this.feedbackService.edit(feedbackVo);
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
			Boolean flag = this.feedbackService.deleteById(id);
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
	
	
}
