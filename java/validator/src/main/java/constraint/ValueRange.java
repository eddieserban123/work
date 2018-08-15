package constraint;

public enum ValueRange {
    MIN(10),
    MAX(90);

    int val;

    ValueRange(int val) {
        this.val = val;
    }

    public int getVal(){
        return val;
    }

}
