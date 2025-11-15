import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Dictionary {
    private Map<String, String> dict = new HashMap<>();

    public Dictionary() {
        dict.put("креветка","shrimp");
        dict.put("phone", "телефон");
        dict.put("giraffe","жираф");
        dict.put("ninja","ніндзя");
        dict.put("scissors","ножиці");
    }

    public void addWord(String eng, String ukr) {
        dict.put(eng.toLowerCase(), ukr.toLowerCase());
    }

    public String translateWord(String eng) {
        return dict.getOrDefault(eng.toLowerCase(), "{" + eng + "}");
    }

    public String translatePhrase(String phrase) {
        String[] words = phrase.toLowerCase().split(" ");
        StringBuilder result = new StringBuilder();

        for (String w : words) {
            result.append(translateWord(w)).append(" ");
        }
        return result.toString().trim();
    }
}

public class lab_6 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Dictionary dictionary = new Dictionary();

        while (true) {
            System.out.print("Додати слово? (y/n): ");
            String ans = scanner.nextLine();

            if (ans.equalsIgnoreCase("n")) break;

            System.out.print("Англійське слово: ");
            String eng = scanner.nextLine();

            System.out.print("Український переклад: ");
            String ukr = scanner.nextLine();

            dictionary.addWord(eng, ukr);
            System.out.println("Додано!");
        }

        System.out.println("Введіть англійську фразу:");
        String phrase = scanner.nextLine();

        String translated = dictionary.translatePhrase(phrase);

        System.out.println("Переклад:");
        System.out.println(translated);
    }
}
