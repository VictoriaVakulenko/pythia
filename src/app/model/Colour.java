package app.model;


public class Colour {
    private int r;
    private int g;
    private int b;
    private int count;
    private int volume;


    public Colour(){}

    public Colour(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Colour(int r, int g, int b, int volume, int count){
        this.r = r;
        this.g = g;
        this.b = b;
        this.volume = volume;
        this.count = count;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        if(volume != 0 && count != 0 ){
            return "R: " + r + " G: " + g + " B: " + b + " Volume: " + volume + " Count: " + count;
        }
        return "R: " + r + " G: " + g + " B: " + b;
    }
}
