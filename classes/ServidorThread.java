package classes;

import java.io.*;
import java.net.*;

public class ServidorThread implements Runnable
{
    private Socket socket;
    private mesa mesa;
    private ObjectOutputStream saidaobj;
    private ObjectInputStream entradaobj;
    private jogador jogador;

    public ServidorThread(Socket socket, mesa mesa)
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
            while(true)
            {
                //esperando a jogada do cliente
                Object acaoCliente = entradaobj.readObject();

                if(acaoCliente instanceof String)
                {
                    String command = (String) acaoCliente;
                    try
                    {
                        mesa.processarAcao(command);
                        // Atualiza todos os clientes após cada ação
                        attUniversal();
                    } catch (WrongCardException e)
                    {
                        System.out.println("Carta inválida, selecione uma carta com mesmo símbolo ou mesma cor.");
                    }
                }
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

    public void setJogador(jogador jogador)//setamos o jogador para aplicação dos nomes 
    {
        this.jogador = jogador;
    }


    public void attUniversal() // atualização para todos os
    {
        try
        {
            for(ServidorThread jogador : Servidor.jogadoresConectados)
            {
                jogador.saidaobj.writeObject(mesa.getCartaNoTopo()); //mensagem com o status atual da mesa (carta de cima, mao dos jogadores)
                jogador.saidaobj.flush();
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}