package com.aylson.dc.sys.controller;

import java.util.Date;
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
import com.aylson.dc.sys.search.NoticeSearch;
import com.aylson.dc.sys.service.NoticeService;
import com.aylson.dc.sys.vo.NoticeVo;

/**
 * 公告管理
 * @author wwx
 * @since  2016-11
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/sys/notice")
public class NoticeController extends BaseController {
	
	@Autowired
	private NoticeService noticeService;     //公告管理服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/notice/index";
	}	
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(NoticeSearch noticeSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			noticeSearch.setIsPage(true);
			List<NoticeVo> list = this.noticeService.getList(noticeSearch);
			result.setTotal(this.noticeService.getRowCount(noticeSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据条件获取列表信息
	 * @param noticeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<NoticeVo> getList(NoticeSearch noticeSearch) {
		List<NoticeVo> list = this.noticeService.getList(noticeSearch);
		return list;
	}
	
	/**
	 * 后台-添加页面
	 * @param noticeVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(NoticeVo noticeVo) {
		this.request.setAttribute("noticeVo",noticeVo);
		return "/jsp/sys/admin/notice/add";
	}	
	
	/**
	 * 后台-添加保存
	 * @param noticeVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(NoticeVo noticeVo) {
		Result result = new Result();
		try{
			noticeVo.setCreateTime(new Date());
			Boolean flag = this.noticeService.add(noticeVo);
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
			NoticeVo noticeVo = this.noticeService.getById(id);
			this.request.setAttribute("noticeVo",noticeVo);
		}
		return "/jsp/sys/admin/notice/add";
	}	
	
	/**
	 * 后台-编辑保存
	 * @param noticeVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(NoticeVo noticeVo) {
		Result result = new Result();
		try {
			Boolean flag = this.noticeService.edit(noticeVo);
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
			Boolean flag = this.noticeService.deleteById(id);
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
	
	@RequestMapping(value = "/admin/publish", method = RequestMethod.POST)
	@ResponseBody
	public Result publish(NoticeVo noticeVo) {
		Result result = new Result();
		try {
			if(noticeVo.getState() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取公告状态失败");
				return result;
			}
			if(noticeVo.getState().intValue() == 1){
				noticeVo.setPublishTime(new Date());
			}
			Boolean flag = this.noticeService.edit(noticeVo);
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
	
	
}
