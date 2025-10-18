import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BancoDeDados {

    private final BancoRepositorio repositorio;

    public BancoDeDados(){
        this.repositorio = new BancoRepositorio();
    }

    public void start() {
        try (ServerSocket servidor = new ServerSocket(12340)) {
            System.out.println("Banco de Dados ouvindo na porta 12340...");

            //Aguarda a conexão do roteador
            Socket roteador = servidor.accept();

            //Thread que trata a conexao do roteador
            Thread threadTrataRoteador = new Thread(new ThreadTrataRoteador(roteador, repositorio));
            threadTrataRoteador.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BancoDeDados bancoDeDados = new BancoDeDados();
        bancoDeDados.start();
    }

}
