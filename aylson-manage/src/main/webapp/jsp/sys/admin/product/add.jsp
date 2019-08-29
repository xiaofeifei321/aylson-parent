<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/themes/default/default.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/plugins/code/prettify.css" />
<script charset="utf-8" src="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/kindeditor.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script type="text/javascript">
var editor_productProp;
var editor_params;
var isEditor = false;
function createEditor(editorId){
	return KindEditor.create('#'+editorId,{
        resizeType:1,      
        urlType:projectName, // 带有域名的绝对路径
        allowFileManager : false,
        allowImageUpload : true,
        uploadJson : projectName+'/sys/fileHandle/kindeditorUpload?bucket=dc-gift',
		 items : [
				'source', '|','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons', 'table', 'link','fullscreen'], 
				
	});
}
$("#tabProduct").tabs({
	onSelect:function(title,index){
		if(index == 1){
			if(!editor_productProp){
				editor_productProp = createEditor('editor_productProp');
				editor_params = createEditor('editor_params');
				isEditor = true;
			}
		}
	}
})
</script>
<style>
.imgItem_td_1{
	width:100px;
	height:80px;
	padding:2px;
	margin:2px;
}
.imgItem_td_1 img{
	width:100px;
}
.button_td{
	width:80px;
	height:80px;
}
.button_td_1{
	width:80px;height:80px;
}
.button_td span{
	font-size:24px;
}
.css_pictureDesc{
	width:90%;
	height:80;
}
</style>
<div align="center">
	<form id="productForm" method="post">
		<div class="easyui-tabs" id="tabProduct" style="width:880px;">
		 <div title="基本信息" style="padding:5px;text-align:left">
			<table class="tableForm" >
				<tr>
					<th colspan="2" style="text-align:center;">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-picture',size:'large',iconAlign:'top'"onclick="uploadImg('img','thumbnail','dc-test')" id="uploadImg" style="margin-bottom:10px;">添加缩略图</a>
					</th>
					<td colspan="2" rowspan="4">
						<div style="border-style:solid;border-width:1px;width:300px;height:200px">
						<c:if test="${not empty productVo.thumbnail}"><img id="img" src="${productVo.thumbnail}" style="width:100%;height:100%"/></c:if>
						<c:if test="${empty productVo.thumbnail }"><img id="img" src="" style="width:100%;height:100%"/></c:if>
						<input id="thumbnail" name="thumbnail" value="${productVo.thumbnail}" type="hidden"/>
						</div> 
					</td>
				</tr>
				<tr>
					<th>序号：</th>
					<td><input name="seq" value="${productVo.seq}"
					class="easyui-textbox" 
					style="width: 200px;" /></td>
				</tr>
				<tr>
					<th>产品类别：</th>
					<td>
					<input name="categoryName" id="categoryName" value="${productVo.categoryName}" type="hidden"/>
					<input   name="category" value="${productVo.category}" 
					data-options="required:true,editable:false, url:'<%=request.getContextPath()%>/sys/dictionary/getList?dicType=ProductIntroduceCategory_Item', valueField:'id', textField:'dicName', method:'GET',
					onSelect:function(record){$('#categoryName').val(record.dicName);}" class="easyui-combobox" style="width: 200px;" prompt="-请选择-"/>
					</td>
				</tr>
				<tr>
					<th>产品名称：</th>
					<td><input name="productName" value="${productVo.productName}"
					class="easyui-textbox" data-options="required:true"
					style="width: 200px;" /></td>
				</tr>
			</table> 
		</div>
		<div title="产品特性" style="padding:5px;text-align:left" >
			<table class="tableForm">
				<tr>
					<th>产品特性：</th>
					<td > 
						<textarea id="editor_productProp"  style="width:600px;height:300px;">
								${productVo.productProp}
						</textarea>
					</td>
				</tr>
				<tr>
					<th>适用范围：</th>
					<td > 
						<input name="application" value="${productVo.application}" class="easyui-textbox" style="width: 400px;" />
					</td>
				</tr>
				<tr>
					<th>技术参数：</th>
					<td > 
						<textarea id="editor_params"  style="width:600px;height:300px;">
								${productVo.params}
						</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div title="实景展示" style="padding:10px">
			<table class="tableForm" class="pictureTable" id="p_table">
				<tr>
					<th colspan="10" style="text-align:left;padding:5px;">
					<a href="#" class="easyui-linkbutton c1" onclick="addImg()" style="width:100px">添加</a>
					</th>
				</tr>
				<c:if test="${!empty productVo.pictureInfo }">
						<c:forEach items="${productVo.pictureInfo}" var="item">
							<tr>
							<%-- 实景图片 --%>
							<td class="imgItem_td_1">
								<img  src="${item.key}"  />
								<input name="pictureShowsItem" value="${item.key}" type="hidden"/>
							</td>
							<%-- 实景描述 --%>
							<td>
								<input name="pictureDescItem" value="${item.value}" prompt="请添加对应实景图片的描述...（建议100字以内）"
								class="easyui-textbox " data-options="required:true" style="width:90%;height:70px;" multiline="true" />
							</td>
							<td class="button_td">
								<a href="#" class="easyui-linkbutton c5" onclick="delImg(this)" style="width:80px;height:80px;"><span >删除</span></a>
							</td>
							</tr>
						</c:forEach>
					</c:if>
			</table>
		</div> 
		<div title="产品视频" style="padding:10px;text-align:left">
			<table class="tableForm" style="width:95%;">
				<tr>
					<th colspan="2" style="text-align:center;">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-picture',size:'large',iconAlign:'top'"onclick="uploadImg('img1','videoThum','dc-test')" id="uploadImg" style="margin-bottom:10px;">添加视频封面</a>
					</th>
					<td colspan="2" rowspan="2">
						<div style="border-style:solid;border-width:1px;width:180px;height:120px">
						<c:if test="${not empty productVo.videoThum}"><img id="img1" src="${productVo.videoThum}" style="width:100%;height:100%"/></c:if>
						<c:if test="${empty productVo.videoThum }"><img id="img1" src="" style="width:100%;height:100%"/></c:if>
						<input id="videoThum" name="videoThum" value="${productVo.videoThum}" type="hidden"/>
						</div> 
					</td>
				</tr>
				<tr>
					<th>视频标题：</th>
					<td><input name="videoTitle" value="${productVo.videoTitle}"
					class="easyui-textbox" 
					style="width: 300px;" /></td>
				</tr>
				<tr>
					<th>视频描述：</th>
					<td colspan="3"><input name="videoDesc" value="${productVo.videoDesc}"
					class="easyui-textbox" data-options=""
					style="width: 600px;" /></td>
				</tr>
				<tr>
					<th>视频地址：</th>
					<td colspan="3"><input name="videoUrl" value="${productVo.videoUrl}"
					class="easyui-textbox" data-options=""
					style="width: 600px;" /></td>
				</tr>
			</table> 
		</div>
	</div> 
		<input name="id" type="hidden" value="${productVo.id}"/>
		<input name="productProp" id="productProp" type="hidden" value=""/>
		<input name="params" id="params" type="hidden" value=""/>
	</form>
