package classes;

import java.io.*;
import java.net.*;

public class ServerUNO
{
    public static void main(String[] args)
    {
        ServerSocket serverSocket = null;
        

        try
        {
            //cria o socket do servidor
            serverSocket = new ServerSocket(5000);
            System.out.println("Servidor UNO iniciado...");

            //aceita o jogador 1
            Socket jogador1Socket = serverSocket.accept();
            System.out.println("Jogador 1 conectado " + jogador1Socket.getInetAddress().getHostName());

            //aceita o jogador 2
            Socket jogador2Socket = serverSocket.accept();
            System.out.println("Jogador 2 conectado " + jogador2Socket.getInetAddress().getHostName());
                
            System.out.println("Temos 2 jogadores, vamos iniciar a partida!");
            
            // Cria uma instância da mesa e inicia o jogo
            baralho baralho = new baralho();
            mesa mesa = new mesa(baralho);
            
            // Cria threads para o jogo
            GameThread jogador1Thread = new GameThread(jogador1Socket, mesa, "Jogador 1");
            GameThread jogador2Thread = new GameThread(jogador2Socket, mesa, "Jogador 2");

            //Inicia as threads
            jogador1Thread.start();
            jogador2Thread.start();

        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                //fecha o socker ao fim do jogo
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

class GameThread extends Thread
{
    private Socket clientSocket;
    private mesa mesa;
    private jogador jogador1;
    private jogador jogador2;
    
    //cria atributos para troca de mensagem com o cliente
    private ObjectOutputStream saidaMsg;
    private ObjectInputStream entradaMsg;

    public GameThread(Socket clientSocket, mesa mesa, String nomeJogador)
    {
        this.clientSocket = clientSocket;
        this.mesa = mesa;


        //criamos a partida e printamos uma mensagem indicando isso
        try
        {
            this.saidaMsg = new ObjectOutputStream(clientSocket.getOutputStream());
            this.entradaMsg = new ObjectInputStream(clientSocket.getInputStream());
            saidaMsg.writeObject(" Jogo criado!");

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //Função para o envio de mensagens para o cliente
    private void mandarMsg(Object msg)
    {
        try
        {
            saidaMsg.writeObject(msg);
            saidaMsg.flush();//garante o envio da mensagem
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //Função para o recebimento de mensagens do cliente
    private Object receberMsg()
    {
        try
        {
            return entradaMsg.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    

    @Override
    public void run()
    {
        try 
        {
            mandarMsg("Bem-vindo ao jogo UNO!");

            while (true) 
            {

                // Obter o jogador atual
                jogador vez = mesa.getJogadorAtual();

                if(vez == null)//se nao tem vez, inicia o jogo e atribui a vez para o jogador 1, de acordo com a lógica da mesa
                {
                    mesa.iniciaJogo(jogador1, jogador2);
                }

                else if (vez.nome.equals("Jogador 1"))//manda msg pro jogador 1
                {
                    mandarMsg("Sua vez!");
                    mandarMsg("Carta no topo: " + mesa.getCartaNoTopo());
                    mandarMsg("Suas cartas: " + mesa.getMao());

                    mandarMsg("Escolha uma carta para jogar (índice) ou digite -1 para comprar uma carta:");
                    int escolha = (int) receberMsg();

                    // Lógica para processar a escolha do jogador
                    if (escolha >= 0 && escolha < mesa.getMao().size())
                    {
                        mesa.processarAcao(mesa.getMao().get(escolha));
                        mandarMsg("A carta jogada: " + mesa.getMao().get(escolha));
                    } else if (escolha == -1)
                    {
                        mesa.compraCarta();
                        mandarMsg("Você comprou uma carta, sua mão agora é: " + mesa.getMao());
                    } else
                    {
                    mandarMsg("Carta inválida, selecione uma carta com mesmo símbolo ou mesma cor.");
                    }
                }
                else//manda msg pro jogador 2
                {
                    mandarMsg("Sua vez!");
                    mandarMsg("Carta no topo: " + mesa.getCartaNoTopo());
                    mandarMsg("Suas cartas: " + mesa.getMao());

                    mandarMsg("Escolha uma carta para jogar (índice) ou digite -1 para comprar uma carta:");
                    int escolha = (int) receberMsg();

                    // Lógica para processar a escolha do jogador
                    if (escolha >= 0 && escolha < mesa.getMao().size())
                    {
                        mesa.processarAcao(mesa.getMao().get(escolha));
                        mandarMsg("A carta jogada: " + mesa.getMao().get(escolha));
                    } else if (escolha == -1)
                    {
                        mesa.compraCarta();
                        mandarMsg("Você comprou uma carta, sua mão agora é: " + mesa.getMao());
                    } else
                    {
                    mandarMsg("Carta inválida, selecione uma carta com mesmo símbolo ou mesma cor.");
                    }
                }
            }
        } finally
        {
            // fecha os sockets ao fim do jogo
            try
            {
                clientSocket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
