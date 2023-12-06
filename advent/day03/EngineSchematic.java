package advent.day03;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import advent.common.util.InputReader;

public class EngineSchematic {
  
  private Character[][] grid;

  private static boolean isSymbol(Character c) {
    return !Character.isDigit(c) && !c.equals('.');
  }
  
  public EngineSchematic(List<String> lines) {
    grid = new Character[lines.size()][lines.get(0).length()];
    for (int i=0; i<lines.size(); i++) {
      String line = lines.get(i);
      for (int j=0; j<line.length(); j++) {
        grid[i][j] = line.charAt(j);
      }
    }
  }

  private List<Integer> getPartNumbers() {
    List<Integer> partNumbers = new LinkedList<>();

    boolean[][] partMask = new boolean[grid.length][grid[0].length];
    
    for (int i=0; i<partMask.length; i++) {
      for (int j=0; j<partMask[i].length; j++) {
        if (isSymbol(grid[i][j])) {
          for (int x=i-1; x<=i+1; x++) {
            for (int y=j-1; y<=j+1; y++) {
              if (x >= 0 && x < partMask.length && y >= 0 && y < partMask[i].length && Character.isDigit(grid[x][y])) {
                partMask[x][y] = true;

                int cursor = y - 1;
                while (cursor >= 0 && Character.isDigit(grid[x][cursor])) {
                  partMask[x][cursor] = true;
                  cursor--;
                }
                cursor = y + 1;
                while (cursor < partMask[x].length && Character.isDigit(grid[x][cursor])) {
                  partMask[x][cursor] = true;
                  cursor++;
                }
              }
            }
          }
        }
      }
    }

    // for (int i=0; i<partMask.length; i++) {
    //   for (int j=0; j<partMask[i].length; j++) {
    //     System.out.print(partMask[i][j] ? "X" : ".");
    //   }
    //   System.out.println();
    // }

    for (int i=0; i<partMask.length; i++) {
      String partNumber = "";
      for (int j=0; j<partMask[i].length; j++) {
        if (partMask[i][j]) {
          partNumber += grid[i][j];
        } else if (!partNumber.isEmpty()) {
          partNumbers.add(Integer.parseInt(partNumber));
          partNumber = "";
        }
      }
      if (!partNumber.isEmpty()) {
        partNumbers.add(Integer.parseInt(partNumber));
      }
    }

    return partNumbers;
  }

  private List<Integer> getGearRatios() {
    List<Integer> gearRatios = new LinkedList<>();

    int[][] gearMask = new int[grid.length][grid[0].length];
    int partId = 1;
    
    for (int i=0; i<gearMask.length; i++) {
      for (int j=0; j<gearMask[i].length; j++) {
        if (grid[i][j].equals('*')) {
          for (int x=i-1; x<=i+1; x++) {
            for (int y=j-1; y<=j+1; y++) {
              if (x >= 0 && x < gearMask.length && y >= 0 && y < gearMask[i].length && Character.isDigit(grid[x][y])) {
                gearMask[x][y] = partId;

                int cursor = y - 1;
                while (cursor >= 0 && Character.isDigit(grid[x][cursor])) {
                  gearMask[x][cursor] = partId;
                  cursor--;
                }
                cursor = y + 1;
                while (cursor < gearMask[x].length && Character.isDigit(grid[x][cursor])) {
                  gearMask[x][cursor] = partId;
                  cursor++;
                }
                partId++;
              }
            }
          }
        }
      }
    }

    Map<Integer, Integer> partNumberMap = new HashMap<>();

    for (int i=0; i<gearMask.length; i++) {
      String partNumber = "";
      partId = 0;
      for (int j=0; j<gearMask[i].length; j++) {
        if (gearMask[i][j] > 0) {
          partId = gearMask[i][j];
          partNumber += grid[i][j];
        } else if (!partNumber.isEmpty()) {
          partNumberMap.put(partId, Integer.parseInt(partNumber));
          partNumber = "";
        }
      }
      if (!partNumber.isEmpty()) {
        partNumberMap.put(partId, Integer.parseInt(partNumber));
      }
    }

    for (int i=0; i<gearMask.length; i++) {
      for (int j=0; j<gearMask[i].length; j++) {
        if (grid[i][j].equals('*')) {
          Set<Integer> adjacentPartNumbers = new HashSet<>();
          for (int x=i-1; x<=i+1; x++) {
            for (int y=j-1; y<=j+1; y++) {
              if (x >= 0 && x < gearMask.length && y >= 0 && y < gearMask[i].length && Character.isDigit(grid[x][y])) {
                adjacentPartNumbers.add(gearMask[x][y]);
              }
            }
          }
          if (adjacentPartNumbers.size() == 2) {
            int gearRatio = 1;
            for (Integer partNumber : adjacentPartNumbers) {
              gearRatio = gearRatio * partNumberMap.get(partNumber);
            }
            gearRatios.add(gearRatio);
          }
        }
      }
    }

    //System.out.println(gearRatios);

    return gearRatios;
  }

  public static void main(String[] args) {
    List<String> lines = InputReader.readLinesFromInput("advent/day03/input.txt");
    EngineSchematic schematic = new EngineSchematic(lines);
    //System.out.println(schematic.getPartNumbers().stream().reduce(0, Integer::sum));
    System.out.println(schematic.getGearRatios().stream().reduce(0, Integer::sum));
  }
}
