import java.io.*;
import java.util.LinkedList;

//B树实现
public class B_T {
    LinkedList<Students> queue = new LinkedList<>();
    LinkedList<String>wrong_list=new LinkedList<>();

    private int level;
    private String path;
    private B_node root;
    public B_T(int level) {
        this.level = level;
        root = new B_node(level);
    }
    public B_T(int level,String path) {
        this.level = level;
        root = new B_node(level);
        this.path=path;
        readfile(new File(path));
    }
    public void insert(Students students) {
        if(occu(students)) {
            return;
        }
        if (root.keyNum == level) {
            B_node newRoot = new B_node(level, null);
            newRoot.isLeaf = false;
            newRoot.childNum = 2;
            newRoot.child[0] = root;
            newRoot.split(0, root);
            int i = 0;
            if (newRoot.key[0].cmp(students) < 0) {
                i++;
            }
            newRoot.child[i].insert(students);
            root = newRoot;
        } else {
            root.insert(students);
        }
    }
    public void delete(Students students) {
        root.delete(students);
    }
    public void search(Students students) {
        root.search(students);
    }
    boolean occu(Students students) {
        return root.occu(students);
    }
    public LinkedList<Students> print() {
        queue.clear();
        root.print(queue);
        return queue;
    }
    public LinkedList<Students> print_d() {
//        倒序输出
        queue.clear();
        root.print_d(queue);
        return queue;

    }
//    通过id 查找学生
    public Students searchById(String id) {
        return root.searchById(id);
    }
    //    通过 id 模糊查找学生
    public LinkedList<Students> searchByIdLike(String id) {
        LinkedList<Students> queue = new LinkedList<>();
        for (Students students : print()) {
            if (students.getId().contains(id)) {
                queue.add(students);
            }
        }
        return queue;
    }
    //    通过 name 模糊查找学生
    public LinkedList<Students> searchByNameLike(String name) {
        LinkedList<Students> queue = new LinkedList<>();
        for (Students students : print()) {
            if (students.getName().contains(name)) {
                queue.add(students);
            }
        }
        return queue;
    }
    //    通过 name 查找学生
    public LinkedList<Students> searchByName(String name) {
        LinkedList<Students> queue = new LinkedList<>();
        for (Students students : print()) {
            if (students.getName().equals(name)) {
                queue.add(students);
            }
        }
        return queue;
    }
    public void  readfile(File file) {
        LinkedList<Students> queue = new LinkedList<>();
        try {
            BufferedReader bf=new BufferedReader(new FileReader(file));
            String str;
            while((str=bf.readLine())!=null){
                Students students = translate(str);
                if(students!=null) {
                    if(occu(students)) {
                        wrong_list.add(str);
                    }else {
                    queue.add(students);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        for (Students students : queue) {
            insert(students);
        }
    }
    public void write_up(){
        File f=new File(path);
        print();
        try {
            BufferedWriter bw=new BufferedWriter(new FileWriter(f));
            for (Students students : print()) {
                bw.write(students.toString());
                bw.newLine();
            }
            bw.close();
        }catch (Exception e) {
        }

    }
    Students translate(String str) {
//       通过“ ”分割字符串
        String[] strings = str.split(" ");
//       如果各部分满足要求，并且学号不重复，则创建对象
        if(!strings[0].matches("学号：.*")||
                !strings[1].matches("姓名：.*")||
                !strings[2].matches("高数：.*")||
                !strings[3].matches("英语：.*")||
                !strings[4].matches("离散：.*")||searchById(strings[0].replaceAll("学号：",""))!=null) {
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

    public LinkedList<String> getWrong_list() {
        return wrong_list;
    }
}
