package com.LifeInGDUT.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;

import com.LifeInGDUT.model.User;

@Component
public class UserUtil {

	private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	/**
	 * md5 加密
	 * 
	 * @param password
	 * @return
	 */
	public static char[] encryptPassword(char[] password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] mdbyte = md.digest(String.valueOf(password).getBytes());
			int j = mdbyte.length;
			char str[] = new char[2 * j];
			for (int k = 0, i = 0; i < j; i++) {
				byte b = mdbyte[i];
				str[k++] = hexDigits[b >>> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return str;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param position
	 *            文件夹名称
	 * @param photo
	 *            图片的字符串形式
	 * @param fileName
	 *            图片名
	 * @throws IOException
	 */
	public static void uploadHeadImg(HttpServletRequest request, String position, String photo, String fileName)
			throws IOException {
		if (photo != null) {
			byte[] b = new BASE64Decoder().decodeBuffer(photo);
			for (int i = 0; i < b.length; i++) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			FileOutputStream file = new FileOutputStream(request.getSession().getServletContext()
					.getRealPath("/photo/" + position)+ File.separatorChar + fileName);
			file.write(b);
			file.close();
		}
	}

	/**
	 * 删除图片
	 * 
	 * @param request
	 * @param position
	 * @param fileName
	 */
	public static void deletePhoto(HttpServletRequest request, String position, String fileName) {
		if (fileName != null) {
			File file = new File(request.getSession().getServletContext().getRealPath("/photo/" + position)
					+ File.separatorChar + fileName);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * 判断用户信息是否完整
	 * 
	 * @param user
	 * @return
	 */
	public static boolean isPerfect(User user) {
		if (user.getNickName() == null || user.getHeadImg() == null || user.getPhone() == null || user.getSex() == 0
				|| user.getDormitory() == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取当前时间，格式为"yyyy-MM-dd hh:mm:ss"
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * 获取开始遍历数据库的位置
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public static int getStart(int page, int size) {
		return (page - 1) * size;
	}
}
