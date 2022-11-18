package SCTP1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class client1 {
    public static void main(String args[])
            throws Exception {
        InetAddress ip = InetAddress.getLocalHost();
        Socket s = new Socket(ip, 888);

        //object to send data to the server
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        //object  to read data coming from the server
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        // to read data from the keyboard
        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
        String op,str, str1;
        // repeat as long as exit
        // is not typed at client
        outer: while (true) {
            System.out.println("Enter the expression client asks ");
            str=kb.readLine();
            // send to the server
            dos.writeBytes(str + "\n");
            if(str==null)
                break;
            // receive from the server
            str1 = br.readLine();
            System.out.println(str1);

            // second way of communication
            System.out.println("the expression the client gets:");
            String str2=br.readLine();
            if(str2==null)
                break;
            System.out.println(str2);
            StringTokenizer st=new StringTokenizer(str2);
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
                System.out.println("Sending result to server");
                dos.writeBytes(result+"\n ");
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
                System.out.println("Sending result to server");
                dos.writeBytes(result+"\n");
            }
        }

        // close connection.
        dos.close();
        br.close();
        kb.close();
        s.close();
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

        // printing binary string in reverse order
        return binaryNum;
    }
}
