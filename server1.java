package SCTP1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class server1 {
    public static void main(String args[])throws Exception
    {
        // Create server Socket
        ServerSocket ss = new ServerSocket(888);
        // connect it to client socket
        Socket s = ss.accept();
        System.out.println("Connection established");

        // to send data to the client
        PrintStream ps= new PrintStream(s.getOutputStream());
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        // to read data coming from the client
        BufferedReader br = new BufferedReader( new InputStreamReader(s.getInputStream()));

        // to read data from the keyboard
        BufferedReader kb= new BufferedReader(new InputStreamReader(System.in));

        // server executes continuously
        while (true) {

            String str, str1;

            // repeat as long as the client
            // does not send a null string

            // read from client
            while ((str = br.readLine()) != null) {
                System.out.println("the expression the server gets:");
                System.out.println(str);
                StringTokenizer st=new StringTokenizer(str);
                String first=st.nextToken();
                if(first.charAt(0)!='0'&& first.charAt(0)!='1')// unary operation
                {
                    String operator=first;
                    String operand1=st.nextToken();
                    int a=binaryToDecimal(operand1);
                    int res=0;
                    if(operator.charAt(0)=='+')
                      res=++a;
                    else if(operator.charAt(0)=='-')
                      res=--a;
                    String result=decToBinary(res);
                    System.out.println("Sending result to client");
                    ps.println(result);
                }
                else
                {
                    String operand1=first;
                    String operator=st.nextToken();
                    String operand2=st.nextToken();
                    int a=binaryToDecimal(operand1);
                    int b=binaryToDecimal(operand2);
                    int res=0;
                    if(operator.equals("+"))
                        res=a+b;
                    else if(operator.equals("-"))
                        res=a-b;
                    else if(operator.equals("*"))
                        res=a*b;
                    else if(operator.equals("/"))
                        res=a/b;
                    String result=decToBinary(res);
                    System.out.println("Sending result to client");
                    ps.println(result);
                }
                System.out.println("Enter the expression the server asks");
                String str2=kb.readLine();
                ps.println(str2);
                String str3=br.readLine();
                System.out.println(str3);


            }

            // close connection
            ps.close();
            br.close();
            kb.close();
            ss.close();
            s.close();

            // terminate application
            System.exit(0);

        }
    }
    static int binaryToDecimal(String n)
    {
        int dec_value = 0;
        int base = 1;
        StringBuffer temp = new StringBuffer(n);
        int len=temp.length();int i=0;
        while (i<len) {
            int last_digit = temp.charAt(len-i-1)-'0';
            temp = temp.deleteCharAt(len-i-1);
            dec_value += last_digit * base;
            base = base * 2;
            i++;
        }
        return dec_value;
    }
    static String decToBinary(int n)
    {
        String binaryNum="";
        // counter for binary string
        int i = 0;
        while (n > 0) {
            // storing remainder in binary string
            binaryNum=Integer.toString(n % 2)+binaryNum;
            n = n / 2;
            i++;
        }
        // returning binary string in reverse order
        return binaryNum;
    }

}
