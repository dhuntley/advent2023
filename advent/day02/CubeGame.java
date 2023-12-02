package advent.day02;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import advent.common.util.InputReader;

public class CubeGame {
  
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
  }
  
  private static class Draw {
    private int numRed = 0;
    private int numGreen = 0;
    private int numBlue = 0;

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
    System.out.println(games.stream().filter(game -> {
      return game.isValid(12, 13, 14);
    }).map(Game::getId).reduce(0, Integer::sum));
  }
}
