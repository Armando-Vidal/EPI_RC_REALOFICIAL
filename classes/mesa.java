package classes;

import java.util.Scanner;
import java.util.Stack;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.InputMismatchException;

//Classe que representa a mesa em que a partida está sendo jogada, controlando ações, baralhos e jogadores
public class mesa implements Serializable{
    //Os atributos são um baralho de onde ccartas serão compradas, um Stack de cartas que será onde as cartas serão jogadas
    //E o jogador que está jogando esse turno
    private baralho baralhoCompra;
    private Stack<carta> baralhoJogado;
    private jogador vez;
    private jogador jogador1;
    private jogador jogador2;

    //O constructo gera um baralho atribuído a mesa e inicia a Stack de cartas jogadas
    public mesa(baralho baralho){
        baralho.geraBaralho();
        this.baralhoCompra = baralho;
        this.baralhoJogado = new Stack<>();
    }

    //Função responsável por realizar o jogo (com 2 players)
    public synchronized void iniciaJogo(jogador player1, jogador player2) throws WrongCardException{
        jogador1 = player1;
        jogador2 = player2;
        
        //Jogadores compram 7 cartas e essas são exibidas (tirar exibição depois)
        for(int i=0; i<7; i++){
            jogador1.compraCarta(baralhoCompra);
        }
        for(int i=0; i<7; i++){
            jogador2.compraCarta(baralhoCompra);
        }

        /*System.out.println("Baralho do(a) " + jogador1.nome + ": ");
        System.out.println(jogador1.getMao());
        System.out.println("Baralho do(a) " + jogador2.nome + ": ");
        System.out.println(jogador2.getMao());*/

        //Coloca uma carta (numérica e não de habilidade) nas cartas jogadas para iniciar o jogo e coloca a vez para o jogador 1
        while(baralhoCompra.brlh.get(baralhoCompra.brlh.size() - 1).getNum() == -1){
            baralhoCompra.brlh.add(0, baralhoCompra.brlh.pop());
        }

        baralhoJogado.add(baralhoCompra.brlh.pop());

        this.vez = jogador1;

        //While que faz o jogo rodar até um player ficar sem cartas
        while(true){
            //Exibição da carta que está no topo das jogadas e da mão do jogador da vez
            System.out.println("Carta no topo da mesa: " + baralhoJogado.peek());
            /*if((baralhoJogado.get(baralhoJogado.size() - 1).getHab().equals("bloqueio")) || (baralhoJogado.get(baralhoJogado.size() - 1).getHab().equals("+2")) ){
                System.out.println(baralhoJogado.get(baralhoJogado.size() - 1).getCor() + " / " + baralhoJogado.get(baralhoJogado.size() - 1).getHab());
            } else if(baralhoJogado.get(baralhoJogado.size() - 1).getNum() > -1){
                System.out.println(baralhoJogado.get(baralhoJogado.size() - 1).getCor() + " / " + baralhoJogado.get(baralhoJogado.size() - 1).getNum());
            }*/

            System.out.println("Vez de: " + vez.nome);

            System.out.println(vez.nome + " sua mão é essa: ");
            System.out.println(vez.getMao());

            //Orientações para o player
            System.out.println("Selecione o Índice [0..." + (vez.mao.size()-1) + "] da carta que deseja jogar ou digite -1 para comprar: ");
            int indexMao = 0;
            Scanner obj = new Scanner(System.in);
                
            //O maior try-catch impede o jogador de escolher uma carta que não "combina" com a do topo das jogadas
            try{
                while(true){
                    //Esse try-catch menor impede o jogador de escolher um valor de índice que esteja fora do range
                    //de suas cartas ou uma letra/símbolo não numérico
                    try {
                        //indeMao é a variávelque representa o índice da carta do jogador no array em sua mão
                        indexMao = obj.nextInt();
                        if(indexMao < -1 || indexMao >= vez.mao.size()) throw new InputMismatchException();
                        break;

                    } catch (InputMismatchException excecao){
                        System.out.println("Selecione um valor numérico inteiro que corresponda ao indice de uma carta");
                        obj.next();
                    }
                }
            
                //Variável de controle para jogada do player (caso compre carta)
                int jogada = 1;

                //Laço da jogada do turno atual
                while(jogada >= 0){
                    
                    //Verificação se a carta "combina" com a última jogada (cor, habilidade ou número iguais)
                    if(indexMao > -1 && ( ( vez.mao.get(indexMao).getHab().equals(baralhoJogado.get(baralhoJogado.size() - 1).getHab()) && 
                                                !(vez.mao.get(indexMao).getHab().equals("nenhum")) ) || 
                                            ( vez.mao.get(indexMao).getNum() == baralhoJogado.get(baralhoJogado.size() - 1).getNum() ) || 
                                            ( vez.mao.get(indexMao).getCor().equals(baralhoJogado.get(baralhoJogado.size() - 1).getCor()) ) )){
                        
                        //Remoção da carta selecionada da mão do jogador e adição dela como última jogada
                        baralhoJogado.add(vez.mao.get(indexMao));
                        vez.mao.remove(indexMao);
                        break;
                    
                    //Condição para caso o jogador resolva comprar carta
                    } else if(indexMao == -1 && jogada == 1){
                        
                        //O jogador do turno compra a carta e ela é exibida
                        vez.compraCarta(baralhoCompra);
                        if(vez.mao.get(vez.mao.size() - 1).getNum() > -1) System.out.println("Carta comprada: " + vez.mao.get(vez.mao.size() - 1).getCor() + " / " + vez.mao.get(vez.mao.size() - 1).getNum());
                        else if(vez.mao.get(vez.mao.size() - 1).getNum() == -1) System.out.println("Carta comprada: " + vez.mao.get(vez.mao.size() - 1).getCor() + " / " + vez.mao.get(vez.mao.size() - 1).getHab());

                        //Orientação para o jogador se ele deseja jogar a carta comprada
                        System.out.println("Você pode e deseja jogar a carta comprada? Digite 1 para sim ou 0 para não: ");

                        Scanner resp = new Scanner(System.in);
                        int respInt = resp.nextInt();

                        //Caso não deseje o laço é quebrado e a jogada acaba
                        if(respInt == 0) break;

                        //Caso ele deseje jogar a carta, o trecho "jogada--" faz com que ele passe mais uma única vez
                        //no laço de jogada (porém sem entrar nesse else if novamente)
                        jogada--;
                        //Atualização do indexMao para que a carta jogada seja verificada
                        indexMao = vez.mao.size() - 1;

                    //Esse else ocorre caso o jogador selecione uma carta que não "combina" com o o topo, jogando uma exceção
                    } else throw new WrongCardException("");
                }
            } catch(WrongCardException e){
                //Essa exceção faz com que o jogador selecione outra carta
                System.out.println("Carta inválida, selecione uma carta com mesmo símbolo ou mesma cor.");
                obj.next();
            }
            
            //Verifica se algum dos jogadoores está de mão vazia
            if(vez.mao.size() == 0) break;

            //Aplica as habilidades das cartas especiais (caso seja um bloqueio a vez não será passada e
            //caso seja um +2 o outro jogador compra duas cartas)
            if(vez == jogador1 && !(baralhoJogado.get(baralhoJogado.size() - 1).getHab().equals("bloqueio"))) vez = jogador2;
            else if(vez == jogador2 && !(baralhoJogado.get(baralhoJogado.size() - 1).getHab().equals("bloqueio"))) vez = jogador1;

            if(baralhoJogado.get(baralhoJogado.size()-1).getHab().equals("+2")){
                vez.compraCarta(baralhoCompra);
                vez.compraCarta(baralhoCompra);
            }
        }
        
        //Exibe mensagem de vitória
        System.out.println(vez.nome + " é o vencedor");
    }


    public synchronized void processarAcao(String acao) throws WrongCardException
    {
        if (!acao.equals("-1")) 
        {
            //tira a carta da mao do jogador, coloca ela no topo da mesa, confirma se posui habilidade e passa a vez
            
  
            System.out.println("Carta inválida, selecione uma carta com mesmo símbolo ou mesma cor.");
        }
        else if (acao.equals("-1"))
        {
            //pega a primeira carta do baralho e adiciona a mao do jogador, pergunta se vai jogar ou manter a carta e continua de acordo com a escolha do cliente
        }
    }

    //funções para o jogo rodar de acordo no servidor
    public Stack<String> getMao()
    {
        return vez.getMao();
    }
    public jogador getJogadorAtual()
    {
        return vez;
    }
    public void compraCarta()
    {
        vez.compraCarta(baralhoCompra);
    }


    //Getters
    public carta getCartaNoTopo(){
        return this.baralhoJogado.peek();
    }

    public baralho getBaralho(){
        return this.baralhoCompra;
    }

    public Stack<carta> getJogados(){
        return this.baralhoJogado;
    }

    public String getVez(){
        return this.vez.nome;
    }

    public void processarAcao(InetAddress inetAddress, String command) {
    }
}
