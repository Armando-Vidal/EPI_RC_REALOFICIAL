package classes;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteThread extends Thread
{
    private String nomeJogador;
    private ObjectOutputStream saidaMsg;
    private ObjectInputStream entradaMsg;
    private Scanner scanner;

    public ClienteThread(String nomeJogador, ObjectOutputStream saidaMsg, ObjectInputStream entradaMsg) {
        this.nomeJogador = nomeJogador;
        this.saidaMsg = saidaMsg;
        this.entradaMsg = entradaMsg;
        this.scanner = new Scanner(System.in);
    }

@Override
    public void run()
    {
        try
        {
            while (true)
            {
                Object msg = entradaMsg.readObject();
                System.out.println("Mensagem do servidor para " + nomeJogador + ": " + msg);

                // Leitura da jogada
                System.out.print("Digite sua jogada, " + nomeJogador + ": ");
                String jogada = scanner.nextLine();

                // Envia a jogada para o servidor
                saidaMsg.writeObject(jogada);
                saidaMsg.flush();
            }
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}