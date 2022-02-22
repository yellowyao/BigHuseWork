package suep.yao.bighusework.entity;

public class City implements Comparable {
    private String name;
    private String first;
    private String second;

    public City(String name) {
        this.name = name;
    }

    public City() {
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", first='" + first + '\'' +
                ", second='" + second + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    @Override
    public int compareTo(Object name) {
        String na = name.toString();
        return this.name.compareTo(na);
    }


}
