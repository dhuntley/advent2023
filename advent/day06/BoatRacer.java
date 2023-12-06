package advent.day06;

import java.util.LinkedList;
import java.util.List;
import advent.common.util.InputReader;

public class BoatRacer {
  
  private static class Race {
    private long totalTime;
    private long targetDistance;

    public Race(long totalTime, long targetDistance) {
      this.totalTime = totalTime;
      this.targetDistance = targetDistance;
    }

    public long getNumWinChoices() {
      long numWins = 0;
      for (long chargeTime=0; chargeTime<=totalTime; chargeTime++) {
        long travelTime = totalTime - chargeTime;
        long speed = chargeTime;
        if (speed * travelTime > targetDistance) {
          numWins++;
        }
      }
      return numWins;
    }
  }

  public static void main(String[] args) {
    List<String> lines = InputReader.readLinesFromInput("advent/day06/input.txt");
    
    String[] timeTokens = lines.get(0).split("\\s+");
    String[] distanceTokens = lines.get(1).split("\\s+");

    List<Race> races = new LinkedList<>();
    for (int i=1; i<timeTokens.length; i++) {
      if (!timeTokens[i].isEmpty() && !distanceTokens[i].isEmpty()) {
        races.add(new Race(Long.parseLong(timeTokens[i]), Long.parseLong(distanceTokens[i])));
      }
    }

    System.out.println(races.stream().mapToLong(Race::getNumWinChoices).reduce(1, (a, b) -> a * b));
  }
}
