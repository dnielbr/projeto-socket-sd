import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadTrataCliente implements Runnable{

    private final Socket socketCliente;
    private final ArrayBlockingQueue<Mensagem> filaPedido;
    private final ArrayBlockingQueue<Mensagem> filaResposta;

    public ThreadTrataCliente(Socket socketCliente, ArrayBlockingQueue<Mensagem> filaPedido, ArrayBlockingQueue<Mensagem> filaResposta) {
        this.socketCliente = socketCliente;
        this.filaPedido = filaPedido;
        this.filaResposta = filaResposta;
    }

    @Override
    public void run() {

        try(
                Scanner in = new Scanner(socketCliente.getInputStream());
                PrintStream out = new PrintStream(socketCliente.getOutputStream());
                ){
            System.out.println("Novo cliente: " + socketCliente.getInetAddress());
            out.println(getMenu());

            while (true) {
                if (!in.hasNextLine()) {
                    System.out.println("Cliente desconectado: " + socketCliente.getInetAddress());
                    break;
                }

                String op = in.nextLine();
                System.out.println("Cliente pediu: " + op);

                if (op.equalsIgnoreCase("1")) {
                    System.out.println("Cliente desconectado: " + socketCliente.getInetAddress());
                    out.println("Conexão encerrada");
                    socketCliente.close();
                    break;
                }else if(op.equalsIgnoreCase("2")){
                    filaPedido.put(new Mensagem(2, null));
                    out.println(filaResposta.take().getObjeto());
                }else if(op.equalsIgnoreCase("3")){
                    filaPedido.put(new Mensagem(3, null));
                    out.println(filaResposta.take().getObjeto());
                }else{
                    out.println("Entrada inválida, use 1 - 2 - 3");
                }

                out.println(getMenu());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao tratar cliente: " + e.getMessage());
        }

    }

    public String getMenu(){

        StringBuilder menu = new StringBuilder();

        menu.append("\n=== Bem-vindo à Matrix ===\n");
        menu.append("1 - Sair\n");
        menu.append("2 - Listar Personagens\n");
        menu.append("3 - Listar habilidades\n");
        menu.append("Escolha uma opção: ");

        return menu.toString();
    }
}
