public class List {
    private Variable head;
    public boolean isEmpty() {
        return head == null;
    }
    public boolean isExist(String name) {
        Variable pointer = head;
        while(pointer != null) {
            if(pointer.getName().equals(name)) {
                return true;
            }
            pointer = pointer.getNext();
        }
        return false;
    }
    public Variable find(String name) {
        Variable pointer = head;
        while(pointer != null) {
            if(pointer.getName().equals(name)) {
                return pointer;
            }
            pointer = pointer.getNext();
        }
        return null;
    }
    public void add(Variable var) {
        var.setNext(head);
        head = var;
    }
    public void showAll() {
        if(!isEmpty()) {
            Variable pointer = head;
            int counter = 1;
            System.out.println("List of Variables:");
            while(pointer != null) {
                System.out.println(counter + ". " + pointer.getName() + " = " + pointer.getValue());
                pointer = pointer.getNext();
                counter++;
            }
        } else {
            System.out.println("There is no variable.");
        }
    }
}
