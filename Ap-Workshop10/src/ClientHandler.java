import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private BufferedReader reader = null;
    private PrintWriter writer = null;
    private User user = null;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException io) {
            System.out.println("connection failed");
        }
    }

    public void Start() {
        try {
            writer.println("please enter your id: ");
            writer.flush();
            String id = reader.readLine();
            User user = new User(id);
            this.user = user;
            inform(id + " joined the group");
            String message = reader.readLine();
            while (true) {
                if (message.startsWith("#exit")) {
                    inform(user.getId() + " left the chat");
                    leave();
                    break;
                } else {
                    inform(user.getId() + " : " + message);
                }
                message = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("connection failed");
        }
    }

    public void inform(String message) {
        for (ClientHandler handler : Server.getClientHandlers()) {
            if ((handler.user == null) || (!handler.user.equals(this.user))) {
                handler.writer.println(message);
            }
        }
    }

    public void leave() throws IOException {
        reader.close();
        writer.close();
        if (!socket.isClosed()) {
            socket.close();
        }
    }
    public void sendMessage(String message){
        writer.println(message);
    }
}
