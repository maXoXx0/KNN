import java.util.*;

public class Attribute {

    float[] attributes;
    String decisionAttribute;

    public Attribute(String decisionAttribute, float[] attributes) {
        this.attributes = attributes;
        this.decisionAttribute = decisionAttribute;
    }

    public static void KNN(int k, List<Attribute> allAttributes, float[] newAttribute) {
        Map<Float, String> map = new HashMap<>();
        double same = 0.0001;
        for (int i = 0; i < allAttributes.size(); i++) {
            float diff = 0;
            for (int j = 0; j < newAttribute.length; j++) {
                diff += Math.abs(allAttributes.get(i).attributes[j] - newAttribute[j]);
            }

            while (map.containsKey(diff)){
                diff += same;
                same += 0.0001;
            }
            map.put(diff, allAttributes.get(i).decisionAttribute);
        }
        List<Float> sortedMapKeys = new ArrayList<>(map.keySet());
        Collections.sort(sortedMapKeys);

        List<String> closest = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            closest.add(map.get(sortedMapKeys.get(i)));
        }

        String newDecisionAttribute = countMajority(closest);
        Attribute newestAtt = new Attribute(newDecisionAttribute, newAttribute);
        allAttributes.add(newestAtt);
    }

    public static String countMajority(List<String> closest) {
        String majority = "";
        Map<String, Integer> countDecisionAttributes = new HashMap<>();
        for (String close : closest) {
            int count = countDecisionAttributes.getOrDefault(close, 0);
            countDecisionAttributes.put(close, count + 1);
        }
        int maximum = -1;
        for (Map.Entry<String, Integer> entry : countDecisionAttributes.entrySet()) {
            if (entry.getValue() > maximum) {
                majority = entry.getKey();
                maximum = entry.getValue();
            }
        }
        return majority;
    }

    public static void print(List<Attribute> list) {
        for (Attribute att : list) {
            for (int i = 0; i < att.attributes.length; i++) {
                System.out.print(att.attributes[i] + " ");
            }
            System.out.println(att.decisionAttribute);

        }
    }
}
