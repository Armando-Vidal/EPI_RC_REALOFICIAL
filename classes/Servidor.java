package classes;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor
{
    static List<ServidorThread> jogadoresConectados = new ArrayList<>();
    static mesa mesa;
   
    public static void main(String args[]) throws Exception
    {
        ServerSocket serverSocket;

        try
        {
            serverSocket = new ServerSocket(5000);

            while(jogadoresConectados.size()<2)//Faz a conexão dos clientes e inicia a thread
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo jogador conectado.");

                ServidorThread jogadorThread = new ServidorThread(clientSocket, mesa);
                jogadoresConectados.add(jogadorThread);
                
                Thread thread = new Thread(jogadorThread);
                thread.start();
            }
            mesa = new mesa(new baralho());
            iniciaJogo();

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void iniciaJogo()
    {

        jogador player1 = new jogador("Jogador1");
        jogador player2 = new jogador("Jogador2");
        mesa.iniciaJogo(player1, player2);

        jogadoresConectados.get(0).setJogador(player1);
        jogadoresConectados.get(1).setJogador(player2);

    }


}