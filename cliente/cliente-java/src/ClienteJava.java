import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteJava {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 12345);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner sc = new Scanner(System.in)
        ) {

            System.out.println("Cliente Java conectado ao Roteador!");

            while (true) {

                String menu = in.readLine();
                while (in.ready()){
                    menu += "\n" + in.readLine();
                }
                System.out.println(menu);

                String linha = sc.nextLine();
                out.println(linha);

                String resposta = in.readLine();
                System.out.println("Servidor respondeu: \n" + resposta);

                if (linha.equalsIgnoreCase("1")){
                    out.println("Cliente Desconectado");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
