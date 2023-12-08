package classes;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class jogador {
    public List<carta> mao;
    public String nome;
    public mesa mesa;

    public jogador(String nome){
        this.mao = new ArrayList<>();
        this.nome = nome;
    }

    public void compraCarta(baralho baralho){
        this.mao.add(baralho.brlh.remove(baralho.brlh.size() - 1));
    }

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
