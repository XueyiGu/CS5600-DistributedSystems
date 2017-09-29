/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccs.xueyi.mavenclient;

import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ceres
 */
public class Tester {
    public static void main(String[] args){
        int threadNum = 10;
        int iterationNum = 100;
        String ip = null;
        String port = null;
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the client parameters \n"
                    + "[thread_num] [iteration_num] [server_ip] [server_port] \n"
                    + "Parameters are separeted by space.");
        while (scanner.hasNext()) {
            String[] strs = scanner.nextLine().split("\\s");
            if (strs != null && strs.length == 4) {
                threadNum = Integer.parseInt(strs[0]);
                iterationNum = Integer.parseInt(strs[1]);
                ip = strs[2];
                port = strs[3];
                break;
            }else{
                System.out.println("Please enter the parameters in correct format");
                return;
            }
        }
        String url = ip + ":" + port;
        ThreadProcessor processor = new ThreadProcessor(url, threadNum, iterationNum);
        
        try {
            processor.startThread();
        } catch (TimeoutException ex) {
            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
