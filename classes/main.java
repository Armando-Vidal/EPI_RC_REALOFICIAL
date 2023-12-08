package classes;
import java.util.Scanner;

public class main{
    public static void main(String[] args) {
        System.out.println("Insira o nome do jogador 1: ");
        Scanner objNome1 = new Scanner(System.in);
        String nome1 = objNome1.nextLine();

        System.out.println("Insira o nome do jogador 2: ");
        Scanner objNome2 = new Scanner(System.in);
        String nome2 = objNome2.nextLine();

        jogador player1 = new jogador(nome1);
        jogador player2 = new jogador(nome2);

        baralho baralho = new baralho();

        mesa mesa = new mesa(baralho);
        mesa.iniciaJogo(player1, player2);
    }
}