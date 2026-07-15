package week1.day2;

class Point{
    int x;
    int y;
    Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    Point(){
        this(0,0);
    }
    @Override
    public String toString() {
        return String.format("x: %d, y: %d", x, y);
    }
}

public class Main {
public static String reverse(String s){
    StringBuilder t = new StringBuilder();
    for(int i = s.length() - 1; i >= 0; i--){
        t.append(s.charAt(i));
    }
    return t.toString();
}

public static void main(String[] args) {
    int x = 5;
    double z = 3.4;
    float q = 4.2f;
    boolean T = true;
    String s = "foo";
    String t = new String("foo"); //dumb and no use, just allocates new memory for the same string
    IO.println(s + " 52 " + t);
    IO.println(s + T + q); // Wow, I can concat string to other primitives)))

    // comparison of strings
    IO.println(s == t);
    IO.println(s.equals(t));

    int[] arr = {6, 7, 67, 6767, 6767, 667};
    for(int i : arr){
        IO.println(i);
    }
    IO.println(arr.length);

    String alas = "The Asid and Aids were brothers! or were they?";
    IO.println(alas.indexOf("Asid"));
    IO.println(alas.substring(1, 5));
    IO.println(reverse(alas));

    Point Z = new Point(0,1);
    IO.println(Z);
    Point ZZ = new Point();
    IO.println(ZZ);
    for (int i = 0; i < args.length; i++) {
        System.out.println(args[i]);
    }

}
}
