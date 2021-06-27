package Boxinator;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;



public class HTTPServer {
    public static final int MYPORT = 8080;
    public static final int bytes = 9000;

    public static void main(String[] args) throws IOException {

        try {
            ServerSocket server = new ServerSocket(MYPORT);
            System.out.println("Server starting");
            while (true) {
                // accept the connection when there is one.
                Socket s = server.accept();
                System.out.println("Connecting " + s.getRemoteSocketAddress());

                // get the output stream and input stream
                OutputStream os = s.getOutputStream();
                InputStream is = s.getInputStream();

                // creates and starts the thread.
                Thread t = new ClientHandler(s, os, is, bytes, args[0]);
                t.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    File fil;
    Socket s;
    OutputStream os;
    InputStream is;
    int bufferval;
    String filePath;

    public ClientHandler(Socket s, OutputStream os, InputStream is, int buff, String filePath) {
        bufferval = buff;
        this.is = is;
        this.os = os;
        this.s = s;
        this.filePath = filePath;
    }



    public void run() {
        byte[] buff = new byte[bufferval];

        try {

            int br = is.read(buff);


            // connection is terminated if br equals -1
            if (br != -1) {

                String request = new String(buff, 0, br);
                String[] requestSplit = request.split("\\r?\\n");
                String[] header = requestSplit[0].split(" ");

                if (header[0].equals("OPTIONS")) {

                    String response = "HTTP/1.1 200 OK\r\n " + "Access-Control-Allow-Origin: *\r\n" + "Access-Control-Allow-Methods: POST, GET \r\n"+"Access-Control-Allow-Headers: Content-Type, x-requested-with\r\n" +
                            "Access-Control-Max-Age: 86400\r\n\r\n";
                    sendHttp(response.getBytes());
                }
                else if(header[1].equals("/boxes")){
                    if(header[0].equals("POST")){

                        for (int i = 0; i < requestSplit.length; i++) {
                            System.out.println(requestSplit[i]);
                        }

                        String values = requestSplit[requestSplit.length -1];

                        if (!isJson(values)){
                            System.out.print(is.available());
                            br = is.read(buff);
                            values = new String(buff, 0, br);
                        }

                        try {
                            JSONObject jsonObject = new JSONObject(values);
                            System.out.println(jsonObject.get("name"));
                            System.out.println(jsonObject);
                            database_connector.insertData(jsonObject.get("name").toString(),Integer.parseInt(jsonObject.get("weight").toString()),jsonObject.get("color").toString(),jsonObject.get("countryId").toString());

                            String response = "HTTP/1.1 200 OK\r\n\r\n";
                            sendHttp(response.getBytes());
                        }catch (JSONException err){
                            System.err.print("Error: " + values);
                            String response = "HTTP/1.1 500 Internal Server Error \r\n\r\n";
                            sendHttp(response.getBytes());
                        }
                    }
                    else if(header[0].equals("GET")){
                        try{
                        ResultSet rs = database_connector.getAllData();
                        JSONArray array = new JSONArray();
                        while (rs.next()) {
                            JSONObject record = new JSONObject();
                            //Inserting key-value pairs into the json object
                            record.put("id", rs.getInt("idboxes"));
                            record.put("name", rs.getString("name"));
                            record.put("weight", rs.getInt("weight"));
                            record.put("color", rs.getString("color"));
                            record.put("country", rs.getString("country"));
                            record.put("price", rs.getInt("price"));
                            array.put(record);
                        }
                        byte[] bytes = array.toString().getBytes("utf-8");
                        String response = "HTTP/1.1 200 OK\r\n" + "Content-Type: application/json\n" +
                                "Accept: application/json \r\n"
                                + "Content-Length: " + bytes.length + "\r\n\r\n";
                        sendHttp(response.getBytes());
                        sendHttp(bytes);
                        sendHttp("\r\n".getBytes());
                        }catch (Exception e){
                            e.printStackTrace();
                            String response = "HTTP/1.1 500 Internal Server Error \r\n\r\n";
                            sendHttp(response.getBytes());
                        }
                    }
                }
                // this is for when the user has a windows machine and have \ in the filepath istead of /
                //requestSplit[1] = requestSplit[1].replace('/', '\\');
                else {
                    String[] headerSplit = header[1].split("\\?");
                    fil = new File(filePath + headerSplit[0]);

                    // if this is Directory add index.html on the end of the file path
                    if (fil.isDirectory()) {

                        fil = new File(filePath + headerSplit[0] + "\\index.html");
                        FileInputStream filR = new FileInputStream(fil);
                        String contentType = getContentType(fil.getName());

                        String response = "HTTP/1.1 200 OK\r\n" + "Content-Type:" + contentType + "\r\n"
                                + "Content-Length: " + fil.length() + "\r\n\r\n";
                        sendHttp(response.getBytes());
                        sendHttp(filR.readAllBytes());
                        sendHttp("\r\n".getBytes());
                    }
                    // check that the file exists if so send that file back.
                    else if (fil.exists()) {
                        String contentType = getContentType(fil.getName());
                        FileInputStream filR = new FileInputStream(fil);

                        String response = "HTTP/1.1 200 OK\r\n" + "Content-Type:" + contentType + "\r\n"
                                + "Content-Length: " + fil.length() + "\r\n\r\n";
                        sendHttp(response.getBytes());
                        sendHttp(filR.readAllBytes());
                        sendHttp("\r\n".getBytes());
                    }
                    // if the file does not exist send back 404 code
                    else{
                        sendHttp("HTTP/1.1 404 not found\r\n\r\n".getBytes());
                    }

                }
            }
            // when all of this is done close everything
            s.close();
            os.close();
            is.close();

            System.out.println("closing connection!");
        } catch (SocketException e) {
            System.out.print("Connection closed sooner then expected.");
        } catch (Exception e) {
            sendHttp("HTTP/1.1 500 Internal server error \r\n\r\n".getBytes());
            e.printStackTrace();
        }
    }

    public boolean isJson(String str){
        try{
            JSONObject json = new JSONObject(str);
        }catch (Exception e ){
            return false;
        }
        return true;
    }
    // get the content type of the file.
    public String getContentType(String name) {
        String[] nameSplit = name.split("\\.");
        if (nameSplit[nameSplit.length - 1].equals("png")) {
            return "image/png";
        } else if (nameSplit[nameSplit.length - 1].equals("js")){
            return "text/javascript";
        }else if(nameSplit[nameSplit.length-1].equals("css")){
            return "text/css";
        }

        return "text/html";

    }
    // basic function to send bytes.
    public void sendHttp(byte[] code) {
        try {
            os.write(code);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}