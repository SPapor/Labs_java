import java.util.ArrayList;
import java.util.List;

abstract class Animal {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " '" + name + "'";
    }
}


abstract class Mammal extends Animal {
    public Mammal(String name) {
        super(name);
    }
}


abstract class Bird extends Animal {
    public Bird(String name) {
        super(name);
    }
}


class Lion extends Mammal {
    public Lion(String name) {
        super(name);
    }
}


abstract class Ungulate extends Mammal {
    public Ungulate(String name) {
        super(name);
    }
}


class Zebra extends Ungulate {
    public Zebra(String name) {
        super(name);
    }
}


class Giraffe extends Ungulate {
    public Giraffe(String name) {
        super(name);
    }
}


class Eagle extends Bird {
    public Eagle(String name) {
        super(name);
    }
}


class CageFullException extends Exception {
    public CageFullException(String message) {
        super(message);
    }
}


class AnimalNotFoundException extends Exception {
    public AnimalNotFoundException(String message) {
        super(message);
    }
}


abstract class Cage<T extends Animal> {
    private final int maxCapacity;
    protected final List<T> animals;

    public Cage(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.animals = new ArrayList<>(maxCapacity);
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }


    public int getOccupiedSeats() {
        return animals.size();
    }

    public void addAnimal(T animal) throws CageFullException {
        if (animals.size() >= maxCapacity) {
            throw new CageFullException("Вольєр повний! Неможливо додати " + animal);
        }
        System.out.println("Додаємо " + animal + " у " + this.getClass().getSimpleName());
        animals.add(animal);
    }

    public void removeAnimal(T animal) throws AnimalNotFoundException {
        if (!animals.remove(animal)) {
            throw new AnimalNotFoundException(animal + " не знайдено у вольєрі.");
        }
        System.out.println("Вилучаємо " + animal + " з " + this.getClass().getSimpleName());
    }

    public List<T> getAnimals() {
        return new ArrayList<>(animals);
    }
}

class BirdCage<T extends Bird> extends Cage<T> {
    public BirdCage(int maxCapacity) {
        super(maxCapacity);
    }
}

class LionCage<T extends Lion> extends Cage<T> {
    public LionCage(int maxCapacity) {
        super(maxCapacity);
    }
}

class UngulateCage<T extends Ungulate> extends Cage<T> {
    public UngulateCage(int maxCapacity) {
        super(maxCapacity);
    }
}


public class lab_4 {

    public List<Cage<? extends Animal>> cages = new ArrayList<>();

    public void addCage(Cage<? extends Animal> cage) {
        cages.add(cage);
    }

    public int getCountOfAnimals() {
        int totalCount = 0;
        for (Cage<? extends Animal> cage : cages) {
            totalCount += cage.getOccupiedSeats();
        }
        return totalCount;
    }

    public static void main(String[] args) {

        try {
            lab_4 myZoo = new lab_4();

            LionCage<Lion> lionCage = new LionCage<>(5);
            UngulateCage<Ungulate> ungulateCage = new UngulateCage<>(10);
            BirdCage<Bird> birdCage = new BirdCage<>(20);

            myZoo.addCage(lionCage);
            myZoo.addCage(ungulateCage);
            myZoo.addCage(birdCage);

            Lion simba = new Lion("Alex");
            Zebra marty = new Zebra("Marty");
            Giraffe melman = new Giraffe("Malman");
            Eagle skylar = new Eagle("Orel");

            lionCage.addAnimal(simba);

            ungulateCage.addAnimal(marty);
            ungulateCage.addAnimal(melman);

            birdCage.addAnimal(skylar);

            System.out.println("Загальна кількість: " + myZoo.getCountOfAnimals());

            ungulateCage.removeAnimal(marty);
            System.out.println("Загальна кількість: " + myZoo.getCountOfAnimals());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}