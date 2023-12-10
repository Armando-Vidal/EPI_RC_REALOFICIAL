package classes;

import java.io.*;
import java.net.*;

public class ServidorThread implements Runnable
{
    private Socket socket;
    private ObjectOutputStream saidaobj;
    private ObjectInputStream entradaobj;
    private Mesa mesa;


    public ServidorThread(Socket socket, Mesa mesa)
    {
        this.socket = socket;
        this.mesa = mesa;
    }
    
    @Override
    public void run()
    {
        try
        {
            //leitura e escrita de objetos
            saidaobj = new ObjectOutputStream(socket.getOutputStream()); 
            entradaobj = new ObjectInputStream(socket.getInputStream()); 

            /*//nome do jogador
            String nomeJogador = (String) entradaobj.readObject();
            System.out.println(("Jogador conectado: " + nomeJogador));*/

            while(true)
            {
                
                Object acaoCliente = entradaobj.readObject();

                if(acaoCliente instanceof MensagemJogada)
                {
                    MensagemJogada mensagemJogada = (MensagemJogada) acaoCliente;
                    int indiceCarta = mensagemJogada.getIndiceCarta();
                }

                else
                {

                }

                /*saidaobj.writeObject(mesa.getEstadoJogo());
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
        }
    }

    public void enviarMensagem(String mensagem) {
        try {
            // Envia uma mensagem pelo ObjectOutputStream
            saidaobj.writeObject(mensagem);
            saidaobj.flush(); // Confirma o envio da mensagem
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
