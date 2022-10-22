import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;




public class Server {
	
	private static File fil = new File("User.txt");
	private static FileWriter fw;
	private static FileReader fr;
	private static BufferedReader br;
	private static String username;
	private static String password;
	private static String returnstring = " ";
	private static String publicKey;
	private static String privateKey;
	
	private static boolean first = true; 
	
	public static ArrayList<String> users = new ArrayList<String>();
	HashMap<String, String> USERS = new HashMap<String, String>();
  public static ArrayList<String> getUsers() {
		return users;
	}

	public static void setUsers(ArrayList<String> users) {
		Server.users = users;
	}
public static boolean isLoginValid(ArrayList e,String user)
{
	if(e.contains(user))
	{
		return false;
	}
	else
	{
		return true;
	}
}
public static void main(String[] args) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        final int PORT = 4040;
        ServerSocket serverSocket = new ServerSocket(PORT);
        String newline = "\n";
        fw = new FileWriter(fil, true);
        fr = new FileReader(fil);
        br = new BufferedReader(fr);
      
        System.out.println("Server started...");
        System.out.println("Wating for clients...");
        
        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread t = new Thread() {
                public void run() {
                    try (
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        Scanner in = new Scanner(clientSocket.getInputStream());
                    ) {
                        while (true) //in.hasNextLine()
                        {
//                        	for(int i = 0 ; i<users.size();i++)
//                        	{
//                        		System.out.println(users.get(i));
//                        	}
                        	
                            String input = in.nextLine();
                            String[] split = input.split(" ");
                            returnstring = "-";

                            if(split[0].equalsIgnoreCase("login"))
                            {
                            	System.out.println("loginserver");
                            	 Scanner myReader = new Scanner(fil);
                                 String data = null;
                                 String key = null;
                                 String[] psearch = null;
                                 while (myReader.hasNextLine()) 
                                 {

                                 	data = myReader.nextLine();
                                 	psearch = data.split(" ");

                                 	if(psearch[0].equals(split[1]))
                                 	{
                                 		key = psearch[1];
                                 		
                                 	}
                                 }
                                 
                                 myReader.close();
                                 String decryptedString = AES.decrypt(split[2], key);

                                 split[2] = decryptedString;
                                 
                            	if(users.contains(split[1]))//users.contains(split[1])
                            	{
                            		break;
                            	}
                            	else 
                            	{
                            		String s;
                                	while((s=br.readLine()) != null)
                                	{
                                		int i = s.indexOf(' ');
                     					int j = s.lastIndexOf(' ');
                                		if(split[1].equals(s.substring(0,i)))
                                		{
                                			if(split[2].equals(s.substring(i+1,j)))
                                			{
                                				users.add(split[1]);
                                			}
                                		}
                                	}
                            	}
	
                            }
                            else if(split[0].equalsIgnoreCase("register"))
                            {
                            	String reg = split[1] + " " + split[2] + " ";
                            	fw.write(newline);
                            	fw.write(reg);
                            	fw.close();
                            	returnstring = "Registered Successfully";
                            	
                            }
                            else
                            {
                            	System.out.println("Client: " + input);
                            }
                            out.println(returnstring);
                        }
                    } catch (IOException e) { }
                    
                }
                
            };
            t.start();
        }
        
    }   
}