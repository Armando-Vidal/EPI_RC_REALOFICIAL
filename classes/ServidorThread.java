package classes;

import java.io.*;
import java.net.*;

public class ServidorThread implements Runnable
{
    private Socket socket;
    private Mesa mesa;
    private ObjectOutputStream saidaobj;
    private ObjectInputStream entradaobj;

    public ServidorThread(Socket socket, Mesa mesa)
    {
        this.socket = socket;
        this.mesa = mesa;

        try
        {
            saidaobj = new ObjectOutputStream(socket.getOutputStream());
            entradaobj = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    
    @Override
    public void run()
    {
        try
        {
            //Informações iniciais do jogador

            saidaobj.writeObject("Olá " + socket.getInetAddress());

            while(true)
            {
                //esperando a jogada do cliente
                Object acaoCliente = entradaobj.readObject();

                if(acaoCliente instanceof String)
                {
                    String command = (String) acaoCliente;

                    if(command.equals("Joga carta")) //adaptar de acordo com o comando de jogar carta
                    {
                        //tira a carta da mao do jogador, coloca ela no topo da mesa, confirma se posui habilidade e passa a vez
                        attUniversal(); // implementar função para atualizar o estado do jogo apos cada jogada
                    }

                    else if (command.equals("Compra carta"))
                    {
                        //pega a primeira carta do baralho e adiciona a mao do jogador, pergunta se vai jogar ou manter a carta e continua de acordo com a escolha do cliente
                        attUniversal();   
                    }
                }

                /*if(acaoCliente instanceof MensagemJogada)
                {
                    MensagemJogada mensagemJogada = (MensagemJogada) acaoCliente;
                    int indiceCarta = mensagemJogada.getIndiceCarta();
                }

                else
                {

                }

                saidaobj.writeObject(mesa.getEstadoJogo());
                saidaobj.flush();

                Object jogada = entradaobj.readObject(); //le a ação do jogador

                //realiza a jogada para a mesa e atualiza o estado do jogo
                if (jogada instanceof Carta)
                {
                    Carta carta = (Carta) jogada;
                    try {
                        mesa.jogadorJogaCarta(carta);
                    } catch (WrongCardException e)
                    {
                        // Envia mensagem de erro para o jogador
                        saidaobj.writeObject("Erro: " + e.getMessage());
                        saidaobj.flush();
                    }
                }
                else if (jogada instanceof Integer && (Integer) jogada == -1)
                {
                    //Jogador comprou uma carta
                    mesa.jogadorCompraCarta();
                }*/

            }

        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }finally
        {
            try
            {
                // Fecha recursos quando a thread do jogador termina
                saidaobj.close();
                entradaobj.close();
                socket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void attUniversal()
    {
        try
        {
            for(ServidorThread jogador : Servidor.jogadoresConectados)
            {
                jogador.saidaobj.writeObject(mesa); //mensagem com o status atual da mesa (carta de cima, mao dos jogadores)
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
