package classes;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteThread implements Runnable
{
    private String nomeJogador;
    
    public ClienteThread(String nomeJogador)
    {
        this.nomeJogador = nomeJogador;  
    }

    @Override
    public void run()
    {
        try
        {
            Socket socket = new Socket("localhost", 12345);

            ObjectOutputStream saidaObj = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entradaObj = new ObjectInputStream(socket.getInputStream());

            saidaObj.writeObject(nomeJogador);

            while (true)
            {
                String controleTurno = (String) entradaObj.readObject();
                System.out.println(controleTurno);

                if(controleTurno.equals("Vez de: " + nomeJogador))
                {
                    System.out.println(nomeJogador + " sua mão é essa: ");
                    System.out.println(nomeJogador.getMao());
                    System.out.println("Selecione o Índice [0..." + (nomeJogador.mao.size()-1) + "] da carta que deseja jogar ou digite -1 para comprar: ");
                }
                
                /*System.out.println("Digite uma mensagem para o servidor (ou 'sair' para encerrar): ");
                Scanner scanner = new Scanner(System.in);
                String mensagem = scanner.nextLine();
                saidaObj.writeObject(mensagem);

                if (mensagem.equalsIgnoreCase("sair")) 
                {
                    break;
                }

                String resposta = (String) entradaObj.readObject();
                System.out.println("Resposta " + resposta);*/
            }



        } catch(IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

}
