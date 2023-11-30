package classes;

import java.util.Collections;
import java.util.Stack;

public class baralho {
    public Stack<carta> brlh;

    public baralho(){   
        this.brlh = new Stack();

        for(int i = 0; i < 10; i++){
            carta a = new carta("azul", i, new habilidade("none"));
            this.brlh.add(a);
        }
        carta blockAzul = new carta("azul", 11, new habilidade("block"));
        this.brlh.add(blockAzul);
        
        carta maisDoisAzul = new carta("azul", 11, new habilidade("compra"));
        this.brlh.add(maisDoisAzul);

        for(int j = 0; j < 10; j++){
            carta a = new carta("verde", j, new habilidade("none"));
            this.brlh.add(a);
        }
        carta blockVerde = new carta("verde", 11, new habilidade("block"));
        this.brlh.add(blockVerde);

        carta maisDoisVerde = new carta("verde", 11, new habilidade("compra"));
        this.brlh.add(maisDoisVerde);

        for(int k = 0; k < 10; k++){
            carta a = new carta("vermelho", k, new habilidade("none"));
            this.brlh.add(a);
        }
        carta blockVermelho = new carta("vermelho", 11, new habilidade("block"));
        this.brlh.add(blockVermelho);

        carta maisDoisVermelho = new carta("vermelho", 11, new habilidade("compra"));
        this.brlh.add(maisDoisVermelho);

        for(int l = 0; l < 10; l++){
            carta a = new carta("amarelo", l, new habilidade("none"));
            this.brlh.add(a);
        }
        carta blockAmarelo = new carta("amarelo", 11, new habilidade("block"));
        this.brlh.add(blockAmarelo);

        carta maisDoisAmarelo = new carta("amarelo", 11, new habilidade("compra"));
        this.brlh.add(maisDoisAmarelo);

        Collections.shuffle(this.brlh);
    }

    public static void main (String args[]){
        baralho teste = new baralho();
        int i = 0;
        while(i<49){
            System.out.println(teste.brlh.get(i).getCor() + " / " + teste.brlh.get(i).getHabilidade());
            i++;
        }
        
    }
}
