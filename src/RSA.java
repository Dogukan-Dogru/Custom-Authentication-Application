import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class RSA {


	private static String publicKey;
	private static String privateKey;
	
    public static PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException
    {
    	File fil = new File("User.txt");
    	FileInputStream in = null;
    	String path = "publicKey.txt";
    	String content = Files.readString(Paths.get(path));
        String publicinput = content;
        try {
            in = new FileInputStream("publicKey.txt");
            
            int c;
            while ((c = in.read()) != -1) {
            	publicinput = publicinput + c;
            }
         }finally {
             if (in != null) {
                 in.close();
              }
              
           }
        
        publicKey = publicinput;
        
        FileInputStream a = null;
    	String cnts2 = Files.readString(Paths.get("privateKey.txt"));
        String privateinput = cnts2;
        
        try {
            a = new FileInputStream("privateKey.txt");
            
            int y;
            while ((y = a.read()) != -1) {
            	privateinput = privateinput + y;
            }
         }finally {
             if (a != null) {
                 a.close();
              }
              
           }

        privateKey = privateinput;
        
        FileWriter fw = new FileWriter(fil, true);
        PrintWriter writer = new PrintWriter(fil);
        writer.print("");
        // other operations
        writer.close();
        String line[];  
        String s;
        String enc = "";
        String newLine = "\n";
        String last = "";
        

        Scanner sc = new Scanner(fil);
        int counter = 0;
        while(sc.hasNextLine())
        {
        	if(counter % 2 == 1)
        	{
        		enc = enc + sc.nextLine() + newLine;
        	}
        	else
        	{
        		enc = enc + sc.nextLine() + newLine;
        	}
        	
        	counter++;
        }
        System.out.println(enc);

        enc =  Base64.getEncoder().encodeToString(encrypt(enc, publicKey));
    	System.out.println(enc);
    	
        fw.write(enc);
//
//        
//        String decryptedString = RSA.decrypt(enc, privateKey);
//        System.out.println(decryptedString);
//        fw.write(decryptedString);
        fw.close();
    }

}