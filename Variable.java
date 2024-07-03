public class Variable {
    private String name;
    private int value;
    private Variable next;
    public void setName(String name) {
        this.name = name;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public int getValue() {
        return value;
    }
    public void setNext(Variable next) {
        this.next = next;
    }
    public Variable getNext() {
        return next;
    }
}
