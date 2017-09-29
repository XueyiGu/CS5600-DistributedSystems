/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccs.xueyi.mavenclient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ceres
 */
public class ClientThread extends Thread{

    private int iterationNum = 100;
    private int requestCount = 0;
    private int successCount = 0;
    private String url;
    private List<Long> latencies = new ArrayList<>();
    private CyclicBarrier barrier;
    
    public ClientThread(String url, int iterationNum, CyclicBarrier barrier){
        this.url = url;
        this.iterationNum = iterationNum;
        this.barrier = barrier;
    }
    
    @Override
    public void run() {
        try {
            //To call the HTTP endpoints iterationNum times
            MyClient myClient = new MyClient(url);
            for(int i = 0; i < iterationNum; i++){
                long startTime = System.currentTimeMillis();
                doGet(myClient);
                long latency = System.currentTimeMillis() - startTime;
                latencies.add(latency);
                
                startTime = System.currentTimeMillis();
                doPost(myClient);
                latency = System.currentTimeMillis() - startTime;
                latencies.add(latency);
            }
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void doGet(MyClient client){
        String get = client.getStatus();
        requestCount++;
        
        if("Got it!".equals(get)){
            successCount++;
        }
    }
    
    private void doPost(MyClient client){
        int length = client.postText("Hello World", Integer.class);
        requestCount++;
        
        if(length == 11){
            successCount++;
        }
    }
    
    public int getSuccessCount(){
        return successCount;
    }
    
    public int getRequestCount(){
        return requestCount;
    }
    
    public List<Long> getLatency(){
        return latencies;
    }
}
