import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public final class Aufgabe1 {
    
    private static abstract class Line {
        private Line() {
        }
    }


    private final static Line Comment = new Line() {
    };

    private final static class Command extends Line {
        private final String cmdType;
        private final int n;

        private Command(String cmdType, int n) {
            this.cmdType = cmdType;
            this.n = n;
        }

        private String getCmdType() {
            return this.cmdType;
        }

        private int getN() {
            return this.n;
        }

    }

    public static void main(String[] args) throws IOException {
        List<Line> lines = linesFromFile("./src/source_simple.txt");
        parse(lines);
    }
    
    private static List<Line> linesFromFile(String url) throws IOException {
        return Files.lines(Paths.get(url))
            .map(Aufgabe1::lineOf)
            .collect(Collectors.toList());
    }
    
    private static void parse(List<Line> lines) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Integer> memory = new HashMap<>();
        
        int acc = 0;
        int ic = 1;
        
        while (true) {
            Line line = lines.get(ic - 1);
            if (line == Comment) {
                ic += 1;
            } else if (line instanceof Command) {
                Command command = (Command) line;
                
                String type = command.getCmdType();
                int n = command.getN();
                
                
                Integer nextIc = null;
                
                
                int value = memory.getOrDefault(n, 0);
                
                switch (type) {
                    case "hlt":
                        return;
                    case "add":
                        acc += value;
                        break;
                    case "sub":
                        acc -= value;
                        break;
                    case "mul":
                        acc *= value;
                        break;
                    case "div":
                        acc /= value;
                        break;
                    case "inp":
                        memory.put(n, scanner.nextInt());
                        break;
                    case "out":
                        System.out.println(value);
                        break;
                    case "lda":
                        acc = value;
                        break;
                    case "ldk":
                        acc = n;
                        break;
                    case "sta":
                        memory.put(n, acc);
                        break;
                    case "jmp":
                        nextIc = n;
                        break;
                    case "jez":
                        if (acc == 0) {
                            nextIc = n;
                        }
                        break;
                    case "jne":
                        if (acc != 0) {
                            nextIc = n;
                        }
                        break;
                    case "jge":
                        if (acc >= 0) {
                            nextIc = n;
                        }
                        break;
                    case "jgz":
                        if (acc > 0) {
                            nextIc = n;
                        }
                        break;
                    case "jle":
                        if (acc <= 0) {
                            nextIc = n;
                        }
                        break;
                    case "jlz":
                        if (acc < 0) {
                            nextIc = n;
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unknown command " + type);
                }
                
                ic = nextIc != null ? nextIc : ic + 1;
            }
        }
        
    }
    
    private static Line lineOf(String str) {
        str = str.trim();
        if (str.isEmpty() || str.startsWith("#")) {
            return Comment;
        } else {
            String[] parts = Arrays.stream(str.split(" "))
                .filter(s -> !s.isEmpty())
                .limit(2)
                .toArray(String[]::new);
            return new Command(parts[0].toLowerCase(), Integer.parseInt(parts[1]));
        }
    }
}


