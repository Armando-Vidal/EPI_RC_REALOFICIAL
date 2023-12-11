package classes;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor
{
    static List<ServidorThread> jogadoresConectados = new ArrayList<>();
    static Mesa mesa;
   
    public static void main(String args[]) throws Exception
    {
        ServerSocket serverSocket;

        try
        {
            serverSocket = new ServerSocket(5000);
            mesa = new Mesa(new Baralho());

            while(true)//Faz a conexão dos clientes e inicia a thread
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo jogador conectado.");

                ServidorThread jogadorThread = new ServidorThread(clientSocket, mesa);
                jogadoresConectados.add(jogadorThread);
                
                Thread thread = new Thread(jogadorThread);
                thread.start();
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }


}
