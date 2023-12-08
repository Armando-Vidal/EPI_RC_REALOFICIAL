package classes;

import java.util.Collections;
import java.util.Stack;

public class baralho {
    public Stack<carta> brlh;

    public baralho(){   
        this.brlh = new Stack();
    }

    public Stack<carta> geraBaralho(){
        int x,y;
        x = y = 0;
        String[] cores = {"azul", "vermelho", "amarelo", "verde"};
        String[] habilidades = {"bloqueio", "+2"};

        while(x < 4){
            carta cardnum = new carta();
            cardnum.setNum(y);
            cardnum.setCor(cores[x]);
            cardnum.setHab("nenhum");
            this.brlh.add(cardnum);
            if(y == 9){
                carta cardhabblock = new carta();
                cardhabblock.setCor(cores[x]);
                cardhabblock.setHab(habilidades[0]);
                cardhabblock.setNum(-1);
                this.brlh.add(cardhabblock);
                
                carta cardhabmaisdois = new carta();
                cardhabmaisdois.setCor(cores[x]);
                cardhabmaisdois.setHab(habilidades[1]);
                cardhabmaisdois.setNum(-1);
                this.brlh.add(cardhabmaisdois);

                y = -1;
                x++;
            }
            y++;
        }

        Collections.shuffle(brlh);

        return this.brlh;
    }

    public Stack<String> getBaralho(){
        Stack<String> baralho = new Stack<>();
        
        for(int i=0; i<this.brlh.size(); i++){
            if(brlh.get(i).getHab().equals("nenhum")) baralho.add(brlh.get(i).getCor() + " / " + brlh.get(i).getNum());
            else if(brlh.get(i).getNum() == -1) baralho.add(brlh.get(i).getCor() + " / " + brlh.get(i).getHab());
        }

        return baralho;
    }
}
