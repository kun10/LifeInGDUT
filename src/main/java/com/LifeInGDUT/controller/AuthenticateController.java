package com.LifeInGDUT.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.LifeInGDUT.exception.AuthenticationException;
import com.LifeInGDUT.exception.ConnectionException;
import com.LifeInGDUT.util.UserUtil;

@Controller
public class AuthenticateController {

	private static final String POSITION = "checkcode";

	private static final String LOGIN_URL = "http://jwgl.gdut.edu.cn";

	private static final String CHECKCODE_URL = "http://jwgl.gdut.edu.cn/CheckCode.aspx";

	private static final Map<String, CloseableHttpClient> clientMap = new HashMap<String, CloseableHttpClient>();

	@ResponseBody
	@RequestMapping(value = "/enumateLogin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String enumateLogin(HttpServletRequest request, String studentId,
			char[] password, String checkCode, String fileName) {
		System.out.println(clientMap);
		JSONObject json = new JSONObject();
		System.out.println("fileName"+fileName);
		CloseableHttpClient client = null;
		List<NameValuePair> form = null;
		client = clientMap.get(fileName.substring(0, fileName.indexOf("-")));
		System.out.println(client);
		try {
			form = buildForm(studentId, password, checkCode, client);
			post(request, form, client, fileName);
		} catch (Exception e) {
			json.accumulate("state", "fail");
			json.accumulate("reason", e.getMessage());
			e.printStackTrace();
			return json.toString();
		}
		json.accumulate("state", "success");
		return json.toString();
	}

	private List<NameValuePair> buildForm(String studentId, char[] password,
			String checkCode, CloseableHttpClient client) throws Exception {
		try {
			HttpGet get = new HttpGet(LOGIN_URL);
			HttpResponse response = client.execute(get);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 200) {
				String __VIEWSTATE = "";
				HttpEntity entity = response.getEntity();
				String html = EntityUtils.toString(entity);
				Pattern pattern = Pattern
						.compile("name=\"__VIEWSTATE\" value=\"(.*)\"");
				Matcher matcher = pattern.matcher(html);
				if (matcher.find()) {
					__VIEWSTATE = matcher.group(1);
				}
				List<NameValuePair> form = new ArrayList<NameValuePair>();
				form.add(new BasicNameValuePair("hidsc", ""));
				form.add(new BasicNameValuePair("Button1", ""));
				form.add(new BasicNameValuePair("hidPdrs", ""));
				form.add(new BasicNameValuePair("lbLanguage", ""));
				form.add(new BasicNameValuePair("txtUserName", studentId));
				form.add(new BasicNameValuePair("RadioButtonList1", "学生"));
				form.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
				form.add(new BasicNameValuePair("txtSecretCode", checkCode));
				form.add(new BasicNameValuePair("TextBox2", String
						.valueOf(password)));
				return form;
			} else {
				throw new ConnectionException("连接超时");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void post(HttpServletRequest request, List<NameValuePair> form,
			CloseableHttpClient client, String fileName) throws Exception {
		System.out.println(form.get(4));
		HttpPost post = new HttpPost(LOGIN_URL);
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form,
					"gb2312");
			post.setEntity(entity);
			System.out.println("requestline:" + post.getRequestLine());
			HttpResponse response = client.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			String html = EntityUtils.toString(response.getEntity());
			if (statusCode == 302) {
				Pattern pattern = Pattern.compile("<a href=\'/(.*)\\?");
				Matcher matcher = pattern.matcher(html);
				System.out.println(html);
				if (matcher.find() && matcher.group(1).equals("xs_main.aspx")) {
					// enterMain(client);
					destroyClient(client);
					UserUtil.deletePhoto(request, POSITION, fileName);
					return;
				} else {
					throw new Exception("system error");
				}
			} else if (statusCode == 200) {
				Pattern pattern1 = Pattern
						.compile("defer>alert\\(\'(.*)\'\\);");
				Matcher matcher1 = pattern1.matcher(html);
				System.out.println(html);
				if (matcher1.find()) {
					throw new AuthenticationException(matcher1.group(1));
				}
			} else {
				throw new Exception("unknow error");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			post.releaseConnection();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getCheckCode")
	public String getCheckCode(HttpServletRequest request, String fileName) {
		JSONObject json = new JSONObject();
		System.out.println(fileName);
		CloseableHttpClient client = null;
		String sessionId = null;
		if (fileName.equals("")) {
			client = HttpClients.createDefault();
		} else {
			sessionId = fileName.substring(0, fileName.indexOf("-"));
			client = clientMap.get(sessionId);
			System.out.println(client);
			UserUtil.deletePhoto(request, POSITION, fileName);
		}
		HttpGet get = new HttpGet(CHECKCODE_URL);
		InputStream in = null;
		FileOutputStream os = null;
		try {
			HttpResponse response = client.execute(get);
			String timeStamp = String.valueOf(System.currentTimeMillis())
					.substring(9);
			if (fileName.equals("")) {
				String cookie = response.getFirstHeader("Set-Cookie")
						.getValue();
				sessionId = cookie.substring(cookie.indexOf("=") + 1,
						cookie.indexOf(";"));
				clientMap.put(sessionId, client);
				System.out.println(clientMap);
			}
			if (response.getStatusLine().getStatusCode() == 200) {
				fileName = sessionId + "-" + timeStamp + ".gif";
				String newFilePath = request.getServletContext().getRealPath(
						"/photo/checkcode")
						+ "\\" + fileName;
				System.out.println(newFilePath);
				File newFile = new File(newFilePath);
				HttpEntity entity = response.getEntity();
				in = entity.getContent();
				os = new FileOutputStream(newFile);
				@SuppressWarnings("unused")
				int l = -1;
				byte[] tmp = new byte[2048];
				while ((l = in.read(tmp)) != -1) {
					os.write(tmp);
				}
				json.accumulate("state", "success");
				json.accumulate("fileName", fileName);
				return json.toString();
			} else {
				json.accumulate("state", "fail");
				json.accumulate("reason", "获取验证码失败");
				return json.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.accumulate("state", "fail");
			json.accumulate("reason", e.getMessage());
			return json.toString();
		} finally {
			try {
				if (os != null && in != null) {
					os.close();
					in.close();
				}
			} catch (IOException e) {
				json.accumulate("state", "fail");
				json.accumulate("reason", e.getMessage());
				return json.toString();
			}
		}
	}

	private void destroyClient(CloseableHttpClient client) {
		Iterator<Entry<String, CloseableHttpClient>> iter = clientMap
				.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, CloseableHttpClient> entry = iter.next();
			if (entry.getValue().equals(client)) {
				System.out.println("destroy前的clientMap:" + clientMap);
				clientMap.remove(entry.getKey());
				System.out.println("destory后的clientMap:" + clientMap);
				break;
			}
		}
	}

	public static void main(String[] args) {
		String time = String.valueOf(System.currentTimeMillis()) + "-1234";
		System.out.println(time.substring(0, time.indexOf("-")));
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println(System.currentTimeMillis());
			}
		}, 9999);
	}
}
