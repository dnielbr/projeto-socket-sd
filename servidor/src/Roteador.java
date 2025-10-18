import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class Roteador {

    private final ArrayBlockingQueue<Mensagem> filaPedido;
    private final ArrayBlockingQueue<Mensagem> filaResposta;

    public Roteador(){
        this.filaPedido = new ArrayBlockingQueue<>(10);
        this.filaResposta = new ArrayBlockingQueue<>(10);
    }

    public void start(){
        try{
            //Conectar ao Servidor do Banco
            Socket socketBanco = new Socket("localhost", 12340);
            System.out.println("Roteador conectado ao Banco de Dados");

            //Thread que se comunica com o Banco
            Thread threadBanco = new Thread(new ThreadTrataBanco(socketBanco, filaPedido, filaResposta));
            threadBanco.start();

            // Iniciar o servidor para os clientes java e python
            try (ServerSocket servidor = new ServerSocket(12345)){
                System.out.println("Roteador aguardando clientes na porta 12345...");

                while (true){
                    Socket cliente = servidor.accept();

                    //todo ao se conectar ja devolver as opcoes para o cliente

                    //Thread que se comunica com os clientes
                    Thread threadCliente = new Thread(new ThreadTrataCliente(cliente, filaPedido, filaResposta));
                    threadCliente.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        Roteador roteador = new Roteador();
        roteador.start();
    }
}
