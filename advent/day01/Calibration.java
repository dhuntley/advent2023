package advent.day01;

import java.util.LinkedList;
import java.util.List;

import advent.common.util.InputReader;

public class Calibration {

  private List<Integer> calibrationValues = new LinkedList();

  private Character getDigit(String line, int index) {
    return Character.isDigit(line.charAt(index)) ? line.charAt(index) : null;
  }

  public Calibration(List<String> lines) {
    for (String line : lines) {
      int cursor;
      Character firstDigit = null;
      Character secondDigit = null;
      
      for (cursor = 0; cursor < line.length(); cursor++) {
        firstDigit = getDigit(line, cursor);
        if (firstDigit != null) {
          break;
        }
      }

      for (cursor = line.length() - 1; cursor >= 0; cursor--) {
        secondDigit = getDigit(line, cursor);
        if (secondDigit != null) {
          break;
        }
      }

      calibrationValues.add(Integer.parseInt("" + firstDigit + secondDigit));
    }
  }

  public int getSum() {
    return calibrationValues.stream().reduce(0, Integer::sum);
  }

  public static void main(String[] args) {
      List<String> lines = InputReader.readLinesFromInput("advent/day01/input.txt");
      Calibration calibration = new Calibration(lines);
      System.out.println(calibration.getSum());
  }
}