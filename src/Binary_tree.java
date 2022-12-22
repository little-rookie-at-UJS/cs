import java.io.*;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

public class Binary_tree {
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
//    LinkedList<Students> has_occur = new LinkedList<>();
   public void create(Students...Students) {
       for (Students students : Students) {
           add(students);
       }
   }
   public void add_file(File file) throws IOException {
       wrong_list.clear();
       BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
       String line;
       while ((line = bufferedReader.readLine()) != null) {
           String[] strings = line.split(" ");
           if(!strings[0].matches("学号：.*")||
                   !strings[1].matches("姓名：.*")||
                   !strings[2].matches("高数：.*")||
                   !strings[3].matches("英语：.*")||
                   !strings[4].matches("离散：.*")){
               wrong_list.add(line);
           }
           else {
               Students students = new Students(strings[0].replaceAll("学号：",""),
                       strings[1].replaceAll("姓名：",""),
                       Integer.parseInt(strings[2].replaceAll("高数：","")),
                       Integer.parseInt(strings[3].replaceAll("英语：","")),
                       Integer.parseInt(strings[4].replaceAll("离散：","")));
               if(search_id_the(strings[0].replaceAll("学号：",""))!=null){
                   wrong_list.add(line);
               }
            else
               add(students);
           }

       }
   }
   public LinkedList<String> getWrong_list() {
       return wrong_list;
   }
   public void add(Students students ){
        Node node = new Node(students);
        studentsLinkedList.add(students);
        if (root == null) {
            root = node;
        } else {
            Node temp = root;
            while (true) {
                if (students.getId().compareTo(temp.getStudents().getId()) < 0) {
                    if (temp.getLeft() == null) {
                        temp.setLeft(node);
                        break;
                    } else {
                        temp = temp.getLeft();
                    }
                } else {
                    if (temp.getRight() == null) {
                        temp.setRight(node);
                        break;
                    } else {
                        temp = temp.getRight();
                    }
                }
            }
        }
    }

    public void delete(String id){
        Node temp = root;
        Node parent = null;
        while (true) {
            if (temp == null) {
                return;
            }
            if (id.equals(temp.getStudents().getId())) {
                break;
            } else if (id.compareTo(temp.getStudents().getId()) < 0) {
                parent = temp;
                temp = temp.getLeft();
            } else {
                parent = temp;
                temp = temp.getRight();
            }
        }
        if (temp.getLeft() == null && temp.getRight() == null) {
            if (parent.getLeft() == temp) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        } else if (temp.getLeft() != null && temp.getRight() != null) {
            Node min = temp.getRight();
            Node minParent = temp;
            while (min.getLeft() != null) {
                minParent = min;
                min = min.getLeft();
            }
            temp.setStudents(min.getStudents());
            if (minParent.getLeft() == min) {
                minParent.setLeft(min.getRight());
            } else {
                minParent.setRight(min.getRight());
            }
        } else {
            if (temp.getLeft() != null) {
                if (parent.getLeft() == temp) {
                    parent.setLeft(temp.getLeft());
                } else {
                    parent.setRight(temp.getLeft());
                }
            } else {
                if (parent.getLeft() == temp) {
                    parent.setLeft(temp.getRight());
                } else {
                    parent.setRight(temp.getRight());
                }
            }
        }
    }
//    销毁二叉树整个树
    public void destroy(){
        root = null;
    }
    private void clear(){
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
        clear();
        Stack<Node> stack = new Stack<>();
        Node temp = root;
        while (temp != null || !stack.isEmpty()) {
            while (temp != null) {
                stack.push(temp);
                temp = temp.getLeft();
            }
            if (!stack.isEmpty()) {
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
        clear();
        Stack<Node> stack = new Stack<>();
        Node temp = root;
        while (temp != null || !stack.isEmpty()) {
            while (temp != null) {
                stack.push(temp);
                temp = temp.getRight();
            }
            if (!stack.isEmpty()) {
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
       try{
        File file = new File("StudentFile.txt");
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("StudentFile.txt", false));

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
    Binary_tree(File file){
       try{
           BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
           String line;
           while ((line = bufferedReader.readLine()) != null) {
               String[] strings = line.split(" ");
               Students students = new Students(strings[0].replaceAll("学号：",""),
                       strings[1].replaceAll("姓名：",""),
                       Integer.parseInt(strings[2].replaceAll("高数：","")),
                       Integer.parseInt(strings[3].replaceAll("英语：","")),
                       Integer.parseInt(strings[4].replaceAll("离散：","")));
               add(students);
           }
           bufferedReader.close();
       }catch (Exception e){

       }

    }
//    通过学号精确查找
    public Students search_id_the(String id){
        Node temp = root;
        while (true) {
            if (temp == null) {
                return null;
            }
            if (id.equals(temp.getStudents().getId())) {
                return temp.getStudents();
            } else if (id.compareTo(temp.getStudents().getId()) < 0) {
                temp = temp.getLeft();
            } else {
                temp = temp.getRight();
            }
        }
    }
//    通过学号模糊查找
    public LinkedList<Students> search_id_fuzzy(String id){
        LinkedList<Students> studentsLinkedList = new LinkedList<>();
        Stack<Node> stack = new Stack<>();
        Node temp = root;
        while (temp != null || !stack.isEmpty()) {
            while (temp != null) {
                stack.push(temp);
                temp = temp.getLeft();
            }
            if (!stack.isEmpty()) {
                temp = stack.pop();
                if (temp.getStudents().getId().contains(id)) {
                    studentsLinkedList.add(temp.getStudents());
                }
                temp = temp.getRight();
            }
        }
        return studentsLinkedList;
    }
//    通过姓名精确查找
    public LinkedList<Students> search_name_the(String name){
    LinkedList<Students> studentsLinkedList = new LinkedList<>();
    Stack<Node> stack = new Stack<>();
    Node temp = root;
    while (temp != null || !stack.isEmpty()) {
        while (temp != null) {
            stack.push(temp);
            temp = temp.getLeft();
        }
        if (!stack.isEmpty()) {
            temp = stack.pop();
            if (temp.getStudents().getName().equals(name)) {
                studentsLinkedList.add(temp.getStudents());
            }
            temp = temp.getRight();
        }
    }
    return studentsLinkedList;
}
//    通过姓名模糊查找
    public LinkedList<Students> search_name_fuzzy(String name){
        LinkedList<Students> studentsLinkedList = new LinkedList<>();
        Stack<Node> stack = new Stack<>();
        Node temp = root;
        while (temp != null || !stack.isEmpty()) {
            while (temp != null) {
                stack.push(temp);
                temp = temp.getLeft();
            }
            if (!stack.isEmpty()) {
                temp = stack.pop();
                if (temp.getStudents().getName().contains(name)) {
                    studentsLinkedList.add(temp.getStudents());
                }
                temp = temp.getRight();
            }
        }
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

}
