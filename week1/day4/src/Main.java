import java.util.regex.Pattern;
void main() {
    var pattern = Pattern.compile("(\\d+)([+\\-*/])(\\d+)");

    do{
        String input = IO.readln();
        var matcher = pattern.matcher(input);
        if (matcher.matches()) {
            Integer a = Integer.parseInt(matcher.group(1));
            String op = matcher.group(2);
            Integer b = Integer.parseInt(matcher.group(3));

            Integer res = switch(op){
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
            IO.println(input + "=" + res);
        } else{
            IO.println("Incorrect pattern!!!");
            break;
        }
    } while(true);
}
