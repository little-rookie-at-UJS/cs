import java.io.*;

import java.util.LinkedList;
import java.util.Stack;

public class Binary_tree {
//把string转换成student对象
   Students translate(String str) {
//       通过“ ”分割字符串
       String[] strings = str.split(" ");
//       如果各部分满足要求，并且学号不重复，则创建对象
       if(!strings[0].matches("学号：.*")||
               !strings[1].matches("姓名：.*")||
               !strings[2].matches("高数：.*")||
               !strings[3].matches("英语：.*")||
               !strings[4].matches("离散：.*")||search_id_the(strings[0].replaceAll("学号：",""))!=null) {
//           如果不满足要求，返回null并吧错误信息打印出来
           wrong_list.add(str);
           return null;
       }
//       创建对象
       Students students = new Students(strings[0].replaceAll("学号：",""),
               strings[1].replaceAll("姓名：",""),
               Integer.parseInt(strings[2].replaceAll("高数：","")),
               Integer.parseInt(strings[3].replaceAll("英语：","")),
               Integer.parseInt(strings[4].replaceAll("离散：","")));
//       返回对象
       return students;

   }
   public void add_file(File file)  {
//清楚错误信息
       wrong_list.clear();
       try{
           BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
           String line;
           while ((line = bufferedReader.readLine()) != null) {
               Students students = translate(line);
//           如果对象不为空，就添加到二叉树中
               if(students!=null)
                   add(students);
           }
           bufferedReader.close();
       }catch (Exception e){

       }

   }
   public LinkedList<String> getWrong_list() {
//       返回错误信息
       return wrong_list;
   }
   public void add(Students students ){
//      创建节点
        Node node = new Node(students);
        studentsLinkedList.add(students);
//      如果根节点为空，就把节点设置为根节点
        if (root == null) {
            root = node;
        } else {
//          创建临时节点
            Node temp = root;
            while (true) {
//              如果节点比临时节点小，就把临时节点设置为临时节点的左节点
                if (node.compareTo(temp)) {
                    if (temp.getRight() == null) {
//                        如果临时节点的右节点为空，就把节点设置为临时节点的右节点
                        temp.setRight(node);
                        break;
                    } else {
                        temp = temp.getRight();
                    }
                } else {
//                  如果节点比临时节点大，就把临时节点设置为临时节点的右节点
                    if (temp.getLeft() == null) {
//                        如果临时节点的左节点为空，就把节点设置为临时节点的左节点
                        temp.setLeft(node);
                        break;
                    } else {
                        temp = temp.getLeft();
                    }
                }
            }
        }
    }
//    string->node 的比较
    public boolean compare(String str,Node node){
//        如果字符串比节点小，返回true
        if(str.compareTo(node.getStudents().getId())<0)
            return true;
//        如果字符串比节点大，返回false
        else
            return false;
    }
    boolean equals(String str,Node node){
//        如果字符串和节点相等，返回true
        if(str.equals(node.getStudents().getId()))
            return true;
//        如果字符串和节点不相等，返回false
        else
            return false;
    }
    boolean equals(String str,Students students){
//        如果字符串和节点相等，返回true
        if(str.equals(students.getId()))
            return true;
//        如果字符串和节点不相等，返回false
        else
            return false;
    }
//删除节点
    public void delete(String id){
//      创建临时节点
        Node temp = root;
        Node parent = null;
        while (true) {
//          如果临时节点为空，就返回
            if (temp == null) {
                return;
            }
//          如果字符串和节点相等停止循环
            if (equals(id,temp)) {
                break;
            } else if (compare(id,temp)) {
//              如果字符串比节点小，就把临时节点设置为临时节点的左节点
                parent = temp;
                temp = temp.getLeft();
            } else {
//              如果字符串比节点大，就把临时节点设置为临时节点的右节点
                parent = temp;
                temp = temp.getRight();
            }
        }
//        如果临时节点的左右节点同时为空，
        if (temp.getLeft() == null && temp.getRight() == null) {
//            如果临时节点是父节点的左节点，就把父节点的左节点设置为空
            if (parent.getLeft() == temp) {
                parent.setLeft(null);
            } else {
//                如果临时节点是父节点的右节点，就把父节点的右节点设置为空
                parent.setRight(null);
            }
        } else if (temp.getLeft() != null && temp.getRight() != null) {
//            如果临时节点的左右节点同时不为空，
//            创建临时节点
            Node min = temp.getRight();
            Node minParent = temp;
//            获取最左节点
            while (min.getLeft() != null) {
                minParent = min;
                min = min.getLeft();
            }
//            把最左节点的值赋给临时节点
            temp.setStudents(min.getStudents());
//            如果最左节点是最左节点的父节点的左节点，就把最左节点的父节点的左节点设置最左节点的右节点
            if (minParent.getLeft() == min) {
                minParent.setLeft(min.getRight());
            } else {
//                如果最左节点是最左节点的父节点的右节点，就把最左节点的父节点的右节点设置最左节点的右节点
                minParent.setRight(min.getRight());
            }
        } else {
//            如果临时节点的左右节点不同时为空，
//            如果临时节点的左节点不为空，就把临时节点的左节点赋给临时节点
            if (temp.getLeft() != null) {
                temp.setStudents(temp.getLeft().getStudents());
                temp.setLeft(temp.getLeft().getLeft());
            }else {
//                如果临时节点的右节点不为空，就把临时节点的右节点赋给临时节点
                temp.setStudents(temp.getRight().getStudents());
                temp.setRight(temp.getRight().getRight());
            }
        }

    }
    private void clear(){
//       置空信息
        sum_of_all = 0;
        sum_of_math = 0;
        sum_of_english = 0;
        sum_of_discrete = 0;
        studentsLinkedList.clear();
         max_of_math = 0;
         max_of_english = 0;
         max_of_discrete = 0;
         max_of_all = 0;

    }

