package com.LifeInGDUT.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 模拟登录教务系统，用于验证用户身份
 * 
 * @author robbin
 *
 */
@Controller
public class EnumerateLoginController {

	private String loginUrl;

	private String checkCodeUrl;

	@ResponseBody
	@RequestMapping(value = "/enumerateLogin", method = RequestMethod.POST)
	public String login(String userName, String password, String checkCode)
			throws ClientProtocolException, IOException {

		CloseableHttpClient client = HttpClients.createDefault();
		// 获取教务系统登录页面
		HttpGet getLoginPage = new HttpGet(loginUrl);
		HttpResponse response1 = client.execute(getLoginPage);
		StatusLine status = response1.getStatusLine();
		if (status.getStatusCode() == 200) {
			String __VIEWSTATE = "";
			HttpEntity entity1 = response1.getEntity();
			String html = EntityUtils.toString(entity1);
			Pattern pattern = Pattern
					.compile("name=\"__VIEWSTATE\" value=\"(.*)\"");
			Matcher matcher = pattern.matcher(html);
			if (matcher.find()) {
				__VIEWSTATE = matcher.group(1);
			}
			// 这里
			String url = "http://jwgl.gdut.edu.cn/CheckCode.aspx";
			String file = "D:/checkCode.gif";
			File checkCode1 = new File(file);
			if (checkCode1.exists()) {
				checkCode1.delete();
			}
			HttpGet getCheckCode = new HttpGet(checkCodeUrl);
			HttpResponse response2 = client.execute(getCheckCode);
			StatusLine status2 = response2.getStatusLine();
			if (status2.getStatusCode() == 200) {
				HttpEntity entity2 = response1.getEntity();
				InputStream in = entity2.getContent();
				FileOutputStream os = new FileOutputStream(checkCode1);
				int l = -1;
				byte[] tmp = new byte[2048];
				while ((l = in.read(tmp)) != -1) {
					os.write(tmp);
				}
				os.close();
				in.close();
			} else {

			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.println("请输入下载下来的验证码中显示的数字...");
			String checkCode2 = br.readLine();
			System.out.println(checkCode);

		} else {
			return "网络错误";
		}
		return "";
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet("http://jwgl.gdut.edu.cn");
		HttpResponse response = client.execute(get);
		StatusLine status = response.getStatusLine();
		System.out.println(status.toString());
		System.out.println(status.getProtocolVersion());
		System.out.println(status.getStatusCode());
		System.out.println(status.getReasonPhrase());
		if (status.getStatusCode() == 200) {

		}
		String html = EntityUtils.toString(response.getEntity());
		Pattern pattern = Pattern
				.compile("name=\"__VIEWSTATE\" value=\"(.*)\"");
		Matcher match = pattern.matcher(html);
		if (match.find()) {
			String __VIEWSTATE = match.group(1);
		}
	}

	// @ResponseBody
	@RequestMapping(value = "/log", method = RequestMethod.POST)
	public String login(String userName, String password)
			throws ParseException, IOException {
		/*
		 * userName = "3113000321"; password = "WAYYAW20120118.";
		 */
		HttpClient client = new DefaultHttpClient();
		// CloseableHttpClient client = HttpClients.createDefault();
		// CloseableHttpClient client =
		// HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpGet get = new HttpGet("http://jwgl.gdut.edu.cn");
		// HttpGet get = new HttpGet(
		// "http://210.38.162.116/%28sz0b1myq1belxrjvxnzaoxya%29/default2.aspx/");
		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		StatusLine status = response.getStatusLine();
		System.out.println(status.getStatusCode());
		String content = EntityUtils.toString(entity);
		System.out.println(content);

		String __VIEWSTATE = "";
		Pattern pattern = Pattern
				.compile("name=\"__VIEWSTATE\" value=\"(.*)\"");
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			__VIEWSTATE = matcher.group(1);
		}
		System.out.println(__VIEWSTATE);
		List<Cookie> cookies2 = ((AbstractHttpClient) client).getCookieStore()
				.getCookies();
		for (Cookie cookie : cookies2) {
			System.out.println(cookie);
		}
		System.out
				.println("-------------------------------华丽的分割线-----------------------");

		String url = "http://jwgl.gdut.edu.cn/CheckCode.aspx";
		String file = "D:/checkCode.gif";
		HttpGet get1 = new HttpGet(url);
		File f = new File(file);
		if (f.exists()) {
			f.delete();
		}
		HttpResponse response1 = client.execute(get1);

		Locale locale1 = response1.getLocale();
		System.out.println(locale1.toString());
		StatusLine status1 = response1.getStatusLine();
		System.out.println(status1.getStatusCode());

		HttpEntity entity1 = response1.getEntity();
		// System.out.println(EntityUtils.toString(entity1));HttpClientBuilder
		List<Cookie> cookies = ((AbstractHttpClient) client).getCookieStore()
				.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println(cookie);
		}
		InputStream in = entity1.getContent();
		FileOutputStream os = new FileOutputStream(f);
		int l = -1;
		byte[] tmp = new byte[2048];
		while ((l = in.read(tmp)) != -1) {
			os.write(tmp);
		}
		os.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入下载下来的验证码中显示的数字...");
		String checkCode = br.readLine();
		System.out.println(checkCode);

