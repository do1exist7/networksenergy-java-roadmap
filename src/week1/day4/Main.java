package week1.day4;

import java.util.regex.Pattern;

public class Main {
public static void main(String[] args) {
    var pattern = Pattern.compile("(\\d+)([+\\-*/])(\\d+)");

    while(true){
        String input = IO.readln("> "); // looks more neatly

        if(input.trim().equalsIgnoreCase("exit")){
            IO.println("Exiting...");
            break;
        }

        var matcher = pattern.matcher(input.replaceAll("\\s+", "")); //clean spaces!!!
        if (matcher.matches()) {
            int a = Integer.parseInt(matcher.group(1));
            String op = matcher.group(2);
            int b = Integer.parseInt(matcher.group(3));

            int res = switch(op){
                case "+" -> a + b;
                case "-" -> a - b;
                case "*" -> a * b;
                case "/" -> {
                    if (b == 0) {
                        IO.println("ERROR: DIVISION BY ZERO");
                        yield 0;
                    }
                    yield a / b;
                }
                default -> 0;
            };
            IO.println(input.replaceAll("\\s+", "") + "=" + res);
        } else{
            IO.println("Incorrect pattern!!!");
        }
    }
}
}
