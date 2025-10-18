import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadTrataBanco implements Runnable{

    private final Socket socketBanco;
    private final ArrayBlockingQueue<Mensagem> filaPedido;
    private final ArrayBlockingQueue<Mensagem> filaResposta;

    public ThreadTrataBanco(Socket socketBanco, ArrayBlockingQueue<Mensagem> filaPedido, ArrayBlockingQueue<Mensagem> filaResposta) {
        this.socketBanco = socketBanco;
        this.filaPedido = filaPedido;
        this.filaResposta = filaResposta;
    }

    @Override
    public void run(){
        try (
                ObjectOutputStream out = new ObjectOutputStream(socketBanco.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socketBanco.getInputStream());
                ) {

            while (true){
                //Aguarda alguma mensagem do cliente
                Mensagem mensagem = filaPedido.take();
                out.writeObject(mensagem);
                out.flush();

                //Banco envia a resposta e coloca na fila
                Mensagem resposta = (Mensagem) in.readObject();
                System.out.println("Banco enviou: " + resposta.getObjeto());
                filaResposta.put(resposta);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
