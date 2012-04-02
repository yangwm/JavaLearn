/**
 * 
 */
package security.cert;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

/**
 * @author yangwm in Dec 11, 2009 5:56:44 PM
 */
public class Sign {
	private static String digits = "0123456789abcdef";

	/**
	 * 
	 * create by yangwm in Dec 11, 2009 5:56:44 PM
	 * @param args
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 * @throws InvalidKeyException
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException,
			SignatureException, InvalidKeyException {
		String alg = "RSA";
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(alg);
		KeyPair kp = kpg.generateKeyPair();

		byte[] data = "abc123".getBytes();
		System.out.println("Data:" + toHex(data));

		// sign
		Signature sig1 = Signature.getInstance("NONEwithRSA");
		sig1.initSign(kp.getPrivate());
		sig1.update(data);
		byte[] siged = sig1.sign();
		System.out.println("Signed siged:" + toHex(siged));
		System.out.println("Data:" + toHex(data));

		// verify
		Signature sig2 = Signature.getInstance("NONEwithRSA");
		sig2.initVerify(kp.getPublic());
		sig2.update(data);
		System.out.println("result:" + sig2.verify(siged));
	}

	public static String toHex(byte[] data) {
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i != data.length; i++) {
			int v = data[i] & 0xff;

			buf.append(digits.charAt(v >> 4));
			buf.append(digits.charAt(v & 0xf));
		}

		return buf.toString();
	}
}

/*
D:\study\tempProject\JavaLearn>javac -d classes -sourcepath src -classpath "." src/cert/Sign.java

D:\study\tempProject\JavaLearn>cd classes

D:\study\tempProject\JavaLearn\classes>java cert.Sign
Data:616263313233
Signed data:501ccf217e62df6bebb81e6c339db2a900c04525e6a27a74ec64a6fe4bfb892772628ac03aec38620ed97cd7
4f47f8174af19c852f222de882699a24484293cdfae9989a01eeb560891db6b61d7cfd696d06d62a4a165ff79f4edf4fc20c
260bffc6f70628aa0210a6c4e8c848eb039a6e02a141f6cd59baac7dfa8e77279212
result:true

D:\study\tempProject\JavaLearn\classes>java cert.Sign
Data:616263313233
Signed data:827f1d4faadb14e48051949000a3845a5b97698b01d51c0eb139488b93c3bc68e8200a2f429978e326e77520
b795efe4325e372bc85f7814b40a4dc0a0fc66d86ab37b595be07fb6fb6ebd43504107b7a58f4905a2191082061a98b5c65e
30448c6e3ba25ab5226cb6303428c0ce092ee58bda8cb00646dc716cb5cadb1ca604
result:true

*/
