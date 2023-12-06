package advent.day05;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import advent.common.util.InputReader;

public class Almanac {
  
  private List<AlmanacTransform> transforms = new LinkedList<>();

  public void addTransform(AlmanacTransform transform) {
    transforms.add(transform);
  }

  public long transform(long source) {
    for (AlmanacTransform transform : transforms) {
      source = transform.transform(source);
    }
    return source;
  }

  public List<Range> transform(List<Range> sourceRanges) {
    List<Range> currentRanges = new LinkedList<>();
    List<Range> transformedRanges = new LinkedList<>();

    currentRanges.addAll(sourceRanges);

    for (AlmanacTransform transform : transforms) {
      for (Range range : currentRanges) {
        transformedRanges.addAll(transform.transform(range));
      }
      currentRanges = transformedRanges;
      transformedRanges = new LinkedList<>();
    }

    return currentRanges;
  }

  private static class Range {
    protected long start;
    protected long end; // Exclusive

    public Range(long start, long end) {
      this.start = start;
      this.end = end;
    }
  }

  private static class AlmanacTransform {
    
    private List<AlmanacTransformRule> rules = new LinkedList<>();

    public void addRule(String line) {
      rules.add(new AlmanacTransformRule(line));
      rules.sort((o1, o2) -> Long.compare(o1.sourceRangeStart, o2.sourceRangeStart));
    }

    public long transform(long source) {
      for (AlmanacTransformRule rule : rules) {
        if (source >= rule.sourceRangeStart && source < rule.sourceRangeStart + rule.rangeLength) {
          return rule.destRangeStart + (source - rule.sourceRangeStart);
        }
      }

      return source;
    }

    public List<Range> transform(Range sourceRange) {
      List<Range> destRanges = new LinkedList<>();

      Iterator<AlmanacTransformRule> rulesIter = rules.iterator();

      while (rulesIter.hasNext()) {
        AlmanacTransformRule rule = rulesIter.next();
        long offset = rule.destRangeStart - rule.sourceRangeStart;
        long ruleSourceRangeEnd = rule.sourceRangeStart + rule.rangeLength;
        long ruleDestRangeEnd = rule.destRangeStart + rule.rangeLength;

        if (sourceRange.start >= ruleSourceRangeEnd) {
          continue;
        }
        if (sourceRange.end <= rule.sourceRangeStart) {
          destRanges.add(new Range(sourceRange.start, sourceRange.end));
          return destRanges;
        }
        if (sourceRange.start < rule.sourceRangeStart && sourceRange.end > rule.sourceRangeStart) {
          destRanges.add(new Range(sourceRange.start, rule.sourceRangeStart));
        }
        if (sourceRange.end > rule.sourceRangeStart && sourceRange.end <= ruleSourceRangeEnd) {
          destRanges.add(new Range(sourceRange.start + offset, sourceRange.end + offset));
          return destRanges;
        } else if (sourceRange.end > ruleSourceRangeEnd) {
          destRanges.add(new Range(sourceRange.start + offset, ruleDestRangeEnd));
          sourceRange = new Range(ruleSourceRangeEnd, sourceRange.end);
        } else {
          System.err.println("NO!");
          System.exit(1);
        }        
      }

      // Add remaining unresolved range to destRanges
      destRanges.add(new Range(sourceRange.start, sourceRange.end));

      return destRanges;
    }

    private static class AlmanacTransformRule {
      protected long sourceRangeStart;
      protected long destRangeStart;
      protected long rangeLength;

      public AlmanacTransformRule(String line) {
        String[] tokens = line.split(" ");
        destRangeStart = Long.parseLong(tokens[0]);
        sourceRangeStart = Long.parseLong(tokens[1]);
        rangeLength = Long.parseLong(tokens[2]);
      }
    }
  }

  public static void main(String[] args) {
    List<String> seedLines = InputReader.readLinesFromInput("advent/day05/seeds.txt");
    //List<Long> seeds = Arrays.asList(seedLines.get(0).split(" ")).stream().map(Long::parseLong).toList();

    List<Range> ranges = new LinkedList<>();

    String[] seedTokens = seedLines.get(0).split(" ");
    for (int i=0; i*2 <= seedTokens.length; i+=2) {
      long start = Long.parseLong(seedTokens[i]);
      long range = Long.parseLong(seedTokens[i+1]);
      long end = start + range;
      ranges.add(new Range(start, end));
    }

    List<String> almanacLines = InputReader.readLinesFromInput("advent/day05/almanac.txt");
    
    Almanac almanac = new Almanac();
    AlmanacTransform transform = null;

    for (String line : almanacLines) {
      if (line.isEmpty()) {
        continue;
      } else if (!Character.isDigit(line.charAt(0))) {
        transform = new AlmanacTransform();
        almanac.addTransform(transform);
      } else {
        transform.addRule(line);
      }
    }

    List<Range> transformedRanges = almanac.transform(ranges);
    long minValue = Long.MAX_VALUE;
    for (Range range : transformedRanges) {
      if (range.start < minValue) {
        minValue = range.start;
      }
    }

    System.out.println(minValue);
  }
}
