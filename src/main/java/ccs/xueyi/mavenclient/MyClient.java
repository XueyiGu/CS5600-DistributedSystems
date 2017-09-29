/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccs.xueyi.mavenclient;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ceres
 */
public class MyClient {
    public String url;
    private final Client client;
    private final WebTarget webTarget;
    
    public MyClient(String url){
        this.url = url;
        client = ClientBuilder.newClient();
        webTarget = 
//            client.target("http://34.214.49.130:8080/MavenServer/").path("webapi/myresource");
            client.target("http://" + url + "/MavenServer/").path("webapi/myresource");
    }
  
    public <T> T postText(Object requestEntity, Class<T> responseType) throws
            ClientErrorException{
        return webTarget.request(MediaType.TEXT_PLAIN)
                        .post(javax.ws.rs.client.Entity.entity(requestEntity,
                                javax.ws.rs.core.MediaType.TEXT_PLAIN),
                                responseType);
    }
    
    public String getStatus() throws ClientErrorException{
        WebTarget resource = webTarget;
        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    } 
}
