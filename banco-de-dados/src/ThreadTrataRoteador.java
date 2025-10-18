import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadTrataRoteador implements Runnable{

    private final Socket socketRoteador;
    private final BancoRepositorio repositorio;

    public ThreadTrataRoteador(Socket socketRoteador, BancoRepositorio repositorio){
        this.socketRoteador = socketRoteador;
        this.repositorio = repositorio;
    }

    @Override
    public void run() {

        try (
                ObjectInputStream in = new ObjectInputStream(socketRoteador.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socketRoteador.getOutputStream());
                ) {
            System.out.println("Conexão ativa com o roteador: " + socketRoteador.getInetAddress());

            while (true){
                Mensagem mensagem = (Mensagem) in.readObject();
                System.out.println("Roteador pediu: " + mensagem.getOpcao());

                switch (mensagem.getOpcao()){
                    case 2:
                        out.writeObject(new Mensagem(2, repositorio.listarPersonagens()));
                        break;
                    case 3:
                        out.writeObject(new Mensagem(3, repositorio.listarHabilidades()));
                        break;
                    default:
                        out.writeObject(new Mensagem(0, "Comando invalido"));
                }

                out.flush();
            }
        } catch (EOFException e) {
            System.out.println("Roteador desconectado: " + socketRoteador.getInetAddress());;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
