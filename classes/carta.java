package classes;

public class carta {
    private String cor;
    private int num;
    private habilidade hab;

    public carta(String cor, int num, habilidade hab){
        this.cor = cor;
        this.num = num;
        this.hab = hab;
    }

    public String getCor(){
        return this.cor;
    }

    public int getNumero(){
        return this.num;
    }

    public String getHabilidade(){
        return this.hab.getHabilidade();
    }
}
