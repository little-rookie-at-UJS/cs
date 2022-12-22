public class Node {
    private Students students;
    private Node left;
    private Node right;
    public Node(Students students) {
        this.students = students;
    }
    Node getLeft() {
        return left;
    }
    void setLeft(Node left) {
        this.left = left;
    }
    Node getRight() {
        return right;
    }
    void setRight(Node right) {
        this.right = right;
    }
    Students getStudents() {
        return students;
    }
    void setStudents(Students students) {
        this.students = students;
    }
}
