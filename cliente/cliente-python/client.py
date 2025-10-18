import socket
import threading

class ClienteSocket:
    def __init__(self, host="127.0.0.1", port=5000):
        self.host = host
        self.port = port
        self.sock = None
        self.ativo = False

    def conectar(self):
        """Conecta ao servidor"""
        try:
            self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self.sock.connect((self.host, self.port))
            self.ativo = True
            print(f"Conectado ao servidor {self.host}:{self.port}")

            # Inicia thread para escutar mensagens do servidor
            thread_receber = threading.Thread(target=self._receber_respostas, daemon=True)
            thread_receber.start()
        except ConnectionRefusedError:
            print("Erro Servidor não está aceitando conexões.")
        except Exception as e:
            print(f"Erro ao conectar {e}")

    def _receber_respostas(self):
        """Escuta respostas do servidor"""
        while self.ativo:
            try:
                data = self.sock.recv(1024)
                if not data:
                    print("\nServidor encerrou a conexão")
                    self.ativo = False
                    break
                print("\n", data.decode().strip())
            except Exception as e:
                print(f"Erro ao receber dados {e}")
                break

    def enviar(self, mensagem: str):
        """Envia mensagem ao servidor"""
        if not self.ativo:
            print("Erro Não há conexão ativa.")
            return

        try:
            # Scanner.nextLine() do Java precisa do \n
            self.sock.sendall((mensagem + "\n").encode())

            if mensagem == "1":
                print("Encerrando conexão...")
                self.fechar()
        except Exception as e:
            print(f"Erro ao enviar dados {e}")

    def fechar(self):
        """Fecha o socket"""
        self.ativo = False
        if self.sock:
            try:
                self.sock.close()
            except Exception:
                pass
        print("Conexão encerrada")

