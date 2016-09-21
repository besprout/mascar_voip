package com.besprout.voip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.besprout.voip.bean.DialogBean;
import com.besprout.voip.bean.DialogResponse;
import com.besprout.voip.common.Extra;
import com.besprout.voip.service.DialogService;
import com.besprout.voip.util.StringUtil;

@RequestMapping("/dialog")
@Controller
public class DialogController {
	
	public static final String DIALOG_ID = "c1f807ba-5554-4e1a-9cdf-d67744102508";
	
	@Autowired
	private DialogService dialogService;
	
	@ResponseBody
	@RequestMapping(value="/conversation", method=RequestMethod.POST)
	public DialogResponse conversation(@RequestParam(required=false) String input, @RequestParam(required=false) String conversationId, @RequestParam(required=false) String clientId){
		if(StringUtil.isEmpty(input) && !StringUtil.isEmpty(conversationId)){
			DialogResponse response = new DialogResponse(DialogResponse.CALL_HANDING);
			response.setClientId(clientId);
			response.setConversationId(conversationId);
			response.setContent("");
			return response;
		}
		
		DialogBean bean = getDialogBean(conversationId, clientId);
		
		DialogResponse response = dialogService.conversation(input == null ? "" : input, bean);
		if(response.getCallType() == DialogResponse.CALL_HANDUP){
			response.setContent(response.getContent());
			//removeDialogBean(clientId);
		}
		response.setOrderText(dialogService.getOrder2Text(bean.getOrderBean()));
		return response;
	}
	
	private void removeDialogBean(String clientId){
		Extra.dialogMap.remove(clientId);
	}
	
	private DialogBean getDialogBean(String conversationId, String clientId){
		DialogBean dialogBean = Extra.dialogMap.get(clientId);
		if(dialogBean == null){
			dialogBean = new DialogBean();
			dialogBean.setFromPhone("12345678");
			dialogBean.setToPhone("1233879123");
			dialogBean.setDialogId(DIALOG_ID);
			dialogBean.setStoreId(3L);
			Extra.dialogMap.put(clientId, dialogBean);
		}
		
		dialogBean.setClientId(clientId == null ? "" : clientId);
		dialogBean.setConversationId(conversationId == null ? "" : conversationId);
		return dialogBean;
	}
}