    public void initList_up(){
//        初始化链表
        clear();
//        定义栈 模拟递归
        Stack<Node> stack = new Stack<>();
//        把根节点压入栈
        Node temp = root;
        while (temp != null || !stack.isEmpty()) {
//            如果临时节点不为空，就把临时节点压入栈，临时节点设置为临时节点的左节点
            while (temp != null) {
                stack.push(temp);
                temp = temp.getLeft();
            }
            if (!stack.isEmpty()) {
//                如果栈不为空，就把栈顶元素弹出，把临时节点设置为栈顶元素，把临时节点的右节点设置为临时节点
                temp = stack.pop();
                studentsLinkedList.add(temp.getStudents());
                sum_of_math += temp.getStudents().getMath();
                sum_of_english += temp.getStudents().getEnglish();
                sum_of_discrete += temp.getStudents().getDiscrete();
                sum_of_all+=temp.getStudents().getTotal();
                max_of_all = Math.max(max_of_all, temp.getStudents().getTotal());
                max_of_math = Math.max(max_of_math, temp.getStudents().getMath());
                max_of_english = Math.max(max_of_english, temp.getStudents().getEnglish());
                max_of_discrete = Math.max(max_of_discrete, temp.getStudents().getDiscrete());
                temp = temp.getRight();
            }
        }
    }

