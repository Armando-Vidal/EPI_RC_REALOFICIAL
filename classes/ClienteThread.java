package classes;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteThread implements Runnable
{
    private String nomeJogador;
    private Socket socket;
    private ObjectOutputStream saidaObj;
    private ObjectInputStream entradaObj;
    
    public ClienteThread(String nomeJogador)
    {
        this.nomeJogador = nomeJogador;
    }

    @Override
    public void run()
    {
        try
        {
            socket = new Socket("localhost", 12345);

            saidaObj = new ObjectOutputStream(socket.getOutputStream());
            entradaObj = new ObjectInputStream(socket.getInputStream());

            saidaObj.writeObject(nomeJogador);

            while (true)
            {
                //atualização do server
                Object attObj = entradaObj.readObject();

                if(attObj instanceof Mesa)
                {
                    //atualização universal da mesa
                    Mesa mesaAtt = (Mesa) attObj;
                    System.out.println("O status da mesa é: " + mesaAtt);
                }
                else if(attObj instanceof String)
                {
                    //atualizações com base em jogadas (jogar carta da mao / comprar carta)
                    String jogada = (String) attObj;
                    System.out.println("A jogada de " + nomeJogador + "foi" + jogada);//talvez fique meio poluido e eu retire isso
                }
                 
                /*String controleTurno = (String) entradaObj.readObject();
                System.out.println(controleTurno);

                if(controleTurno.equals("Vez de: " + nomeJogador))
                {
                    System.out.println(nomeJogador + " sua mão é essa: ");
                    System.out.println(nomeJogador.getMao());
                    System.out.println("Selecione o Índice [0..." + (nomeJogador.mao.size()-1) + "] da carta que deseja jogar ou digite -1 para comprar: ");
                }
                
                System.out.println("Digite uma mensagem para o servidor (ou 'sair' para encerrar): ");
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
        }finally
        {
            try
            //fecha os recursos ao fim do jogo
            {
            entradaObj.close();
            saidaObj.close();
            socket.close();
            }catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
        // Envio de jogadas para o servidor
    public void enviarJogada(String jogada)
    {
        try
        {
            saidaObj.writeObject(jogada);
            saidaObj.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    // Enviar o estado da mesa atual
    private void attMesa(Mesa mesaAtt)
    {
        //implementar a atualização da mesa k
    }
}
