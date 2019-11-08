package xxx;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.bouncycastle.util.encoders.UrlBase64;

public class Base64 {

	public static String encrypt(byte[] arg0) {
		return new String(UrlBase64.encode(arg0));
	}

	public static byte[] decrypt(String arg0) {
		if (arg0 == null) return null;
		try {
			return UrlBase64.decode(arg0);
		} catch (Exception e) {
			return null;
		}
	}

	public static Object Base64_Object(String str) throws Exception {
		byte[] obj = decrypt(str);
		if (obj == null) return null;
		ByteArrayInputStream baos = new ByteArrayInputStream(obj);
		BufferedInputStream zipOut = new BufferedInputStream(baos);
		ObjectInputStream os = new ObjectInputStream(zipOut);
		return os.readObject();
	}

	public static String Object_Base64(Object obj) throws IOException {
		if (obj == null) return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedOutputStream zipOut = new BufferedOutputStream(baos);
		ObjectOutputStream os = new ObjectOutputStream(zipOut);
		os.writeObject(obj);
		os.flush();
		os.close();
		zipOut.close();
		return encrypt(baos.toByteArray());
	}
}
