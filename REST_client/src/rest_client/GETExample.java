/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest_client;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An example showing how to send HTTP GET and read the response from the server
 */
public class GETExample {

    public static void main(String[] args) {
        GETExample example = new GETExample("104.248.47.74", 80);
        example.doExampleGet();
    }

    private String BASE_URL; // Base URL (address) of the server

    /**
     * Create an HTTP GET example
     *
     * @param host Will send request to this host: IP address or domain
     * @param port Will use this port
     */
    public GETExample(String host, int port) {
        BASE_URL = "http://" + host + ":" + port + "/";
    }

    /**
     * Send an HTTP GET to a specific path on the web server
     */
    public void doExampleGet() {
        // TODO: change path to something correct
        sendGet("dkrest/test/get2");
    }

    /**
     * Send HTTP GET
     *
     * @param path     Relative path in the API.
     */
    private void sendGet(String path) {
        try {
            String url = BASE_URL + path;
            URL urlObj = new URL(url);
            System.out.println("Sending HTTP GET to " + url);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Server reached");
                // Response was OK, read the body (data)
                InputStream stream = con.getInputStream();
                String responseBody = convertStreamToString(stream);
                stream.close();
                System.out.println("Response from the server:");
                parseJSON(responseBody);
                //System.out.println(responseBody);
            } else {
                String responseDescription = con.getResponseMessage();
                System.out.println("Request failed, response code: " + responseCode + " (" + responseDescription + ")");
            }
        } catch (ProtocolException e) {
            System.out.println("Protocol not supported by the server");
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void parseJSON(String jsonObjString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonObjString);
            if(jsonObject.has("a")) {
                int aNum = jsonObject.getInt("a");
                System.out.println("The object contains field 'a' with value "
                + aNum);
            } 
            if(jsonObject.has("b")) {
                int bNum = jsonObject.getInt("b");
                System.out.println("The object contains field 'b' with value "
                + bNum);
            }
            if(jsonObject.has("c")) {
                int cNum = jsonObject.getInt("c");
                System.out.println("The object contains field 'c' with value "
                + cNum);
            }
            
        } catch (JSONException e){
            System.out.println("Got exception in JSON parsing: " + e.getMessage());
        }
        System.out.println("");
    }

    /**
     * Read the whole content from an InputStream, return it as a string
     * @param is Inputstream to read the body from
     * @return The whole body as a string
     */
    private String convertStreamToString(InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                response.append('\n');
            }
        } catch (IOException ex) {
            System.out.println("Could not read the data from HTTP response: " + ex.getMessage());
        }
        return response.toString();
    }
}

