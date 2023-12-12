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
            // Conecta ao servidor
            Socket socket = new Socket("localhost", 5000);

            // Configura entrada e saída
            ObjectOutputStream saidaMsg = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entradaMsg = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);//pegar o nome do jogador
            System.out.println("Insira o nome do jogador: ");
            String nomeJogador = scanner.nextLine();
            saidaMsg.writeObject(nomeJogador);
            saidaMsg.flush();


            while (true)
            {
                // Leitura da jogada
                System.out.print("Digite sua jogada: ");
                String jogada = scanner.nextLine();

                // Envia a jogada para o server
                saidaMsg.writeObject(jogada);
                saidaMsg.flush();

                // Recebe mensagens
                Object msg = entradaMsg.readObject();
                System.out.println("Mensagem do servidor: " + msg);
            }

        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
