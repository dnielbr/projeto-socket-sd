from client import ClienteSocket

def main():
    cliente = ClienteSocket(host="127.0.0.1", port=12345)
    cliente.conectar()

    try:
        while cliente.ativo:
            msg = input().strip()
            cliente.enviar(msg)

    except KeyboardInterrupt:
        print("\nEncerrado manualmente pelo usuário")
        cliente.fechar()

    except Exception as e:
        print(f"Erro inesperado: {e}")

if __name__ == "__main__":
    main()
