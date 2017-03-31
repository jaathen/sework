import org.json.JSONObject;

import java.io.PrintStream;
import java.net.Socket;

public class Test {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost", 8080);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("function", "sent");
        jsonObject.put("bid", 1);
        PrintStream printStream = new PrintStream(socket.getOutputStream());
        printStream.println(jsonObject.toString() + "\r\n");
    }
}
