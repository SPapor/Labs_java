import java.util.*;

class Book {
    String title, author, publisher;
    int year, pages;
    double price;

    Book(String title, String author, String publisher, int year, int pages, double price) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("\"%s\" — %s, %s, %d р., %d стор., %.2f грн",
                title, author, publisher, year, pages, price);
    }
}

class PublisherComparator implements Comparator<Book> {
    public int compare(Book a, Book b) {
        return a.publisher.compareToIgnoreCase(b.publisher);
    }
}

class Model {
    Book[] books;

    Model(Book[] books) { this.books = books; }

    List<Book> byAuthor(String author) {
        List<Book> res = new ArrayList<>();
        for (Book b : books) if (b.author.equalsIgnoreCase(author)) res.add(b);
        return res;
    }

    List<Book> byPublisher(String publisher) {
        List<Book> res = new ArrayList<>();
        for (Book b : books) if (b.publisher.equalsIgnoreCase(publisher)) res.add(b);
        return res;
    }

    List<Book> afterYear(int year) {
        List<Book> res = new ArrayList<>();
        for (Book b : books) if (b.year > year) res.add(b);
        return res;
    }

    void sortByPublisher() {
        Arrays.sort(books, new PublisherComparator());
    }
}

class View {
    void printBooks(Book[] books, String title) {
        System.out.println("\n    " + title + "    ");
        for (int i = 0; i < books.length; i++)
            System.out.println((i + 1) + ". " + books[i]);
        System.out.println();
    }

    void printList(List<Book> list, String title) {
        System.out.println("\n    " + title + "    ");
        if (list.isEmpty()) {
            System.out.println("Нічого не знайдено.");
        } else {
            for (int i = 0; i < list.size(); i++)
                System.out.println((i + 1) + ". " + list.get(i));
        }
        System.out.println();
    }

    void printMenu() {
        System.out.println("    МЕНЮ    ");
        System.out.println("1. Книги заданого автора");
        System.out.println("2. Книги заданого видавництва");
        System.out.println("3. Книги після заданого року");
        System.out.println("4. Відсортувати за видавництвом");
        System.out.println("0. Вихід");
        System.out.print("Виберіть: ");
    }

    void msg(String s) { System.out.println(s); }
}

class Controller {
    Model model;
    View view;
    Scanner sc = new Scanner(System.in);

    String[] authors    = {"Тарас Шевченко", "Леся Українка", "Іван Франко", "Іван Багряний"};
    String[] publishers = {"Фоліо", "А-БА-БА-ГА-ЛА-МА-ГА", "Освіта", "Ранок"};
    int[]    years      = {2000, 1990, 2010, 1985};

    Controller(Model model, View view) {
        this.model = model;
        this.view  = view;
    }

    void run() {
        Book[] books = model.books;
        view.printBooks(books, "ПОЧАТКОВИЙ СПИСОК КНИГ");

        int choice;
        do {
            view.printMenu();
            choice = readInt();

            switch (choice) {
                case 1 -> {
                    String author = random(authors);
                    view.msg("Пошук автора: " + author);
                    view.printList(model.byAuthor(author), "Книги автора: " + author);
                }
                case 2 -> {
                    String pub = random(publishers);
                    view.msg("Пошук видавництва: " + pub);
                    view.printList(model.byPublisher(pub), "Книги видавництва: " + pub);
                }
                case 3 -> {
                    int year = random(years);
                    view.msg("Пошук після " + year + " року");
                    view.printList(model.afterYear(year), "Книги після " + year + " року");
                }
                case 4 -> {
                    model.sortByPublisher();
                    view.printBooks(books, "ВІДСОРТОВАНО ЗА ВИДАВНИЦТВОМ");
                }
                case 0 -> view.msg("До побачення!");
                default -> view.msg("Невірний вибір!");
            }
        } while (choice != 0);
    }

    int readInt() {
        while (!sc.hasNextInt()) {
            view.msg("Введіть число!");
            sc.next();
        }
        int v = sc.nextInt();
        sc.nextLine();
        return v;
    }

    <T> T random(T[] arr) {
        return arr[new Random().nextInt(arr.length)];
    }

    int random(int[] arr) {
        return arr[new Random().nextInt(arr.length)];
    }
}

public class lab_3 {
    public static void main(String[] args) {
        Book[] books = {
                new Book("Кобзар", "Тарас Шевченко", "Фоліо", 2005, 320, 250.00),
                new Book("Лісова пісня", "Леся Українка", "А-БА-БА-ГА-ЛА-МА-ГА", 2010, 180, 180.50),
                new Book("Камінний хрест", "Іван Франко", "Освіта", 1998, 240, 120.00),
                new Book("Сад Гетсиманський", "Іван Багряний", "Ранок", 2015, 400, 320.00),
                new Book("Тигролови", "Іван Багряний", "Фоліо", 2008, 380, 290.00),
                new Book("Майстер і Маргарита", "Михайло Булгаков", "А-БА-БА-ГА-ЛА-МА-ГА", 2018, 480, 410.00),
                new Book("Захар Беркут", "Іван Франко", "Освіта", 2003, 260, 160.00),
                new Book("Енеїда", "Іван Котляревський", "Фоліо", 1995, 200, 140.00),
                new Book("Молитва за гетьмана Мазепу", "Богдан Лепкий", "Ранок", 2012, 550, 380.00),
                new Book("Чорний ворон", "Василь Шкляр", "А-БА-БА-ГА-ЛА-МА-ГА", 2011, 420, 360.00)
        };

        Model model = new Model(books);
        View  view  = new View();
        new Controller(model, view).run();
    }
}