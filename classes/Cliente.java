package classes;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente
{
    public static void main(String args[]) throws Exception
    {    
        try
        {       
            Socket socket = new Socket("localhost", 5000); //criação do socket
        
            ObjectOutputStream saidaObj = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entradaObj = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.println("Insira o nome do jogador: ");
            String nomeJogador = scanner.nextLine();
            
            saidaObj.writeObject(nomeJogador); // enviar o nome do jogador para o servidor
            saidaObj.flush();
            
            System.out.println("Jogador " + nomeJogador + "conectado!");

            ClienteThread clienteThread = new ClienteThread(nomeJogador, socket);//abrimos a thread de cada jogador
            Thread thread = new Thread(clienteThread);
            thread.start();

            //agora o codigo é realizado em ClienteThread

        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
