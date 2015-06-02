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

/**
 * 教务系统验证用户
 * 
 * @author robbin
 *
 */
@Controller
public class AuthenticateController {

	/* 下载的验证码存放的文件夹 */
	private static final String POSITION = "checkcode";

	/* 登录页面url */
	private static final String LOGIN_URL = "http://jwgl.gdut.edu.cn";

	/* 验证码url */
	private static final String CHECKCODE_URL = "http://jwgl.gdut.edu.cn/CheckCode.aspx";

	/* 用于存放CloseableHttpClient，键为获取验证码时浏览器返回的sessionId，值为对应的client */
	private static final Map<String, CloseableHttpClient> clientMap = new HashMap<String, CloseableHttpClient>();

	/**
	 * 先根据fileName来获取在下载验证码时存好的client，再用studentId，password，checkCode来构造数据包，
	 * 然后通过post把数据包发送到登录页面模拟登录
	 * 
	 * @param request
	 * @param studentId
	 *            学号
	 * @param password
	 *            密码
	 * @param checkCode
	 *            验证码
	 * @param fileName
	 *            验证码的名字，由打开浏览器时获取的sessionId及时间戳组成，用"-"连接
	 * @return 成功则返回"{"state":"success"}";失败则返回"{"state":"fail","reason",错误信息}"
	 */
	@ResponseBody
	@RequestMapping(value = "/enumateLogin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String enumateLogin(HttpServletRequest request, String studentId, char[] password, String checkCode,
			String fileName) {
		JSONObject json = new JSONObject();
		CloseableHttpClient client = null;
		List<NameValuePair> form = null;
		client = clientMap.get(fileName.substring(0, fileName.indexOf("-")));
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

	/**
	 * 构造数据包，用于发送post请求时把数据包发送过去。先打开登录页面，正则截取页面中的必要参数，用学号等构造数据包。
	 * 
	 * @param studentId
	 *            学号
	 * @param password
	 *            密码
	 * @param checkCode
	 *            验证码
	 * @param client
	 *            发送请求的对象
	 * @return 数据包(List<NameValuePair>)
	 * @throws Exception
	 *             无法打开登录页面时返回异常信息:"连接超时"
	 */
	private List<NameValuePair> buildForm(String studentId, char[] password, String checkCode,
			CloseableHttpClient client) throws Exception {
		try {
			HttpGet get = new HttpGet(LOGIN_URL);
			HttpResponse response = client.execute(get);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 200) {
				String __VIEWSTATE = "";
				HttpEntity entity = response.getEntity();
				String html = EntityUtils.toString(entity);
				Pattern pattern = Pattern.compile("name=\"__VIEWSTATE\" value=\"(.*)\"");
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
				form.add(new BasicNameValuePair("TextBox2", String.valueOf(password)));
				return form;
			} else {
				throw new ConnectionException("连接超时");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 发送数据到登录页面，根据返回的页面判断是否发送成功，若成功则销毁对应的client及验证码，失败则抛出异常
	 * 
	 * @param request
	 * @param form
	 *            数据包
	 * @param client
	 *            发送请求的对象
	 * @param fileName
	 *            验证码的名字
	 * @throws Exception
	 */
	private void post(HttpServletRequest request, List<NameValuePair> form, CloseableHttpClient client, String fileName)
			throws Exception {
		HttpPost post = new HttpPost(LOGIN_URL);
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, "gb2312");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			String html = EntityUtils.toString(response.getEntity());
			Matcher matcher = null;
			if (statusCode == 302) {
				matcher = Pattern.compile("<a href=\'/(.*)\\?").matcher(html);
				if (matcher.find() && matcher.group(1).equals("xs_main.aspx")) {
					destroyClient(client);
					UserUtil.deletePhoto(request, POSITION, fileName);
					return;
				} else {
					throw new Exception("系统未知错误");
				}
			} else if (statusCode == 200) {
				matcher = Pattern.compile("defer>alert\\(\'(.*)\'\\);").matcher(html);
				if (matcher.find()) {
					throw new AuthenticationException(matcher.group(1));
				}
			} else {
				throw new Exception("未知错误");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			post.releaseConnection();
		}
	}

	/**
	 * 获取验证码。如果fileName为空，则说明本次回话中第一次获取验证码，新建一个client，
	 * 已浏览器返回的sessionId作为key存进clientMap；若fileName不为空，则说明已获取过验证码，
	 * 则根据fileName从clientMap中获取对应的client再去获取验证码，下载到本地。验证码以sessionId及时间戳用"-"连接命名
	 * 
	 * @param request
	 * @param fileName
	 *            验证码名字，第一次获取验证码则名字为空
	 * @return 成功:"{"state":"success","fileName":验证码名字}";
	 *         失败:"{"state":"fail";"reason ":错误信息}"
	 */
	@ResponseBody
	@RequestMapping(value = "/getCheckCode")
	public String getCheckCode(HttpServletRequest request, String fileName) {
		JSONObject json = new JSONObject();
		HttpGet get = new HttpGet(CHECKCODE_URL);
		CloseableHttpClient client = null;
		String sessionId = null;
		InputStream in = null;
		FileOutputStream os = null;
		if ("".equals(fileName)) {
			client = HttpClients.createDefault();
		} else {
			sessionId = fileName.substring(0, fileName.indexOf("-"));
			client = clientMap.get(sessionId);
			UserUtil.deletePhoto(request, POSITION, fileName);
		}
		try {
			HttpResponse response = client.execute(get);
			String timeStamp = String.valueOf(System.currentTimeMillis()).substring(9);
			if ("".equals(fileName)) {
				String cookie = response.getFirstHeader("Set-Cookie").getValue();
				sessionId = cookie.substring(cookie.indexOf("=") + 1, cookie.indexOf(";"));
				clientMap.put(sessionId, client);
			}
			if (response.getStatusLine().getStatusCode() == 200) {
				fileName = sessionId + "-" + timeStamp + ".gif";
				String newFilePath = request.getServletContext().getRealPath("/photo/checkcode") + File.separatorChar
						+ fileName;
				in = response.getEntity().getContent();
				os = new FileOutputStream(new File(newFilePath));
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

	/**
	 * 从clientMap中移除client
	 * 
	 * @param client
	 */
	private void destroyClient(CloseableHttpClient client) {
		Iterator<Entry<String, CloseableHttpClient>> iter = clientMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, CloseableHttpClient> entry = iter.next();
			if (entry.getValue().equals(client)) {
				clientMap.remove(entry.getKey());
				break;
			}
		}
	}
}
