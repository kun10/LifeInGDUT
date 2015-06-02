package com.LifeInGDUT.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.LifeInGDUT.model.User;

import sun.misc.BASE64Decoder;

@Component
public class UserUtil {

	private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

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

	public static void uploadHeadImg(HttpServletRequest request,
			String position, String photo, String fileName) throws IOException {
		if (photo != null) {
			byte[] b = new BASE64Decoder().decodeBuffer(photo);
			for (int i = 0; i < b.length; i++) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			String path = request.getSession().getServletContext()
					.getRealPath("/photo/" + position)
					+ File.separatorChar + fileName;
			;
			System.out.println(path);
			FileOutputStream f = new FileOutputStream(path);
			f.write(b);
			f.close();
		}
	}

	public static void deletePhoto(HttpServletRequest request, String position,
			String fileName) {
		if (fileName != null) {
			String path = request.getSession().getServletContext()
					.getRealPath("/photo/" + position)
					+ File.separatorChar + fileName;
			System.out.println(path);
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	public static boolean isPerfect(User user) {
		if (user.getNickName() == null || user.getHeadImg() == null
				|| user.getPhone() == null || user.getSex() == 0
				|| user.getDormitory() == null) {
			return false;
		} else {
			return true;
		}
	}
}
