package advent.day09;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import advent.common.util.InputReader;

public class Oasis {
  
  public static long extrapolateRight(List<Long> sequence) {
    List<Long> differences = new ArrayList<>();
    for (int i=0; i<sequence.size()-1; i++) {
      differences.add(sequence.get(i+1) - sequence.get(i));
    }
    
    if (differences.stream().allMatch(diff -> diff.equals(0))) {
      return sequence.get(0);
    }

    return sequence.getLast() + extrapolateRight(differences);
  }

  public static long extrapolateLeft(List<Long> sequence) {
    List<Long> differences = new ArrayList<>();
    for (int i=0; i<sequence.size()-1; i++) {
      differences.add(sequence.get(i+1) - sequence.get(i));
    }
    
    if (differences.stream().allMatch(diff -> diff.equals(0))) {
      return sequence.get(0);
    }

    return sequence.getFirst() - extrapolateLeft(differences);
  }

  public static void main(String[] args) {
    List<String> lines = InputReader.readLinesFromInput("advent/day09/input.txt");
    List<List<Long>> sequences = lines.stream().map(line -> {
      List<Long> sequence = new LinkedList<>();
      String[] tokens = line.split(" ");
      for (String token : tokens) {
        sequence.add(Long.parseLong(token));
      }
      return sequence;
    }).toList(); 

    //System.out.println(sequences);
    //System.out.println(sequences.stream().mapToLong(Oasis::extrapolateRight).sum());
    System.out.println(sequences.stream().mapToLong(Oasis::extrapolateLeft).sum());
  }
}
