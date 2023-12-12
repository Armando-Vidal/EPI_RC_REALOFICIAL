package classes;

import java.io.*;
import java.net.*;
//esse codigo ta obsoleto pq o jogo n ta rodando simultaneamente pros 2 jogadores em locais diferentes
public class ServidorThread implements Runnable
{
    private Socket socket;
    private mesa mesa;
    private ObjectOutputStream saidaobj;
    private ObjectInputStream entradaobj;
    private jogador jogador;

    public ServidorThread(Socket socket, mesa mesa)//começa a thread de cada jogador e acessa a mesa do jogo
    {
        this.socket = socket;
        this.mesa = mesa;

        try
        {
            saidaobj = new ObjectOutputStream(socket.getOutputStream());// declaração de saida de mensagens para o servidor
            entradaobj = new ObjectInputStream(socket.getInputStream());// declaração de entrada de mensagens para o servidor
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    
    @Override
    public void run()
    {
        try
        {
            //Informações iniciais do jogador(ideia era pegar as infos iniciais aqui, mas ta tudo sendo feito direto no servidor, entao ta redundante)
            while(true)
            {
                //esperando a jogada do cliente
                // ideia é deixar o servidor com a mesa e a cada jogada do cliente ela ser atualizada
                Object acaoCliente = entradaobj.readObject();

                if(acaoCliente instanceof String)
                {
                    String command = (String) acaoCliente;
                    try
                    {
                        mesa.processarAcao(command);
                        // Atualiza todos os clientes após cada ação realizada
                        attUniversal();
                    } catch (WrongCardException e)
                    {
                        System.out.println("Carta inválida, selecione uma carta com mesmo símbolo ou mesma cor.");//pegando o erro de carta basico
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }finally
        {
            try
            {
                //finally eh chamado ao fim do jogo pra fechar sockets e canais de mensagem
                // Fecha recursos quando a thread do jogador termina
                saidaobj.close();
                entradaobj.close();
                socket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void setJogador(jogador jogador)//setamos o jogador para aplicação dos nomes 
    {
        this.jogador = jogador;
    }


    public void attUniversal() // atualização para todo mundo do estado atual da mesa
    {
        try
        {
            for(ServidorThread jogador : Servidor.jogadoresConectados)
            {
                jogador.saidaobj.writeObject(mesa.getCartaNoTopo()); //mensagem com o status atual da mesa (carta de cima, mao dos jogadores)
                jogador.saidaobj.flush();//garante q a mensagem é enviada agora
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}