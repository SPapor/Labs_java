import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;


public class lab_5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Task 1: Line with the maximum number of words");
            System.out.println("2. Task 2: OOP");
            System.out.println("3. Task 3: File Encryption/Decryption");
            System.out.println("4. Task 4: Count HTML tags from URL");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Enter file path: ");
                        String path1 = sc.nextLine();
                        String maxLine = Helpers.findMaxWordsLine(path1);
                        System.out.println("Line with the most words:\n" + maxLine);
                        break;
                    case "2":
                        BookManager_5.run(sc);
                        break;
                    case "3":
                        System.out.print("Enter input file: ");
                        String inPath = sc.nextLine();
                        System.out.print("Enter output file: ");
                        String outPath = sc.nextLine();
                        System.out.print("Enter key: ");
                        char key = sc.nextLine().charAt(0);
                        System.out.print("Encrypt (e) / Decrypt (d)? ");
                        String mode = sc.nextLine();
                        if (mode.equalsIgnoreCase("e")) {
                            Helpers.encryptFile(inPath, outPath, key);
                            System.out.println("File encrypted.");
                        } else if (mode.equalsIgnoreCase("d")) {
                            Helpers.decryptFile(inPath, outPath, key);
                            System.out.println("File decrypted.");
                        } else {
                            System.out.println("Invalid mode.");
                        }
                        break;
                    case "4":
                        System.out.print("Enter URL: ");
                        String url = sc.nextLine();
                        Map<String, Integer> freq = Helpers.countHtmlTags(url);

                        System.out.println("\nTags alphabetically:");
                        freq.entrySet().stream()
                                .sorted(Map.Entry.comparingByKey())
                                .forEach(e -> System.out.printf("%-15s: %d\n", e.getKey(), e.getValue()));

                        System.out.println("\nTags by frequency:");
                        freq.entrySet().stream()
                                .sorted(Map.Entry.comparingByValue())
                                .forEach(e -> System.out.printf("%-15s: %d\n", e.getKey(), e.getValue()));
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }
}


class Helpers {

    public static String findMaxWordsLine(String filePath) throws IOException {
        String maxLine = "";
        int maxWords = -1;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                int words = line.trim().isEmpty() ? 0 : line.trim().split("\\s+").length;
                if (words > maxWords) {
                    maxWords = words;
                    maxLine = line;
                }
            }
        }
        return maxLine;
    }

    public static void encryptFile(String inPath, String outPath, char key) throws IOException {
        try (
                InputStream in = new FileInputStream(inPath);
                OutputStream out = new EncryptOutputStream(new FileOutputStream(outPath), key)
        ) {
            in.transferTo(out);
        }
    }

    public static void decryptFile(String inPath, String outPath, char key) throws IOException {
        try (
                InputStream in = new DecryptInputStream(new FileInputStream(inPath), key);
                OutputStream out = new FileOutputStream(outPath)
        ) {
            in.transferTo(out);
        }
    }

    public static Map<String, Integer> countHtmlTags(String urlStr) throws IOException {
        Map<String, Integer> freq = new HashMap<>();
        Pattern p = Pattern.compile("<\\s*([a-zA-Z0-9]+)");
        URL url = new URL(urlStr);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher m = p.matcher(line);
                while (m.find()) {
                    String tag = m.group(1).toLowerCase();
                    freq.put(tag, freq.getOrDefault(tag, 0) + 1);
                }
            }
        }
        return freq;
    }
}


class EncryptOutputStream extends FilterOutputStream {
    private final int key;
    public EncryptOutputStream(OutputStream out, char key) {
        super(out);
        this.key = (int) key;
    }
    @Override
    public void write(int b) throws IOException {
        super.write(b + key);
    }
}

class DecryptInputStream extends FilterInputStream {
    private final int key;
    public DecryptInputStream(InputStream in, char key) {
        super(in);
        this.key = (int) key;
    }
    @Override
    public int read() throws IOException {
        int b = super.read();
        return (b == -1) ? -1 : (b - key);
    }
}



class BookManager_5 {

    public static void run(Scanner mainScanner) {
        Book_5[] books = {
                new Book_5("Кобзар", "Тарас Шевченко", "Фоліо", 2005, 320, 250.00),
                new Book_5("Лісова пісня", "Леся Українка", "А-БА-БА-ГА-ЛА-МА-ГА", 2010, 180, 180.50),
                new Book_5("Камінний хрест", "Іван Франко", "Освіта", 1998, 240, 120.00),
                new Book_5("Сад Гетсиманський", "Іван Багряний", "Ранок", 2015, 400, 320.00),
                new Book_5("Тигролови", "Іван Багряний", "Фоліо", 2008, 380, 290.00),
                new Book_5("Майстер і Маргарита", "Михайло Булгаков", "А-БА-БА-ГА-ЛА-МА-ГА", 2018, 480, 410.00),
                new Book_5("Захар Беркут", "Іван Франко", "Освіта", 2003, 260, 160.00),
                new Book_5("Енеїда", "Іван Котляревський", "Фоліо", 1995, 200, 140.00),
                new Book_5("Молитва за гетьмана Мазепу", "Богдан Лепкий", "Ранок", 2012, 550, 380.00),
                new Book_5("Чорний ворон", "Василь Шкляр", "А-БА-БА-ГА-ЛА-МА-ГА", 2011, 420, 360.00)
        };
        Model_5 model = new Model_5(books);
        View_5 view  = new View_5();
        Controller_5 controller = new Controller_5(model, view, mainScanner);
        controller.run();
    }
}


