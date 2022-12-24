
public class Students {
//    定义一个学生类 StudentFile.txt ，包含姓名 学号 高数 英语 离散 等属性，包含构造方法、get、set方法、toString方法
    private String id;
    private String name;
    private int math;
    private int english;
    private int discrete;
    private int total;
//    构造函数 用于初始化
    public Students(String id, String name, int math, int english, int discrete) {
        this.id = id;
        this.name = name;
        this.math = math;
        this.english = english;
        this.discrete = discrete;
        this.total = math + english + discrete;
    }
//    get方法和set方法
//    get方法用于获取属性值

    public String getId() {
//        返回学号
        return id;
    }
    public String getName() {
//        返回姓名
        return name;
    }
    public int getMath() {
//        返回高数成绩
        return math;
    }

    public int getEnglish() {
//        返回英语成绩
        return english;
    }

    public int getDiscrete() {
//        返回离散成绩
        return discrete;
    }

    public int getTotal() {
//        返回总成绩
        return total;
    }

    public void setName(String name) {
//        设置姓名
        this.name = name;
    }
    public void setMath(int math) {
//        设置高数成绩并更新总成绩
        total = total - this.math + math;
        this.math = math;
    }
    public void setEnglish(int english) {
//        设置英语成绩并更新总成绩
        total = total - this.english + english;
        this.english = english;
    }
    public void setDiscrete(int discrete) {
//        设置离散成绩并更新总成绩
        total = total - this.discrete + discrete;
        this.discrete = discrete;
    }
    boolean contain_Id(String id) {
//        判断是否包含某个学号
        if (this.id.contains(id)) {
            return true;
        } else {
            return false;
        }
    }
    boolean contain_Name(String name) {
//        判断是否包含某个姓名
        if (this.name.contains(name)) {
            return true;
        } else {
            return false;
        }
    }
    public String toString(){
//        重写toString方法
      return "学号："+id+" 姓名："+name+" 高数："+math+" 英语："+english+" 离散："+discrete+" 总分："+total;
    }


}
