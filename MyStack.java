public class MyStack {
    private SNode last;
    public boolean isEmpty() {
        return last == null;
    }
    public void push() {
        SNode sample = new SNode();
        if(!isEmpty()) {
            sample.setNext(last);
        }
        last = sample;
    }
    public void pop() throws Exception {
        if(!isEmpty()) {
            last = last.getNext();
        } else {
            throw new Exception();
        }
    }
}
