package classes;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

//Entidade que representa cada player do jogo
public class jogador {
    //Cada jogador possui uma mão com suas cartas, um nome e a mesa na qual está jogando
    public List<carta> mao;
    public String nome;
    public mesa mesa;

    //O constructor inicia o ArrayList da mão e atribui o nome (escolhido pelo usuário)
    public jogador(String nome){
        this.mao = new ArrayList<>();
        this.nome = nome;
    }

    //A função de comprar carta remove o último elemento (carta) de um baralho indicado
    public void compraCarta(baralho baralho){
        this.mao.add(baralho.brlh.remove(baralho.brlh.size() - 1));
    }

    //Essa função é responsável por imprimir toda a mão do jogador de forma mais prática para leitura, sendo ela : 
    //"[cor] / [habilidade ou número]"
    public Stack<String> getMao(){
        Stack<String> repmao = new Stack<>();
        int i = 0;
        while(i < this.mao.size()){
            if(mao.get(i).getHab().equals("nenhum")) repmao.add(mao.get(i).getCor() + " / " + mao.get(i).getNum());
            else if(mao.get(i).getNum() == -1) repmao.add(mao.get(i).getCor() + " / " + mao.get(i).getHab());
            i++;
        }
        
        return repmao;
    }
}
