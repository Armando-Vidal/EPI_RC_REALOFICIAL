package classes;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor
{
    static List<ServidorThread> jogadoresConectados = new ArrayList<>();//lista com os jogadores conectados ao servidor
    static mesa mesa;
   
    public static void main(String args[]) throws Exception
    {
        ServerSocket serverSocket;//socketzinho base

        try
        {
            serverSocket = new ServerSocket(5000);

            while(jogadoresConectados.size()<2)//Faz a conexão dos clientes e inicia a thread
            {
                Socket clientSocket = serverSocket.accept();//aceita a conexao do cliente e printa
                System.out.println("Novo jogador conectado.");

                ServidorThread jogadorThread = new ServidorThread(clientSocket, mesa);//cria a thread para cada cliente
                jogadoresConectados.add(jogadorThread);//adiciona o jogador a lista do servidor
                
                Thread thread = new Thread(jogadorThread);//inicia a thread de cada cliente para rodar simultaneamente os 2
                thread.start();
            }
            mesa = new mesa(new baralho());//cria uma unica mesa
            iniciaJogo();//roda o jogo

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void iniciaJogo()
    {

        jogador player1 = new jogador("Jogador1");//cria o jogador 1 e atribui o nome -> mudar para pegar o nome da lista do servidor
        jogador player2 = new jogador("Jogador2");//cria o jogador 2 e atribui o nome -> mudar para pegar o nome da lista do servidor
        mesa.iniciaJogo(player1, player2);

        jogadoresConectados.get(0).setJogador(player1);//aqui eu queria pegar o nome do jogador pela lsta mas n ta funfando
        jogadoresConectados.get(1).setJogador(player2);

    }


}