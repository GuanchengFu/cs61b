public class OffByN implements CharacterComparator{

    private int distance;
    public OffByN(int N){
        this.distance = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        if (diff == distance || diff == -distance)
            return true;
        return false;
    }

}
