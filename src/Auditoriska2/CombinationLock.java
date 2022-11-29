package Auditoriska2;

public class CombinationLock {
    private int combination;
    private boolean isOpen;

    public CombinationLock(int combination) {
        this.combination = combination;
        this.isOpen = false;
    }

    public boolean open(int combination) {
        if(this.combination == combination)
            this.isOpen = true;
        else
            this.isOpen = false;
        return this.isOpen;
    }

    public boolean changeCombo(int oldCombination, int newCombination) {
        boolean isCorrect = (this.combination == oldCombination);
        if(isCorrect)
            this.combination = newCombination;
        return isCorrect;
    }
}
