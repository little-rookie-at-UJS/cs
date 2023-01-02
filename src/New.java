import java.util.LinkedList;

public class New {
    public static void main(String[] args) {
        Students students[] = {
               new  Students("1","zaj",1,1,1),
                new  Students("2","zaj",1,1,1),
                new  Students("3","zaj",1,1,1),
                new  Students("4","zaj",1,1,1),
        };
        B_T b_tree = new B_T(5);
        for (int i = 0; i < students.length; i++) {
            b_tree.insert(students[i]);
        }
      LinkedList<Students>S= b_tree.print();
        for (int i = 0; i < S.size(); i++) {
            System.out.println(S.get(i).toString());
        }
        b_tree.delete(students[0]);
        S= b_tree.print();
//        for (int i = 0; i < S.size(); i++) {
//            System.out.println(S.get(i).toString());
//        }

    }
}
