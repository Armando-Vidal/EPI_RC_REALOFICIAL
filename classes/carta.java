package classes;

public class carta {
    private String cor;
    private String hab;
    private int num;

    public carta(){}

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
