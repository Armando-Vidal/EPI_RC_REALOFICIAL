package classes;

import java.util.Stack;

public class jogador {
    public Stack<carta> mao;
    public boolean vez = false;

    public void compraCarta(baralho baralho){
        mao.add(baralho.brlh.pop());
    }

    public void jogaCarta(carta carta){
        int i;
        for(i = 0; i < mao.size(); i++){
            if(mao.get(i) == carta) break;
        }
        mao.remove(i);
    }
}
