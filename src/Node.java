public class Node {
//    节点类
    private Students students;
//    左节点
    private Node left;
//    右节点
    private Node right;
    public Node(Students students) {
//        构造函数
        this.students = students;
    }
    public Boolean compareTo(Node node) {
//        比较函数 用于比较两个节点的大小
        if (this.students.getId().compareTo(node.students.getId()) > 0) {
            return true;
        } else {
            return false;
        }
    }
    boolean contain_Id(String id) {
//        判断是否包含某个学号
        if (students.contain_Id(id)) {
            return true;
        } else {
            return false;
        }
    }
    boolean contain_Name(String name) {
//        判断是否包含某个姓名
        if (students.contain_Name(name)) {
            return true;
        } else {
            return false;
        }
    }

    Node getLeft() {
//        获取左节点
        return left;
    }
    Node getRight() {
//        获取右节点
        return right;
    }
    Students getStudents() {
//        获取学生信息
        return students;
    }
    void setRight(Node right) {
//        设置右节点
        this.right = right;
    }
    void setStudents(Students students) {
//        设置学生信息
        this.students = students;
    }
    void setLeft(Node left) {
//        设置左节点
        this.left = left;
    }
}
