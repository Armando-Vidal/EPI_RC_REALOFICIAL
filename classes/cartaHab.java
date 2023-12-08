package classes;

public class cartaHab extends carta {
    private String hab;

    public void cartaHab(String cor){
        super.setCor(cor);
    }

    public String getHab(){
        return this.hab;
    }

    public void setHab(String hab){
        this.hab = hab;
    }
}
