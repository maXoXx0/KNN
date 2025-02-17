import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);


        List<String[]> trainigSet = readFile(args[1]);

        List<Attribute> allAttributes = new ArrayList<>();

        for (String[] arr : trainigSet) {
            float[] att = new float[arr.length - 1];
            for (int i = 0; i < arr.length - 1; i++) {
                att[i] = Float.parseFloat(arr[i]);
            }
            String decAtt = arr[arr.length - 1];

            Attribute attribute = new Attribute(decAtt, att);
            allAttributes.add(attribute);
        }

        List<String[]> testSet = readFile(args[2]);

        for (String[] set : testSet) {
            float[] line = new float[set.length - 1];
            for (int i = 0; i < set.length - 1; i++) {
                line[i] = Float.parseFloat(set[i]);
            }
            Attribute.KNN(k, allAttributes, line);
        }

        Attribute.print(allAttributes);
        accuracy(allAttributes, testSet);


        int length = trainigSet.get(0).length - 1;
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Czy chcesz dodac nową wartość: " +
                    "\n1. Tak" +
                    "\n2. Nie");
            int choice = in.nextInt();
            if (choice == 2) {
                break;
            } else if (choice != 1 && choice != 2) {
                System.out.println("Podano nie odpowiednią odpowiedz. Spróbuj ponownie");
            } else if (choice == 1) {
                System.out.println("Podaj " + length + " wartosci po jednej: (Przecinek zamiast kropki w konsoli)");
                float[] newAtt = new float[length];
                for (int i = 0; i < newAtt.length; i++) {
                    newAtt[i] = in.nextFloat();
                }
                Attribute.KNN(k, allAttributes, newAtt);
                Attribute.print(allAttributes);
                System.out.println("Wyżej najnowsza wartosc\n");
            }

        }
    }

    public static List<String[]> readFile(String path) {
        List<String[]> data = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) {
                String[] line = reader.nextLine().split(";");
                data.add(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public static void accuracy(List<Attribute> tested, List<String[]> answers) {
        double accurate = 0;
        int j = 0;
        for (int i = answers.size() - 1; i >= 0; i--) {
            j += 1;
            if (answers.get(i)[answers.get(i).length - 1].equals(tested.get(tested.size() - 1 - j).decisionAttribute)) {
                accurate++;
            }
        }
        double accuracy =  accurate / answers.size();
        System.out.println("Accuraccy: " + accuracy);

    }

}