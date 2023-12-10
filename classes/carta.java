package classes;

//Essa classe representa a entidade de cada carta individualmente
public class carta {
    //Possui os atributos de cor, hab (que pode ser "nenhum") e número (que pode ser -1 em caso de cartas de habilidade)
    private String cor;
    private String hab;
    private int num;

    public carta(){}

    //Getters e Setters
    public String getCor(){
        return this.cor;
    }

    public String getHab(){
        return this.hab;
    }

    public int getNum(){
        return this.num;
    }

    public void setCor(String cor){
        this.cor = cor;
    }

    public void setNum(int num){
        this.num = num;
    }

    public void setHab(String hab){
        this.hab = hab;
    }
}
