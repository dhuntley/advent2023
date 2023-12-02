package advent.day01;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import advent.common.util.InputReader;

public class CalibrationAdvanced {

  private List<Integer> calibrationValues = new LinkedList();

  private static final Map<String, Character> digitMap;
  static {
    digitMap = new HashMap<>();
    digitMap.put("one", '1');
    digitMap.put("two", '2');
    digitMap.put("three", '3');
    digitMap.put("four", '4');
    digitMap.put("five", '5');
    digitMap.put("six", '6');
    digitMap.put("seven", '7');
    digitMap.put("eight", '8');
    digitMap.put("nine", '9');
  }

  private Character getDigit(String line, int index) {
    for (String digitName : digitMap.keySet()) {
      if (line.substring(index).startsWith(digitName)) {
        return digitMap.get(digitName);
      }
    }
    return Character.isDigit(line.charAt(index)) ? line.charAt(index) : null;
  }

  public CalibrationAdvanced(List<String> lines) {
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
      CalibrationAdvanced calibration = new CalibrationAdvanced(lines);
      System.out.println(calibration.getSum());
  }
}