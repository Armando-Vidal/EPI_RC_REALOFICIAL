package classes;

import java.util.Random;

public class mesa {
    public baralho cartaCompra;
    public carta cimaDaMesa;
    public jogador jogador1;
    public jogador jogador2;

    public void iniciaJogo(baralho cartaCompra, jogador jogador1, jogador jogador2){
        for(int i=0; i<7; i++){
            jogador1.compraCarta(cartaCompra);
            jogador2.compraCarta(cartaCompra);
        }

        while(cartaCompra.brlh.get(cartaCompra.brlh.size()).getHabilidade() != "none"){
            carta volta = cartaCompra.brlh.get(cartaCompra.brlh.size());
            cartaCompra.brlh.add(volta);
            cartaCompra.brlh.pop();
        }
        cimaDaMesa = cartaCompra.brlh.pop();

        Random random = new Random();

        int dado1 = random.nextInt(100);
        int dado2 = random.nextInt(100);
        while(dado1 == dado2){
            dado1 = random.nextInt(100);
            dado2 = random.nextInt(100);
        }
        System.out.println("Dado Jogador 1: " + dado1);
        System.out.println("Dado Jogador 2: " + dado2);

        if(dado1 > dado2) jogador1.vez = true;
        else if(dado2 > dado1) jogador2.vez = true;
    }
}
