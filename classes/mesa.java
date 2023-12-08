package classes;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class mesa {
    private baralho baralhoCompra;
    private Stack<carta> baralhoJogado;
    private jogador vez;

    public mesa(baralho baralho){
        baralho.geraBaralho();
        this.baralhoCompra = baralho;
        this.baralhoJogado = new Stack<>();
    }

    public void iniciaJogo(jogador player1, jogador player2){
        jogador jogador1 = player1;
        jogador jogador2 = player2;
        
        for(int i=0; i<7; i++){
            jogador1.compraCarta(baralhoCompra);
        }
        for(int i=0; i<7; i++){
            jogador2.compraCarta(baralhoCompra);
        }

        System.out.println("Baralho do(a) " + jogador1.nome + ": ");
        System.out.println(jogador1.getMao());
        System.out.println("Baralho do(a) " + jogador2.nome + ": ");
        System.out.println(jogador2.getMao());

        //System.out.println("Topo do baralho de compra (antes de ir pra mesa): ");
        //System.out.println(baralhoCompra.brlh.get(baralhoCompra.brlh.size()-1).getCor() + " / " + baralhoCompra.brlh.get(baralhoCompra.brlh.size()-1).getHab() + " / " + baralhoCompra.brlh.get(baralhoCompra.brlh.size()-1).getNum());

        while(baralhoCompra.brlh.get(baralhoCompra.brlh.size() - 1).getNum() == -1){
            baralhoCompra.brlh.add(0, baralhoCompra.brlh.pop());
        }

        //System.out.println("Topo do baralho de compra (depois de ir pra mesa): ");
        //System.out.println(baralhoCompra.brlh.get(baralhoCompra.brlh.size()-1).getCor() + " / " + baralhoCompra.brlh.get(baralhoCompra.brlh.size()-1).getHab() + " / " + baralhoCompra.brlh.get(baralhoCompra.brlh.size()-1).getNum());

        baralhoJogado.add(baralhoCompra.brlh.pop());

        this.vez = jogador1;

        while(true){
            System.out.println("Carta no topo da mesa: ");
            if((baralhoJogado.get(baralhoJogado.size() - 1).getHab().equals("bloqueio")) || (baralhoJogado.get(baralhoJogado.size() - 1).getHab().equals("+2")) ){
                //System.out.println("Carta da mesa hab");
                System.out.println(baralhoJogado.get(baralhoJogado.size() - 1).getCor() + " / " + baralhoJogado.get(baralhoJogado.size() - 1).getHab());
            } else if(baralhoJogado.get(baralhoJogado.size() - 1).getNum() > -1){
                //System.out.println("Carta da mesa num");
                System.out.println(baralhoJogado.get(baralhoJogado.size() - 1).getCor() + " / " + baralhoJogado.get(baralhoJogado.size() - 1).getNum());
            }

            System.out.println("Vez de: " + vez.nome);

            System.out.println(vez.nome + " sua mão é essa: ");
            System.out.println(vez.getMao());
            System.out.println("Selecione o Índice [0...n-1] da carta que deseja jogar ou digite -1 para comprar: ");
            Scanner obj = new Scanner(System.in);

            int indexMao = obj.nextInt();
            int jogada = 1;

            while(jogada >= 0){
                //System.out.println("entrou no while");
                
                if(indexMao > -1 && ( ( vez.mao.get(indexMao).getHab().equals(baralhoJogado.get(baralhoJogado.size() - 1).getHab()) && 
                                            !(vez.mao.get(indexMao).getHab().equals("nenhum")) ) || 
                                        ( vez.mao.get(indexMao).getNum() == baralhoJogado.get(baralhoJogado.size() - 1).getNum() ) || 
                                        ( vez.mao.get(indexMao).getCor().equals(baralhoJogado.get(baralhoJogado.size() - 1).getCor()) ) )){
                    //System.out.println("Entrou no if");
                    baralhoJogado.add(vez.mao.get(indexMao));
                    vez.mao.remove(indexMao);
                    break;
                } else if(indexMao == -1 && jogada == 1){
                    //System.out.println("Entrou no segundo if");
                    vez.compraCarta(baralhoCompra);
                    if(vez.mao.get(vez.mao.size() - 1).getNum() > -1) System.out.println("Carta comprada: " + vez.mao.get(vez.mao.size() - 1).getCor() + " / " + vez.mao.get(vez.mao.size() - 1).getNum());
                    else if(vez.mao.get(vez.mao.size() - 1).getNum() == -1) System.out.println("Carta comprada: " + vez.mao.get(vez.mao.size() - 1).getCor() + " / " + vez.mao.get(vez.mao.size() - 1).getHab());
                    
                    System.out.println("Você pode e deseja jogar a carta comprada? Digite 1 para sim ou 0 para não: ");

                    Scanner resp = new Scanner(System.in);
                    int respInt = resp.nextInt();

                    if(respInt == 0) break;

                    jogada--;
                    indexMao = vez.mao.size() - 1;
                }
                //System.out.println("Saiu dos ifs");
            }

            //System.out.println("Saiu do while");

            if(vez.mao.size() == 0) break;

            if(vez == jogador1 && !(baralhoJogado.get(baralhoJogado.size() - 1).getHab().equals("bloqueio"))) vez = jogador2;
            else if(vez == jogador2 && !(baralhoJogado.get(baralhoJogado.size() - 1).getHab().equals("bloqueio"))) vez = jogador1;

            if(baralhoJogado.get(baralhoJogado.size()-1).getHab().equals("+2")){
                vez.compraCarta(baralhoCompra);
                vez.compraCarta(baralhoCompra);
            }
        }
        
        System.out.println(vez.nome + " é o vencedor");
    }

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
}