		System.out
				.println("-------------------------------华丽的分割线-----------------------");
		HttpPost post = new HttpPost("http://jwgl.gdut.edu.cn/default2.aspx");
		// HttpPost post = new
		// HttpPost("http://210.38.162.116/(ctvpshfz1ld1vumaw31yzcu3)/default2.aspx");

		// header
		post.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Host", "jwgl.gdut.edu.cn");
		// post.setHeader("Host", "210.38.162.116");
		post.setHeader("Referer", "http://jwgl.gdut.edu.cn/default2.aspx");
		// post.setHeader("Referer",
		// "http://210.38.162.116/(ctvpshfz1ld1vumaw31yzcu3)/default2.aspx");
		post.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0");

		// postData
		List<NameValuePair> form = new ArrayList<NameValuePair>();
		form.add(new BasicNameValuePair("Button1", ""));
		form.add(new BasicNameValuePair("RadioButtonList1", "学生"));
		form.add(new BasicNameValuePair("TextBox2", password));
		form.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
		form.add(new BasicNameValuePair("hidPdrs", ""));
		form.add(new BasicNameValuePair("hidsc", ""));
		form.add(new BasicNameValuePair("lbLanguage", ""));
		form.add(new BasicNameValuePair("txtUserName", userName));
		form.add(new BasicNameValuePair("txtSecretCode", checkCode));
		UrlEncodedFormEntity entity2 = new UrlEncodedFormEntity(form, "gb2312");
		HttpResponse rep = client.execute(post);
		StatusLine status2 = rep.getStatusLine();
		System.out.println(status2.getStatusCode());

		HttpEntity result1 = rep.getEntity();
		String content1 = EntityUtils.toString(result1);
		System.out.println(content1);
		Pattern pattern1 = Pattern.compile("defer>alert\\(\'(.*)\'\\);");
		Matcher matcher1 = pattern1.matcher(content1);
		if (matcher1.find()) {
			__VIEWSTATE = matcher1.group(1);
		}
		System.out.println(__VIEWSTATE);
		List<Cookie> cookies1 = ((AbstractHttpClient) client).getCookieStore()
				.getCookies();
		for (Cookie cookie : cookies1) {
			System.out.println(cookie);
		}
		post.releaseConnection();
		in.close();

		System.out
				.println("-------------------------------华丽的分割线-----------------------");
		HttpGet get2 = new HttpGet(
				"http://jwgl.gdut.edu.cn/xs_main.aspx?xh=3113000321");
		// HttpGet get2 = new HttpGet(
		// "http://210.38.162.116/(ctvpshfz1ld1vumaw31yzcu3)/xs_main.aspx?xh=121090046");
		HttpResponse response2 = client.execute(get2);

		Locale locale3 = response2.getLocale();
		System.out.println(locale3.toString());
		StatusLine status3 = response2.getStatusLine();
		System.out.println(status3.getStatusCode());

		HttpEntity entity3 = response2.getEntity();
		String content2 = EntityUtils.toString(entity3);
		System.out.println(content2);
		List<Cookie> cookies3 = ((AbstractHttpClient) client).getCookieStore()
				.getCookies();
		for (Cookie cookie : cookies3) {
			System.out.println(cookie);
		}
		return "index";
	}

	@ResponseBody
	@RequestMapping(value = "/login2", method = RequestMethod.POST)
	public String login2(String userName, String password) {
		if (userName.equals("123456") && password.equals("123456")) {
			System.out.println("登录成功");
			return "main";
		} else {
			System.out.println("登录失败");
			return "fail";
		}
	}

	@RequestMapping(value = "/login3", method = RequestMethod.POST)
	public static void login3() throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet("http://jwgl.gdut.edu.cn");
		HttpResponse response = client.execute(get);
		String cookie = response.getFirstHeader("Set-Cookie").getValue();
		String sessionId = cookie.substring(cookie.indexOf("=")+1, cookie.indexOf(";"));
		System.out.println(cookie);
		System.out.println(sessionId);
	}
	
	public static void main(String[] args) {
		try {
			login3();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
