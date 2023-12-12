package classes;
import java.io.*;
import java.net.*;

public class ClienteThread implements Runnable
{
    private String nomeJogador;
    private Socket socket;
    private ObjectOutputStream saidaObj;
    private ObjectInputStream entradaObj;
    
    public ClienteThread(String nomeJogador, Socket socket)
    {
        this.nomeJogador = nomeJogador;
        this.socket = socket;
    
        try
        {
            saidaObj = new ObjectOutputStream(socket.getOutputStream());
            entradaObj = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                Object attObj = entradaObj.readObject();//ideia era ter 2 tipos de acoes, jogada e atualização do status da mesa

                if(attObj instanceof mesa)
                {
                    //atualização universal da mesa -> n roda desse jeito ainda mas a ideia é q a cada turno a gnt tenha uma atualização igual é feito em mesa.java
                    //n sei como fazer mas era pra dar a atualização como a carta de cima e passar a mao do cliente(obviamente a mao certa pra cada um e n mostrar a do adversário)
                    mesa mesaAtt = (mesa) attObj;
                    System.out.println("O status da mesa é: " + mesaAtt);
                }
                else if(attObj instanceof String)
                {
                    //atualizações com base em jogadas (jogar carta da mao / comprar carta)
                    String jogada = (String) attObj;
                    System.out.println("A jogada de" + nomeJogador + "foi " + jogada);
                    //colocar a logica de atualização das mao a partir da jogada(chamar mesa ou implementar aqui) -> n sei fazer essa aplicação separadamente
                }
            }
        } catch(IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }finally
        {
            fecharConexao();
        }
    }

    // Envia açao do jogador -> infelizmente minha ideia de 2 mensagens diferentes eh legal mas n eh aplicada aqui pq eu n tinha noção doq tava fazendo
    public void enviarAcao(String acao)
    {
        try
        {
            saidaObj.writeObject(acao);
            saidaObj.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }    
    //Fecha a conexão de tudo mas so com o fim do jogo
    public void fecharConexao()
    {
        try
        {
            entradaObj.close();
            saidaObj.close();
            socket.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
