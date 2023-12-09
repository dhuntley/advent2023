package advent.day08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import advent.common.util.InputReader;

public class HauntedWastelandAdvanced {
  
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
    HauntedWastelandAdvanced wasteland = new HauntedWastelandAdvanced();
    
    for (String line : lines) {
      String[] tokens = line.split("\\W+");
      wasteland.addEdges(tokens[0], tokens[1], tokens[2]);
    }
    
    String directions = "LRLRLRRLRRRLRRRLRRRLRLRRRLRRRLRRRLLRLRRRLRLRRRLLRRRLRRLRRRLRRLRLRRRLRRRLRLRRLRRRLRRLRRRLRRLRLRRLRRRLRLRRLRRRLLRRRLRLRRLLLRLLRLRRLLRRRLLRLLRRLRLRRRLLLRLRRLRLRRLRRRLRRLLRRLLRLRRRLRRRLRLLLLRLLRLRLRLRRRLRRLRRLRLRRRLLRRLRLLRRLRLRRLRLRLRRLRRLLRLRRLLRLLRRRLLLRRRLRRLRLRRRLRRLRRRLRRLLLRRRR";
    long numSteps = 0;
    
    List<Node> nodes = wasteland.nodeMap.keySet().stream().filter(id -> id.endsWith("A")).map(id -> wasteland.getNode(id)).toList();

    List<Set<String>> visitedNodeIds = new ArrayList<>();
    List<Boolean> hasLoop = new ArrayList<>();
    List<Boolean> hasEnd = new ArrayList<>();
    List<Long> numStepsToEnd = new ArrayList<>();
    for (int i = 0; i < nodes.size(); i++) {
      visitedNodeIds.add(new HashSet<>());
      visitedNodeIds.get(i).add(nodes.get(i).id);
      hasLoop.add(Boolean.FALSE);
      hasEnd.add(Boolean.FALSE);
      numStepsToEnd.add(0l);
    }

    while (hasLoop.contains(Boolean.FALSE) || hasEnd.contains(Boolean.FALSE)) {
      char move = directions.charAt((int)(numSteps % (long)directions.length()));
      List<Node> nextNodes = new ArrayList<>();
      for (int i = 0; i < nodes.size(); i++) {
        Node nextNode = (move == 'L') ? nodes.get(i).left : nodes.get(i).right;
        nextNodes.add(nextNode);
        if (visitedNodeIds.get(i).contains(nextNode.id)) {
          hasLoop.set(i, true);
        }
        if (nextNode.id.endsWith("Z")) {
          hasEnd.set(i, true);
          if (numStepsToEnd.get(i) == 0) {
            numStepsToEnd.set(i, numSteps + 1);
          }
        }
        visitedNodeIds.get(i).add(nextNode.id);
      }
      nodes = nextNodes;
      numSteps++;
    }
    System.out.println(numStepsToEnd);
  }
}