</div>
<script>
function delImg(obj){
	$(obj).parent().parent().remove();
}

function addImg(){//id:获取上传图片地址的元素,target:目标id
	var win;
	win = $("<div></div>").dialog({
		title:'上传图片',
		width:500,
		height:200,
		modal:true,
		href:projectName+'/sys/fileHandle/toImgUpload',
		onClose:function(){
			$(this).dialog("destroy");
		},
		buttons:[{
			text:'确定',
		    iconCls:'icon-ok',
		    handler:function(){
		    	var winTip = $.messager.progress({
					title:'请稍候...',
					msg:'正在上传...'
				});
		    	var filePath = $("#imgName").filebox('getValue');
		    	$("#bucket").val("dc-test");
		    	if(imgTypeCheck(filePath)){
			    	$("#imgUploadForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/sys/fileHandle/imgUpload',
			    		 //url:projectName+'/sys/fileHandle/uploadTest',
			    		 success:function(responseData){
			    			 $.messager.progress('close');
			    			 if(responseData){
			    				var data = $.parseJSON(responseData);
			    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			    			 	if(data.success){
			    			 		var param = "<tr >";
			    			 		param += "	<td class='imgItem_td_1'>";
			    			 		param += "		<img  src='"+data.data+"' />";
			    			 		param += "		<input name='pictureShowsItem' value='"+data.data+"' type='hidden'/>";
			    			 		param += "	</td>";
			    			 		param += "	<td>";
			    			 		param += "		<input name='pictureDescItem' value='' class='textboxCss' />";
			    			 		param += "	</td>";
			    			 		param += "	<td class='button_td'>";
			    			 		param += "		<a href='#'class='buttonCss' onclick='delImg(this)' class='easyui-linkbutton c5' style='width:80px;height:80px;'><span >删除</span></a>";
			    			 		param += "	</td>";
			    			 		param += "	</tr>";
			    			 		$("#p_table").append(param);
			    			 		$(".textboxCss").textbox({required:true,multiline:true,width:'90%',height:80,prompt:'请添加对应实景图片的描述...（建议100字以内）'})
			    			 		$(".buttonCss").linkbutton();
			    			 		$(".buttonCss").addClass("c5");
			    			 		win.dialog('destroy');
			    				}
			    			 } 
			    		 }
			    	});
		    	}
		     }   
		   },{
			 text:'取消',
		     iconCls:'icon-cancel',  
		 	 handler:function(){
		 		 win.dialog('destroy');
		 	 }   
		  }]
	});
}
</script>