    public void initList_down(){
//        初始化链表
        clear();
//        定义栈 模拟递归
        Stack<Node> stack = new Stack<>();
        Node temp = root;
        while (temp != null || !stack.isEmpty()) {
//            如果临时节点不为空，就把临时节点压入栈，临时节点设置为临时节点的右节点
            while (temp != null) {
                stack.push(temp);
                temp = temp.getRight();
            }
            if (!stack.isEmpty()) {
//                如果栈不为空，就把栈顶元素弹出，把临时节点设置为栈顶元素，把临时节点的左节点设置为临时节点
                temp = stack.pop();
                studentsLinkedList.add(temp.getStudents());
                sum_of_math += temp.getStudents().getMath();
                sum_of_english += temp.getStudents().getEnglish();
                sum_of_discrete += temp.getStudents().getDiscrete();
                sum_of_all+=temp.getStudents().getTotal();
                max_of_all = Math.max(max_of_all, temp.getStudents().getTotal());
                max_of_math = Math.max(max_of_math, temp.getStudents().getMath());
                max_of_english = Math.max(max_of_english, temp.getStudents().getEnglish());
                max_of_discrete = Math.max(max_of_discrete, temp.getStudents().getDiscrete());
                temp = temp.getLeft();
            }
        }
    }
    public void write_up()  {
//       写入文件
       try{
//          新建文件类
        File file = new File("StudentFile.txt");
//        System.out.println(file.exists());
//        System.out.println(file.getAbsolutePath());
//           io流
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("StudentFile.txt", false));
//        写入文件
        for (Students students : studentsLinkedList) {
            bufferedWriter.write(students.toString());
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
        System.out.println("写入成功");
       }catch (Exception e){
           System.out.println("写入失败");
       }
    }
    Binary_tree(){
        root = null;
    }
//
    Binary_tree(File file){
        add_file(file);
    }
//    通过学号精确查找
    public Students search_id_the(String id){
//       临时节点
        Node temp = root;
        while (true) {
//            如果临时节点为空，就返回null
            if (temp == null) {
                return null;
            }
//            如果临时节点的学号等于要查找的学号，就返回临时节点的学生
            if (equals(id,temp)) {
                return temp.getStudents();
            } else if (compare(id,temp)) {
//                如果临时节点的学号大于要查找的学号，就把临时节点设置为临时节点的左节点
                temp = temp.getLeft();
            } else {
//                如果临时节点的学号小于要查找的学号，就把临时节点设置为临时节点的右节点
                temp = temp.getRight();
            }
        }
    }

//    通过学号模糊查找
    public LinkedList<Students> search_id_fuzzy(String id){
//       初始化链表
        LinkedList<Students> studentsLinkedLists = new LinkedList<>();
        for(Students students : studentsLinkedList){
            if(students.contain_Id(id)){
                studentsLinkedLists.add(students);
            }
        }
////        定义栈 模拟递归
//        Stack<Node> stack = new Stack<>();
////        临时节点
//        Node temp = root;
//        while (temp != null || !stack.isEmpty()) {
////            如果临时节点不为空，就把临时节点压入栈，临时节点设置为临时节点的右节点
//            while (temp != null) {
//                stack.push(temp);
//                temp = temp.getLeft();
//            }
//            if (!stack.isEmpty()) {
////                如果栈不为空，就把栈顶元素弹出，把临时节点设置为栈顶元素，把临时节点的左节点设置为临时节点
//                temp = stack.pop();
//                if (temp.contain_Id(id)) {
////                    如果临时节点的学号包含要查找的学号，就把临时节点的学生加入链表
//                    studentsLinkedList.add(temp.getStudents());
//                }
//                temp = temp.getRight();
//            }
//        }
        return studentsLinkedLists;
    }
//    通过姓名精确查找
    public LinkedList<Students> search_name_the(String name){
//       初始化链表
    LinkedList<Students> studentsLinkedLists = new LinkedList<>();
    for (Students students : studentsLinkedList) {
        if (equals(name,students)) {
            studentsLinkedLists.add(students);
        }
    }
////        定义栈 模拟递归
//    Stack<Node> stack = new Stack<>();
////        临时节点
//    Node temp = root;
//    while (temp != null || !stack.isEmpty()) {
////            如果临时节点不为空，就把临时节点压入栈，临时节点设置为临时节点的右节点
//        while (temp != null) {
//            stack.push(temp);
//            temp = temp.getLeft();
//        }
////        如果栈不为空，就把栈顶元素弹出，把临时节点设置为栈顶元素，把临时节点的左节点设置为临时节点
//        if (!stack.isEmpty()) {
//            temp = stack.pop();
//            if (equals(name,temp)) {
//                studentsLinkedList.add(temp.getStudents());
//            }
//            temp = temp.getRight();
//        }
//    }
    return studentsLinkedList;
}
//    通过姓名模糊查找
    public LinkedList<Students> search_name_fuzzy(String name){
//       初始化链表
        LinkedList<Students> studentsLinkedLists = new LinkedList<>();
        for (Students students : studentsLinkedList) {
            if (students.contain_Name(name)) {
                studentsLinkedLists.add(students);
            }
        }
////        定义栈 模拟递归
//        Stack<Node> stack = new Stack<>();
//        Node temp = root;
////        临时节点
//        while (temp != null || !stack.isEmpty()) {
////            如果临时节点不为空，就把临时节点压入栈，临时节点设置为临时节点的右节点
//            while (temp != null) {
//                stack.push(temp);
//                temp = temp.getLeft();
//            }
//            if (!stack.isEmpty()) {
////                如果栈不为空，就把栈顶元素弹出，把临时节点设置为栈顶元素，把临时节点的左节点设置为临时节点
//                temp = stack.pop();
//                if (temp.contain_Name(name)) {
//                    studentsLinkedList.add(temp.getStudents());
//                }
//                temp = temp.getRight();
//            }
//        }
        return studentsLinkedList;
    }
    public LinkedList<Students> returnList(){
        return studentsLinkedList;
    }
    public double getAverage_math(){
        return 1.0*sum_of_math/studentsLinkedList.size();
    }
    public double getAverage_english(){
        return  1.0*sum_of_english/studentsLinkedList.size();
    }
    public double getAverage_discrete(){
        return 1.0*sum_of_discrete/studentsLinkedList.size();
    }
    public double getAverage_all(){
        return 1.0*sum_of_all/studentsLinkedList.size();
    }
    public int getMax_math(){
        return max_of_math;
    }
    public int getMax_english(){
        return max_of_english;
    }
    public int getMax_discrete(){
        return max_of_discrete;
    }
    public int getMax_all(){
        return max_of_all;
    }
//    定义私有属性
    private LinkedList<Students> studentsLinkedList = new LinkedList<>();
    private Node root;
    private int sum_of_math = 0;
    private int sum_of_english = 0;
    private int sum_of_discrete = 0;
    private int sum_of_all = 0;
    private int max_of_math = 0;
    private int max_of_english = 0;
    private int max_of_discrete = 0;
    private int max_of_all = 0;
    LinkedList<String> wrong_list = new LinkedList<>();

}