class Book_5 implements Serializable {
    private static final long serialVersionUID = 1L;
    String title, author, publisher;
    int year, pages;
    double price;

    Book_5(String title, String author, String publisher, int year, int pages, double price) {
        this.title = title; this.author = author; this.publisher = publisher;
        this.year = year; this.pages = pages; this.price = price;
    }
    @Override
    public String toString() {
        return String.format("\"%s\" — %s, %s, %d y., %d pages, %.2f UAH",
                title, author, publisher, year, pages, price);
    }
}

class PublisherComparator_5 implements Comparator<Book_5>, Serializable {
    private static final long serialVersionUID = 2L;
    public int compare(Book_5 a, Book_5 b) {
        return a.publisher.compareToIgnoreCase(b.publisher);
    }
}

class BookFileStorage_5 {
    public static void saveBooks(Book_5[] books, String filename) {
        try {
            File file = new File(filename);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(books);
                System.out.println("Data successfully saved to file: " + filename);
            }
        } catch (IOException e) {
            System.err.println("File save error: " + e.getMessage());
        }
    }

    public static Book_5[] loadBooks(String filename) {
        Book_5[] books = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            books = (Book_5[]) ois.readObject();
            System.out.println("Data successfully loaded from file: " + filename);
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found: " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Load error: " + e.getMessage());
        }
        return books;
    }
}

class Model_5 {
    Book_5[] books;
    Model_5(Book_5[] books) { this.books = books; }
    public void setBooks(Book_5[] books) { this.books = books; }

    List<Book_5> byAuthor(String author) {
        List<Book_5> res = new ArrayList<>();
        if (books == null) return res;
        for (Book_5 b : books) if (b.author.equalsIgnoreCase(author)) res.add(b);
        return res;
    }
    List<Book_5> byPublisher(String publisher) {
        List<Book_5> res = new ArrayList<>();
        if (books == null) return res;
        for (Book_5 b : books) if (b.publisher.equalsIgnoreCase(publisher)) res.add(b);
        return res;
    }
    List<Book_5> afterYear(int year) {
        List<Book_5> res = new ArrayList<>();
        if (books == null) return res;
        for (Book_5 b : books) if (b.year > year) res.add(b);
        return res;
    }
    void sortByPublisher() {
        if (books == null) return;
        Arrays.sort(books, new PublisherComparator_5());
    }
}

class View_5 {
    void printBooks(Book_5[] books, String title) {
        System.out.println("\n    " + title + "    ");
        if (books == null || books.length == 0) {
            System.out.println("The list is empty.");
            return;
        }
        for (int i = 0; i < books.length; i++)
            System.out.println((i + 1) + ". " + books[i]);
        System.out.println();
    }
    void printList(List<Book_5> list, String title) {
        System.out.println("\n    " + title + "    ");
        if (list.isEmpty()) {
            System.out.println("Nothing found.");
        } else {
            for (int i = 0; i < list.size(); i++)
                System.out.println((i + 1) + ". " + list.get(i));
        }
        System.out.println();
    }
    void printMenu() {
        System.out.println("1. Books by author");
        System.out.println("2. Books by publisher");
        System.out.println("3. Books after year");
        System.out.println("4. Sort by publisher");
        System.out.println("5. Save to file");
        System.out.println("6. Load from file");
        System.out.println("0. Return to main menu");
        System.out.print("Choose: ");
    }
    void msg(String s) { System.out.println(s); }
    void print(String s) { System.out.print(s); }
}

class Controller_5 {
    Model_5 model;
    View_5 view;
    Scanner sc;

    Controller_5(Model_5 model, View_5 view, Scanner scanner) {
        this.model = model;
        this.view  = view;
        this.sc = scanner;
    }

    void run() {
        int choice;
        do {
            view.printMenu();
            choice = readInt();

            switch (choice) {
                case 1:
                    String author = readString("Enter author: ");
                    view.printList(model.byAuthor(author), "Books by author: " + author);
                    break;
                case 2:
                    String pub = readString("Enter publisher: ");
                    view.printList(model.byPublisher(pub), "Books by publisher: " + pub);
                    break;
                case 3:
                    view.print("Enter year: ");
                    int year = readInt();
                    view.printList(model.afterYear(year), "Books after year " + year);
                    break;
                case 4:
                    model.sortByPublisher();
                    view.printBooks(model.books, "SORTED BY PUBLISHER");
                    break;
                case 5:
                    String fSave = readString("Enter filename to save: ");
                    BookFileStorage_5.saveBooks(model.books, fSave);
                    break;
                case 6:
                    String fLoad = readString("Enter filename to load: ");
                    Book_5[] loaded = BookFileStorage_5.loadBooks(fLoad);
                    if (loaded != null) {
                        model.setBooks(loaded);
                        view.printBooks(model.books, "LOADED BOOK LIST");
                    }
                    break;
                case 0:
                    view.msg("Returning to main menu");
                    break;
                default:
                    view.msg("Invalid choice!");
            }
        } while (choice != 0);
    }

    String readString(String prompt) {
        view.print(prompt);
        return sc.nextLine();
    }

    int readInt() {
        while (true) {
            try {
                int v = Integer.parseInt(sc.nextLine());
                return v;
            } catch (NumberFormatException e) {
                view.msg("Error: Enter a number!");
                view.print("Choose: ");
            }
        }
    }
}
