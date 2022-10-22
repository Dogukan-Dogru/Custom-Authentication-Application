import java.io.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
 
import java.time.Duration;
import java.time.Instant;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AES {
	
	private static SecretKeySpec secretKey;
    private static byte[] key;
   
 
    public static void setKey(String myKey) 
    {
    	
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            
            secretKey = new SecretKeySpec(key, "AES");
            //System.out.print(key);
            
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    public static String encrypt(String strToEncrypt, String secret) 
    {
        try
        {
        	//System.out.println(strToEncrypt);
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    public static String decrypt(String strToDecrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    public static void main(String[] args) throws IOException
    {
    	
    	System.out.println("AES");
    	
    	long start = System.currentTimeMillis();

    	FileInputStream in = null;
    	String path = "aes_key";
    	String content = Files.readString(Paths.get(path));
        String secretKey = content;
        
        try {
            in = new FileInputStream("aes_key");
            
            int c;
            while ((c = in.read()) != -1) {
               secretKey = secretKey + c;
            }
         }finally {
             if (in != null) {
                 in.close();
              }
              
           }
	    
        /*FileInputStream a = null;
	    String lyrics = "lyrics.txt";
    	String cnts = Files.readString(Paths.get(lyrics));
        String input = cnts;
        
        try {
            a = new FileInputStream("lyrics.txt");
            
            int x;
            while ((x = a.read()) != -1) {
            	input = input + x;
            }
         }finally {
             if (a != null) {
                 a.close();
              }
              
           }*/
        //System.out.println(input);
        String input = "aaaaaa";
        boolean check = true;
        long finish = System.currentTimeMillis();
    	long timeElapsed = ((finish - start) / 1000) % 60;
    	
    	int filecount  = 0;
    	String filename = "enc" + filecount;
    	
    	String encryptedString = AES.encrypt(input, secretKey) ;
    	
        int encdecCount = 0;
        while(check)
        {
        	encryptedString = AES.encrypt(input, secretKey) ;
            
        	
            finish = System.currentTimeMillis();
            
            timeElapsed = ((finish - start) / 1000) % 60;
            
            
            if(timeElapsed == 10)
            {
            	System.out.println(encdecCount + " Amount of Data Encrypted in 10 Seconds");
            	check = false;
            }
            
            try {
                FileWriter myWriter = new FileWriter(filename);
                myWriter.write(encryptedString);
                myWriter.close();
                
              } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
              }
            
            //System.out.println(filename);
           
            
            filecount++;
            encdecCount++;
            filename = "enc" + filecount;
            finish = System.currentTimeMillis();
            
        }
        check = true;
        while(check)
        {
        	encryptedString = AES.encrypt(input, secretKey) ;
            
        	
            finish = System.currentTimeMillis();
            
            timeElapsed = ((finish - start) / 1000) % 60;
            
            
            if(timeElapsed == 30)
            {
            	System.out.println(encdecCount + " Amount of Data Encrypted in 30 Seconds");
            	check = false;
            }
            
            try {
                FileWriter myWriter = new FileWriter(filename);
                myWriter.write(encryptedString);
                myWriter.close();
                
              } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
              }
            
            //System.out.println(filename);
           
            
            filecount++;
            encdecCount++;
            filename = "enc" + filecount;
            finish = System.currentTimeMillis();
            
        }
        check = true;
        while(check)
        {
        	encryptedString = AES.encrypt(input, secretKey) ;
            
        	
            finish = System.currentTimeMillis();
            
            timeElapsed = ((finish - start) / 1000) % 60;
            
            
            if(timeElapsed == 45)
            {
            	System.out.println(encdecCount + " Amount of Data Encrypted in 45 Seconds");
            	check = false;
            }
            
            try {
                FileWriter myWriter = new FileWriter(filename);
                myWriter.write(encryptedString);
                myWriter.close();
                
              } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
              }
            
            //System.out.println(filename);
           
            
            filecount++;
            encdecCount++;
            filename = "enc" + filecount;
            finish = System.currentTimeMillis();
            
        }
        boolean tenC = true;
        boolean thC = true;
        long decStart = System.currentTimeMillis();
        long decFinish = System.currentTimeMillis();
        long timeElapsed2 = ((finish - start) / 1000) % 60;
        String decryptedString = AES.decrypt(encryptedString, secretKey);
        int deccount = 0;
        filecount  = 0;
        check = true;
        filename = "enc" + filecount;
        
        while(check)
        {   
        	FileInputStream o = null;
        	String cnts = Files.readString(Paths.get(filename));
            String eninput = cnts;
            
            try {
                o = new FileInputStream(filename);
                
                int x;
                while ((x = o.read()) != -1) {
                	eninput = eninput + x;
                }
             }finally {
                 if (o != null) {
                     o.close();
                  }
                  
               }
        	decryptedString = AES.decrypt(encryptedString, secretKey);
        	
        	decFinish = System.currentTimeMillis();
            
            timeElapsed2 = ((decFinish - decStart) / 1000) % 60;
            
           
            if(timeElapsed2 == 10)
            {
            	System.out.println(deccount + " Amount of Data Decrypted in 10 Seconds");
            	check = false;
            }
            
            try {
                FileWriter myWriter = new FileWriter(filename);
                myWriter.write(input);
                myWriter.close();
                
              } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
              }
            
            //System.out.println(filename);
           
            
            filecount++;
            deccount++;
            filename = "enc" + filecount;
            decFinish = System.currentTimeMillis();
            
        }
        check = true;
        while(check)
        {   
        	FileInputStream o = null;
        	String cnts = Files.readString(Paths.get(filename));
            String eninput = cnts;
            
            try {
                o = new FileInputStream(filename);
                
                int x;
                while ((x = o.read()) != -1) {
                	eninput = eninput + x;
                }
             }finally {
                 if (o != null) {
                     o.close();
                  }
                  
               }
        	decryptedString = AES.decrypt(encryptedString, secretKey);
        	
        	decFinish = System.currentTimeMillis();
            
            timeElapsed2 = ((decFinish - decStart) / 1000) % 60;
            
            
            if(timeElapsed2 == 30)
            {
            	System.out.println(deccount + " Amount of Data Decrypted in 30 Seconds");
            	check = false;
            }
            
            try {
                FileWriter myWriter = new FileWriter(filename);
                myWriter.write(input);
                myWriter.close();
                
              } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
              }
            
            //System.out.println(filename);
           
            
            filecount++;
            deccount++;
            filename = "enc" + filecount;
            decFinish = System.currentTimeMillis();
            
        }
        check = true;
        while(check)
        {   
        	FileInputStream o = null;
        	String cnts = Files.readString(Paths.get(filename));
            String eninput = cnts;
            
            try {
                o = new FileInputStream(filename);
                
                int x;
                while ((x = o.read()) != -1) {
                	eninput = eninput + x;
                }
             }finally {
                 if (o != null) {
                     o.close();
                  }
                  
               }
        	decryptedString = AES.decrypt(encryptedString, secretKey);
        	
        	decFinish = System.currentTimeMillis();
            
            timeElapsed2 = ((decFinish - decStart) / 1000) % 60;
            
           
            if(timeElapsed2 == 45)
            {
            	System.out.println(deccount + " Amount of Data Decrypted in 45 Seconds");
            	check = false;
            }
            
            try {
                FileWriter myWriter = new FileWriter(filename);
                myWriter.write(input);
                myWriter.close();
                
              } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
              }
            
            //System.out.println(filename);
           
            
            filecount++;
            deccount++;
            filename = "enc" + filecount;
            decFinish = System.currentTimeMillis();
            
        }
        
        //System.out.println(input);
        //System.out.println(encryptedString);
        //System.out.println(decryptedString);
        //System.out.println(secretKey);
        
        System.out.println(encdecCount + " File Encrypted");
        System.out.println(deccount + " File Decrypted");
    	
    	

        
        
    }
}
