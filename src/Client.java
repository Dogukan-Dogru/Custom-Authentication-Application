import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;




public class Client {
	
	
	public static boolean firststart = true;
	
    public static boolean isFirststart() {
		return firststart;
	}

	public static void setFirststart(boolean firststart) {
		Client.firststart = firststart;
	}

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        final String HOST = "127.0.0.1";
        final int PORT = 4040;
        
        System.out.println("Client started.");
        System.out.println("To Register or Login; Please write one of them and write your username and password.");
//        System.out.println("");
        System.out.println("Ex. Login abc 123");
        System.out.println("To exit, please write 'logout' " );
        
        try (
            Socket socket = new Socket(HOST, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner in = new Scanner(socket.getInputStream());
            Scanner s = new Scanner(System.in);
        	FileInputStream xd = null;
        ) {
            while (true) {
                System.out.print("Input: ");
                String input = s.nextLine();
                String[] split = input.split(" ");
                FileInputStream a = null;
                String enc = "";
                if(firststart == true)
                {
                	if(split[0].equalsIgnoreCase("login"))
                    {
                		
                		enc = AES.encrypt(split[2], split[2]);// split[2] = password
                		split[2] = enc;
                		firststart = false;
                		input = split[0] +" "+ split[1] +" "+ split[2];
                			
                    	out.println(input);      		
                		
                    }
                    else if(split[0].equalsIgnoreCase("register"))
                    {
                    	firststart = false;
                    	out.println(input);
                    	
                    }
                    else
                    {
                    	System.out.println("Please login or register");
                    	continue;
                    }
                }
                else
                {	
                	if (split[0].equalsIgnoreCase("logout"))
                    {
                    	out.println(input);
                    	break;
                    }
                	if(split[0].equalsIgnoreCase("register") || split[0].equalsIgnoreCase("login"))
                	{
                		System.out.println("Type something else please.");
                	}
                    else
                    {
                    	out.println(input);
                    }
                }
                System.out.println(in.nextLine());
                
            }
        }
    }
 
}