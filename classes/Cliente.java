package classes;
import java.io.*;
import java.net.*;
import java.util.Scanner;
//codigo de cliente geral 
public class Cliente
{
    public static void main(String args[]) throws Exception
    {    
        try
        {       
            Socket socket = new Socket("localhost", 5000); //criação do socket
        
            ObjectOutputStream saidaObj = new ObjectOutputStream(socket.getOutputStream());//declaração de saida/entrada de mensagens que ainda não esta sendo aplicada pq o cliente
            ObjectInputStream entradaObj = new ObjectInputStream(socket.getInputStream());//so serve pra colocar o nome do jogador, e depois nem isso pq ta atribuindo o nome la no server

            Scanner scanner = new Scanner(System.in);//pegar o nome do jogador
            System.out.println("Insira o nome do jogador: ");
            String nomeJogador = scanner.nextLine();
            
            saidaObj.writeObject(nomeJogador); // enviar o nome do jogador para o servidor, isso é feito certo uffa
            
            System.out.println("Jogador " + nomeJogador + " conectado!");//printando o nome do cliente pra ter um controle

            ClienteThread clienteThread = new ClienteThread(nomeJogador, socket);//criamos a thread de cada jogador
            Thread thread = new Thread(clienteThread);//comeca a thread, mas ainda n tem a aplicação de rodar sincronizado, krl falta mta coisa
            thread.start();

            //agora o codigo é realizado em ClienteThread

        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
