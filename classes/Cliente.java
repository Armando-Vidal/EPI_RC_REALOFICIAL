package classes;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente
{
    public static void main(String[] args) {
        //Especificação do endereço IP e porta do servidor
        try
        {
            //Pega o nome do jogador
            Scanner scanner = new Scanner(System.in);
            System.out.println("Insira o nome do jogador: ");
            String nomeJogador = scanner.nextLine();
                        
            // Conecta ao servidor
            Socket socket = new Socket("localhost", 5000);

            // Configura entrada e saída de mensagens
            ObjectOutputStream saidaMsg = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entradaMsg = new ObjectInputStream(socket.getInputStream());

            saidaMsg.writeObject(nomeJogador);//envia o nome do jogador para o servidor
            saidaMsg.flush();//garante o envio da mensagem
            
            ClienteThread jogador = new ClienteThread(nomeJogador, saidaMsg, entradaMsg);//inicia a thread do cliente
            jogador.start();
            
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
