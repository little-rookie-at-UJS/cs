import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Students {
//    定义一个学生类 StudentFile.txt ，包含姓名 学号 高数 英语 离散 等属性，包含构造方法、get、set方法、toString方法
    private String id;
    private String name;
    private int math;
    private int english;
    private int discrete;
    private int total;

    public Students(String id, String name, int math, int english, int discrete) {
        this.id = id;
        this.name = name;
        this.math = math;
        this.english = english;
        this.discrete = discrete;
        this.total = math + english + discrete;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {

        this.name = name;
    }
    public int getMath() {
        return math;
    }
    public void setMath(int math) {
        total = total - this.math + math;
        this.math = math;
    }
    public int getEnglish() {
        return english;
    }
    public void setEnglish(int english) {
        total = total - this.english + english;
        this.english = english;
    }
    public int getDiscrete() {
        return discrete;
    }
    public void setDiscrete(int discrete) {
        total = total - this.discrete + discrete;
        this.discrete = discrete;
    }
    public int getTotal() {
        return total;
    }
    public String toString(){
      return "学号："+id+" 姓名："+name+" 高数："+math+" 英语："+english+" 离散："+discrete+" 总分："+total;
    }


}
