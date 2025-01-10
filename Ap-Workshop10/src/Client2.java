import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class Client2 {
        public static void main(String[] args) throws IOException {
            Socket socket = null;
            PrintWriter writer = null;
            BufferedReader reader = null;

            try {
                socket = new Socket("127.0.0.1", 8080);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);
                Chat userInput = new Chat(socket, writer);
                userInput.start();
            }
            catch (Exception e){
                System.out.println("connection failed. try again");
            }

            while (true){
                String message = reader.readLine();
                if (message == null){
                    break;
                }
                System.out.println(message);
            }
        }
    private static void leave(Socket socket, PrintWriter p, BufferedReader b){
        try {
            b.close();
            p.close();
            if (!socket.isClosed())
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    }
