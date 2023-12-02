package advent.day02;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import advent.common.util.InputReader;

public class CubeGameAdvanced {
  
  private static class Game {
    private int id;
    private List<Draw> draws = new LinkedList<>();
    
    public Game(String line) {
      String[] tokens = line.split(":");
      id = Integer.parseInt(tokens[0].split(" ")[1]);

      for (String drawLine : tokens[1].split(";")) {
        draws.add(new Draw(drawLine));
      }
    }

    public boolean isValid(int r, int g, int b) {
      return draws.stream().allMatch(draw -> draw.isValid(r, g, b));
    }

    public int getId() {
      return id;
    }

    public long getMinimumPower() {
      int minRed = Integer.MIN_VALUE;
      int minGreen = Integer.MIN_VALUE;
      int minBlue = Integer.MIN_VALUE;

      for (Draw draw : draws) {
        minRed = Math.max(minRed, draw.numRed);
        minGreen = Math.max(minGreen, draw.numGreen);
        minBlue = Math.max(minBlue, draw.numBlue);
      }

      return minRed * minBlue * minGreen;
    }
  }
  
  private static class Draw {
    int numRed = 0;
    int numGreen = 0;
    int numBlue = 0;

    public Draw(String line) {
      String[] tokens = line.split(",");
      for (String token : tokens) {
        String color = token.trim().split(" ")[1];
        Integer count = Integer.parseInt(token.trim().split(" ")[0]);

        if (color.equals("red")) {
          numRed = count;
        } else if (color.equals("green")) {
          numGreen = count;
        } else if (color.equals("blue")) {
          numBlue = count;
        }
      }
    }

    public boolean isValid(int r, int g, int b) {
      return numRed <= r && numGreen <= g && numBlue <= b;
    }
  }
  
  public static void main(String[] args) {
    List<String> lines = InputReader.readLinesFromInput("advent/day02/input.txt");
    List<Game> games = lines.stream().map(line -> new Game(line)).collect(Collectors.toList());
    System.out.println(games.stream().map(Game::getMinimumPower).reduce(0l, Long::sum));
  }
}
