package classes;

public class cartaNum extends carta {
    private int num;

    public void cartaNum(String cor){
        super.setCor(cor);
    }

    public int getNum(){
        return this.num;
    }

    public void setNum(int num){
        this.num = num;
    }
}
