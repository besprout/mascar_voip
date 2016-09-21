package com.besprout.voip.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.besprout.voip.bean.ConversationBean;
import com.ibm.watson.developer_cloud.dialog.v1.DialogService;
import com.ibm.watson.developer_cloud.dialog.v1.model.Conversation;


public class WatsonUtil {
	
	public static final String BASE_URL = "https://gateway.watsonplatform.net/dialog/api";
	public static final String USER_NAME = "16dd0fd0-b649-49c6-8eb7-c584a615a761";
	public static final String PASSWORD = "oTCOFIpLVVVz";
	
	public static final String BASE_VOICE_URL = "https://stream.watsonplatform.net/text-to-speech/api";
	public static final String VOICE_USER_NAME = "de0f3165-6897-4cc6-8755-94c63ab932bf";
	public static final String VOICE_PASSWORD = "WCgk1TGIFq7o";
	
	public static String getResponse(String result){
		ConversationBean cvs = getConvert(result);
		for (String response : cvs.getResponse()) {
			if(response != null && !response.equals("")){
				return response;
			}
		}
		return null;
	}
	
	public static ConversationBean getConvert(String result){
		return JsonUtil.readJsonEntity(result, ConversationBean.class);
	}
	
	public static String createDialog(String fileXml) {
		try {
			HttpPost post = new HttpPost(BASE_URL + "/v1/dialogs");
			post.addHeader("Authorization", Credentials.basic(USER_NAME, PASSWORD));

			FileBody file = new FileBody(new File(fileXml));
			StringBody comment = new StringBody("pizza_sample6");

			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("file", file);
			reqEntity.addPart("name", comment);
			post.setEntity(reqEntity);

			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(post);
			HttpEntity entitys = response.getEntity();
			System.out.println(response.getStatusLine().getStatusCode());
			if (HttpStatus.SC_CREATED == response.getStatusLine().getStatusCode()) {
				String content = EntityUtils.toString(entitys);
				System.out.println(content);
				JSONObject json = (JSONObject) new JSONParser().parse(content);
				return (String)json.get("dialog_id");
			}
			httpclient.getConnectionManager().shutdown();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean updateDialog(String filePath, String dialogId){
		boolean result = false;
		
		try {
			HttpPut put = new HttpPut(BASE_URL + "/v1/dialogs/" + dialogId);
			put.addHeader("Authorization", Credentials.basic(USER_NAME, PASSWORD));
			
			FileBody file = new FileBody(new File(filePath));
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("file", file);
			put.setEntity(reqEntity);
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(put);
			HttpEntity entitys = response.getEntity();
			System.out.println(response.getStatusLine().getStatusCode());
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				System.out.println(EntityUtils.toString(entitys));
				result = true;
			}
			httpclient.getConnectionManager().shutdown();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void deleteDialog(String dialogId){
		try {
			HttpDelete delete = new HttpDelete(BASE_URL + "/v1/dialogs/" + dialogId);
			delete.addHeader("Authorization", Credentials.basic(USER_NAME, PASSWORD));
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(delete);
			HttpEntity entitys = response.getEntity();
			System.out.println(response.getStatusLine().getStatusCode());
			System.out.println(EntityUtils.toString(entitys));
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				
			}
			httpclient.getConnectionManager().shutdown();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getDialogs(){
		try {
			HttpGet get = new HttpGet(BASE_URL + "/v1/dialogs");
			get.addHeader("Authorization", Credentials.basic(USER_NAME, PASSWORD));
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(get);
			HttpEntity entitys = response.getEntity();
			System.out.println(response.getStatusLine().getStatusCode());
			System.out.println(EntityUtils.toString(entitys));
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				
			}
			httpclient.getConnectionManager().shutdown();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String doConverse(String dialogId, String clientId, String conversationId, String input){
		System.out.println("dialogId: " + dialogId + ", clientId: " + clientId + ", convertsationId: " + conversationId + ", input: " + input);
		
		String content = null;
		try {
			HttpPost post = new HttpPost(BASE_URL + "/v1/dialogs/" + dialogId + "/conversation");
			post.addHeader("Authorization", Credentials.basic(USER_NAME, PASSWORD));
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if(input != null){
				nvps.add(new BasicNameValuePair("input", input));
			}
			if(clientId != null){
				nvps.add(new BasicNameValuePair("client_id", clientId));
			}
			if(conversationId != null){
				nvps.add(new BasicNameValuePair("conversation_id", conversationId));
			}
			
			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			HttpClient httpclient = new DefaultHttpClient();
			
			
			HttpResponse response = httpclient.execute(post);
			
			
			HttpEntity entitys = response.getEntity();
			int status = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == status || HttpStatus.SC_CREATED == status) {
				content = EntityUtils.toString(entitys);
			}
			httpclient.getConnectionManager().shutdown();
			
		} catch (Throwable e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return content;
	}
	
	public static Conversation doConverse2(String dialogId, String clientId, String conversationId, String input){
		try {
			DialogService service = new DialogService();
			service.setUsernameAndPassword(USER_NAME, PASSWORD);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(DialogService.DIALOG_ID, dialogId);
			params.put(DialogService.CLIENT_ID, clientId);
			params.put(DialogService.INPUT, input);
			params.put(DialogService.CONVERSATION_ID, conversationId);
			
			Conversation conversation = service.converse(params);
			return conversation;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void getVoices(){
		try {
			HttpGet get = new HttpGet(BASE_VOICE_URL + "/v1/voices");
			get.addHeader("Authorization", Credentials.basic(VOICE_USER_NAME, VOICE_PASSWORD));


			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(get);
			HttpEntity entitys = response.getEntity();
			System.out.println(response.getStatusLine().getStatusCode());
			System.out.println(EntityUtils.toString(entitys));
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {

			}
			httpclient.getConnectionManager().shutdown();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean doSynthesis(String text, String filePath, String accept){
		try {
			HttpPost post = new HttpPost(BASE_VOICE_URL + "/v1/synthesize?voice=en-US_AllisonVoice");
			post.addHeader("Authorization", Credentials.basic(VOICE_USER_NAME, VOICE_PASSWORD));
			post.addHeader("Accept", accept);

			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("text", new StringBody("{\"text\":\"" + text + "\"}"));
			
			post.setEntity(reqEntity);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(post);
			HttpEntity entitys = response.getEntity();
			System.out.println(response.getStatusLine().getStatusCode());
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				System.out.println(entitys.getContentType().getValue());
				InputStream in = entitys.getContent();
				File file = new File(filePath);
				FileOutputStream fout = new FileOutputStream(file);
				byte[] buffer = new byte[4096];
				int readLength = 0;
				while ((readLength = in.read(buffer)) > 0) {
					byte[] bytes = new byte[readLength];
					System.arraycopy(buffer, 0, bytes, 0, readLength);
					fout.write(bytes);
				}
				fout.flush();
				fout.close();
				in.close();
			}else{
				System.out.println(EntityUtils.toString(entitys));
				return false;
			}
			httpclient.getConnectionManager().shutdown();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static void doSynthesis2(String text){
		try {
			String encodeText = URLEncoder.encode(text, "UTF-8");
			HttpGet get = new HttpGet(BASE_VOICE_URL + "/v1/synthesize?accept=audio/wav&text=" + encodeText + "&voice=en-US_AllisonVoice");
			get.addHeader("Authorization", Credentials.basic(VOICE_USER_NAME, VOICE_PASSWORD));


			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(get);
			HttpEntity entitys = response.getEntity();
			System.out.println(response.getStatusLine().getStatusCode());
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				System.out.println(entitys.getContentType().getValue());
				InputStream in = entitys.getContent();
				File file = new File("D:\\test.wav");
				FileOutputStream fout = new FileOutputStream(file);
				byte[] buffer = new byte[4096];
				int readLength = 0;
				while ((readLength = in.read(buffer)) > 0) {
					byte[] bytes = new byte[readLength];
					System.arraycopy(buffer, 0, bytes, 0, readLength);
					fout.write(bytes);
				}
				fout.flush();
				fout.close();
				in.close();
			}
			httpclient.getConnectionManager().shutdown();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
