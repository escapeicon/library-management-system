import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class test {
    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Animal("zs",12));

        Animal zs = new Animal("zs", 12);
        System.out.println(arrayList.contains(zs));
    }
}

class Animal{
    String name;
    int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Animal() {
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
                Objects.equals(name, animal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
