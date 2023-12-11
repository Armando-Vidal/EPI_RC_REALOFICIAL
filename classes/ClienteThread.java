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
                Object attObj = entradaObj.readObject();

                if(attObj instanceof mesa)
                {
                    //atualização universal da mesa
                    mesa mesaAtt = (mesa) attObj;
                    System.out.println("O status da mesa é: " + mesaAtt);
                }
                else if(attObj instanceof String)
                {
                    //atualizações com base em jogadas (jogar carta da mao / comprar carta)
                    String jogada = (String) attObj;
                    System.out.println("A jogada de" + nomeJogador + "foi " + jogada);
                    //colocar a logica de atualização das mao a partir da jogada(chamar mesa ou implementar aqui)
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

    // Envia açao do jogador
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
    //Fecha a conexão ao fim do jogo
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
