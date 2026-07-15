package week2.day4;

class SpeedLimitExceeded extends RuntimeException {
    public SpeedLimitExceeded(String message) {
        super(message);
    }
}

public class Main {
public static void main(String[] args) {
    try {
        int[] arr = new int[67];
//        arr[67]=667;
        throw new SpeedLimitExceeded("You are running with 67 km/h!");

    } catch (ArrayIndexOutOfBoundsException e){
//    } catch (NullPointerException ex){
        IO.println("67 >= 67 XD");
    } catch (SpeedLimitExceeded e) {
        IO.println("Damn they are running to fast" + e.toString());
    }
    finally{
        IO.println("What finally?");
    }
    IO.println("GEGE is the mangaka");
}

@Override
public int hashCode() {
    return super.hashCode();
}
}
