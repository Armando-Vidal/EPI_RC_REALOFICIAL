package classes;

import java.util.Collections;
import java.util.Stack;


//Classe que representa o baralho de cartas usado no jogo, que é manipulado como uma pilha
public class baralho {
    //Stack de cartas (ou o próprio baralho que será manipulado) chamado de brlh
    public Stack<carta> brlh;

    //Constructor somente inicializa brlh
    public baralho(){   
        this.brlh = new Stack();
    }

    //Função para que as cartas sejam criadas e o baralho (brlh) embaralhado
    public Stack<carta> geraBaralho(){
        //X e Y são iteradores para cores/habilidades e cores/habilidades são selecionadas em arrays de String
        int x,y;
        x = y = 0;
        String[] cores = {"azul", "vermelho", "amarelo", "verde"};
        String[] habilidades = {"bloqueio", "+2"};

        //Laço para criar cartas
        while(x < 4){
            //Cria cartas de números 0 à 9
            carta cardnum = new carta();
            cardnum.setNum(y);
            cardnum.setCor(cores[x]);
            cardnum.setHab("nenhum");
            this.brlh.add(cardnum);
            if(y == 9){
                //Ao entrar no if (após criar as cartas de 0 à 9 da cor X) é criado e adicionado no baralho as cartas com habilidades
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

        //Embaralhamento de retorno do baralho
        Collections.shuffle(brlh);

        return this.brlh;
    }

    //Essa função é responsável por imprimir todo o baralho de forma mais prática para leitura, sendo ela : 
    //"[cor] / [habilidade ou número]"
    public Stack<String> getBaralho(){
        Stack<String> baralho = new Stack<>();
        
        for(int i=0; i<this.brlh.size(); i++){
            if(brlh.get(i).getHab().equals("nenhum")) baralho.add(brlh.get(i).getCor() + " / " + brlh.get(i).getNum());
            else if(brlh.get(i).getNum() == -1) baralho.add(brlh.get(i).getCor() + " / " + brlh.get(i).getHab());
        }

        return baralho;
    }
}
