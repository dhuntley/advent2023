package advent.day08;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import advent.common.util.InputReader;

public class HauntedWasteland {
  
  private Map<String, Node> nodeMap = new HashMap<>();

  private static class Node {
    final String id;
    Node left;
    Node right;

    public Node(String id) {
      this.id = id;
    }
  }

  private Node getNode(String nodeId) {
    if (!nodeMap.containsKey(nodeId)) {
      nodeMap.put(nodeId, new Node(nodeId));
    }
    return nodeMap.get(nodeId);
  }

  private void addEdges(String parentId, String leftId, String rightId) {
    Node parent = getNode(parentId);
    parent.left = getNode(leftId);
    parent.right = getNode(rightId);
  }

  public static void main(String[] args) {
    List<String> lines = InputReader.readLinesFromInput("advent/day08/input.txt");
    HauntedWasteland wasteland = new HauntedWasteland();
    
    for (String line : lines) {
      String[] tokens = line.split("\\W+");
      wasteland.addEdges(tokens[0], tokens[1], tokens[2]);
    }
    
    String directions = "LRLRLRRLRRRLRRRLRRRLRLRRRLRRRLRRRLLRLRRRLRLRRRLLRRRLRRLRRRLRRLRLRRRLRRRLRLRRLRRRLRRLRRRLRRLRLRRLRRRLRLRRLRRRLLRRRLRLRRLLLRLLRLRRLLRRRLLRLLRRLRLRRRLLLRLRRLRLRRLRRRLRRLLRRLLRLRRRLRRRLRLLLLRLLRLRLRLRRRLRRLRRLRLRRRLLRRLRLLRRLRLRRLRLRLRRLRRLLRLRRLLRLLRRRLLLRRRLRRLRLRRRLRRLRRRLRRLLLRRRR";
    int numSteps = 0;
    
    Node node = wasteland.getNode("AAA");

    while (!node.id.equals("ZZZ")) {
      char move = directions.charAt(numSteps % directions.length());
      node = (move == 'L') ? node.left : node.right;
      numSteps++;
    }
    System.out.println(numSteps);
  }
}
