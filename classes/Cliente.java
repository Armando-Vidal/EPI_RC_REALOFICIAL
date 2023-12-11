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
            
            ClienteThread clienteThread = new ClienteThread(nomeJogador);//abrimos a thread de cada jogador
            Thread thread = new Thread(clienteThread);
            thread.start();

            //precisa implementar atualizações do cliente para o servidor ainda

        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
