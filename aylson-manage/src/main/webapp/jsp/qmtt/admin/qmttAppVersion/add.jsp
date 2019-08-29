<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
</head>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.6/summernote.css" rel="stylesheet">

<style>
.param_th{
text-align:center!important;
font-size:14px;
padding:5px;
}
.param_td_oper{
	text-align:center!important;
}
.imgItem_td_1{
	width:60px;
	height:100px;
	padding:2px;
	margin:2px;
}
.imgItem_td_1 img{
	width:60px;
}
.basic_td{
text-align:left!important;
}
</style>
<div align="center" >
	<div class="easyui-tabs" id="tabActivity" style="width:100%">
		 <div title="渠道版本配置" style="padding:10px;text-align:center">
		 	<form id="qmttAppVersionConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>配置名称：</th>
						<td colspan="3" style="text-align:left">
							<input name="configName" value="渠道配置详情" readOnly=true
								class="easyui-textbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>渠道明细配置：</th>
						<td colspan="3" style="text-align:left">
							<div id="editor" style="width:90%;height:500px;">
								${qmttAppVersionVo.configData}
							</div>
						</td>
					</tr>
				</table>
				<input name="id" type="hidden" value="${qmttAppVersionVo.id}"/>
				<input name="configData" id="configData" type="hidden" value=""/>
			</form>
		</div>
	</div> 
</div>
<script>
var editor = ace.edit("editor");
editor.setTheme("ace/theme/monokai");
editor.getSession().setMode("ace/mode/javascript");
</script>