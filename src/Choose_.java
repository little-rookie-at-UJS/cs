import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import java.io.*;
/*
 * Created by JFormDesigner on Sat Dec 17 12:51:26 CST 2022
 */
/**
 * @author unknown
 */
public class Choose_ extends JFrame {
    String student_id;
    private Binary_tree binary_tree=new Binary_tree(new File("StudentFile.txt"));

    public Choose_() {
        initComponents();
        add_Actionlistener();
    }

    private void add_Actionlistener(){

        show_id_up.addActionListener(e -> {
            binary_tree.initList_up();
            init_table(binary_tree.returnList());
        });
        flush_information.addActionListener(e -> {
            init_table(binary_tree.returnList());
        });
        show_id_down.addActionListener(e -> {
            binary_tree.initList_down();
            init_table(binary_tree.returnList());
        });
        select_id_the.addActionListener(e -> {
            students_part.setText("");
            String id=select_id.getText();
           Students students= binary_tree.search_id_the(id);
           if (students==null){
              students_part.setText("没有该学生");
           }else {
               students_part.setText(students.toString());
           }
        });
        select_id_quzzy.addActionListener(e -> {
            students_part.setText("");
            String id=select_id.getText();
            LinkedList<Students> students=binary_tree.search_id_fuzzy(id);
            if (students.size()==0||id.equals("")){
                students_part.setText("没有该学生");
            }else {
                for (Students student : students) {
                    students_part.append(student.toString()+"\n");
                }
            }
        });
        select_name_the.addActionListener(e -> {
            students_part.setText("");
            String name=select_name.getText();
            LinkedList<Students> students=binary_tree.search_name_the(name);
            if (students.size()==0){
                students_part.setText("没有该学生");
            }else {
               for (Students student : students) {
                   students_part.append(student.toString()+"\n");
               }
            }
        });
        select_name_quzzy.addActionListener(e -> {
            students_part.setText("");
            String name=select_name.getText();
            LinkedList<Students> students=binary_tree.search_name_fuzzy(name);
            if (students.size()==0||name.equals("")){
                students_part.setText("没有该学生");
            }else {
                for (Students student : students) {
                    students_part.append(student.toString()+"\n");
                }
            }
        });
        input_button.addActionListener(e -> {
            String id=input_id.getText();
            String name=input_name.getText();
//            判断数据是否合法
            if (id.equals("")||name.equals("")){
                wrong_information.setText("输入数据不合法");
                return;
            }
            if (binary_tree.search_id_the(id)!=null){
                wrong_information.setText("该学生已存在");
                return;
            }
            if(!input_math.getText().matches("[0-9]+")){
                wrong_information.setText("成绩必须为数字");
                return;
            }
            if(!input_english.getText().matches("[0-9]+")){
                wrong_information.setText("成绩必须为数字");
                return;
            }
            if(!input_discrete.getText().matches("[0-9]+")){
                wrong_information.setText("成绩必须为数字");
                return;
            }
            int math=Integer.parseInt(input_math.getText());
            int english=Integer.parseInt(input_english.getText());
            int discrete=Integer.parseInt(input_discrete.getText());
            Students students=new Students(id,name,math,english,discrete);
            if(binary_tree.search_id_the(id)==null) {
                binary_tree.add(students);
                wrong_information.setText("添加成功");
            }
            else {
                wrong_information.setText("该学生已存在");
            }
            binary_tree.write_up();
        });
        search_imformation.addActionListener(e -> {
            JFileChooser jFileChooser=new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jFileChooser.showDialog(new JLabel(),"选择");
            File file=jFileChooser.getSelectedFile();
            if (file==null){
             wrong_information.setText("未选择文件");
            }else if(!file.getName().matches(".*\\.txt")){
               wrong_information.setText("文件格式不正确");
             }else {
                try {
                    binary_tree.add_file(file);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                wrong_information.setText("导入成功\n");
                LinkedList<String> students=binary_tree.getWrong_list();
                if (students.size()!=0){
                    wrong_information.append("以下学生信息有误\n");
                    for (String student : students) {
                        wrong_information.append(student+"\n");
                    }
                }
            }
            binary_tree.write_up();
        });
        change_flush.addActionListener(e -> {
//            获取树的所有id
            LinkedList<Students>List=binary_tree.returnList();
            LinkedList<String> id_list=new LinkedList<>();
            for (Students students : List) {
                id_list.add(students.getId());

            }
            String[] id=new String[id_list.size()];
            for (int i = 0; i < id_list.size(); i++) {
                id[i]=id_list.get(i);
            }
            change_list.setListData(id);

            change_list.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e){
                    int index=change_list.getSelectedIndex();
                    Students students=List.get(index);
                    student_id=students.getId();
//                    change_id.setText(students.getId());
                    change_name.setText(students.getName());
                    change_math.setText(String.valueOf(students.getMath()));
                    change_english.setText(String.valueOf(students.getEnglish()));
                    change_discrete.setText(String.valueOf(students.getDiscrete()));
                }
            });
        });
        delete_button.addActionListener(e -> {
            if(student_id==null){
                change_susses.setText("未选择学生");
            }else if(binary_tree.search_id_the(student_id)==null){
                change_susses.setText("该学生不存在");
            }else {
                binary_tree.delete(student_id);
                change_susses.setText("删除成功");
            }
            binary_tree.write_up();
        });
        change_button.addActionListener(e->{
            if(student_id==null){
                change_susses.setText("未选择学生");
            }else if(binary_tree.search_id_the(student_id)==null){
                change_susses.setText("该学生不存在");
            }else{
                Students students=binary_tree.search_id_the(student_id);
                String name=change_name.getText();
                String math=change_math.getText();
                String english=change_english.getText();
                String discrete=change_discrete.getText();
                if (name.equals("")||math.equals("")||english.equals("")||discrete.equals("")){
                    change_susses.setText("输入数据不合法");
                    return;
                }
                if(!math.matches("[0-9]+")){
                    change_susses.setText("成绩必须为数字");
                    return;
                }
                if(!english.matches("[0-9]+")){
                    change_susses.setText("成绩必须为数字");
                    return;
                }
                if(!discrete.matches("[0-9]+")){
                    change_susses.setText("成绩必须为数字");
                    return;
                }
                students.setName(name);
                students.setMath(Integer.parseInt(math));
                students.setEnglish(Integer.parseInt(english));
                students.setDiscrete(Integer.parseInt(discrete));
                change_susses.setText("修改成功");
            }
            binary_tree.write_up();
        });
    }
    void init_table(LinkedList<Students>list){
        String[] columnNames = {"学号", "姓名", "高数", "英语", "离散", "总分"};
        Object[][] data = new Object[list.size()+2][6];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getId();
            data[i][1] = list.get(i).getName();
            data[i][2] = list.get(i).getMath();
            data[i][3] = list.get(i).getEnglish();
            data[i][4] = list.get(i).getDiscrete();
            data[i][5] = list.get(i).getTotal();
        }
        data[list.size()][0] = "平均分";
        data[list.size()][1] = "";
        data[list.size()][2] = binary_tree.getAverage_math();
        data[list.size()][3] = binary_tree.getAverage_english();
        data[list.size()][4] = binary_tree.getAverage_discrete();
        data[list.size()][5] = binary_tree.getAverage_all();
        data[list.size()+1][0] = "最高分";
        data[list.size()+1][2] = binary_tree.getMax_math();
        data[list.size()+1][3] = binary_tree.getMax_english();
        data[list.size()+1][4] = binary_tree.getMax_discrete();
        data[list.size()+1][5] = binary_tree.getMax_all();

        show_all = new JTable(data, columnNames);
        scrollPane1.setViewportView(show_all);
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        tabbedPane1 = new JTabbedPane();
        show = new JPanel();
        show_id_up = new JButton();
        show_id_down = new JButton();
        flush_information = new JButton();
        scrollPane1 = new JScrollPane();
        show_all = new JTable();
        select = new JPanel();
        label6 = new JLabel();
        label7 = new JLabel();
        select_id = new JTextField();
        select_id_the = new JButton();
        select_id_quzzy = new JButton();
        select_name_the = new JButton();
        select_name_quzzy = new JButton();
        scrollPane4 = new JScrollPane();
        students_part = new JTextArea();
        select_name = new JTextField();
        input = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        input_id = new JTextField();
        input_name = new JTextField();
        input_math = new JTextField();
        input_english = new JTextField();
        input_discrete = new JTextField();
        input_button = new JButton();
        search_imformation = new JButton();
        scrollPane2 = new JScrollPane();
        wrong_information = new JTextArea();
        change = new JPanel();
        scrollPane3 = new JScrollPane();
        change_list = new JList();
        delete_button = new JButton();
        change_button = new JButton();
        label10 = new JLabel();
        change_name = new JTextField();
        label11 = new JLabel();
        change_math = new JTextField();
        label12 = new JLabel();
        change_english = new JTextField();
        label13 = new JLabel();
        change_discrete = new JTextField();
        change_flush = new JButton();
        scrollPane5 = new JScrollPane();
        change_susses = new JTextArea();

        //======== this ========
        setMinimumSize(new Dimension(600, 480));
        setTitle("\u5b66\u751f\u7ba1\u7406\u7cfb\u7edf");
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== tabbedPane1 ========
        {

            //======== show ========
            {
                show.setLayout(null);

                //---- show_id_up ----
                show_id_up.setText("\u6309\u7167\u5b66\u53f7\u5347\u5e8f\u6392\u5217");
                show.add(show_id_up);
                show_id_up.setBounds(new Rectangle(new Point(50, 25), show_id_up.getPreferredSize()));

                //---- show_id_down ----
                show_id_down.setText("\u6309\u7167\u5b66\u53f7\u964d\u5e8f\u6392\u5217");
                show.add(show_id_down);
                show_id_down.setBounds(new Rectangle(new Point(380, 25), show_id_down.getPreferredSize()));

                //---- flush_information ----
                flush_information.setText("\u5237\u65b0");
                show.add(flush_information);
                flush_information.setBounds(new Rectangle(new Point(240, 25), flush_information.getPreferredSize()));

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(show_all);
                }
                show.add(scrollPane1);
                scrollPane1.setBounds(40, 100, 465, 285);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < show.getComponentCount(); i++) {
                        Rectangle bounds = show.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = show.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    show.setMinimumSize(preferredSize);
                    show.setPreferredSize(preferredSize);
                }
            }
            tabbedPane1.addTab("\u663e\u793a\u5b66\u751f\u4fe1\u606f", show);

            //======== select ========
            {
                select.setLayout(null);

                //---- label6 ----
                label6.setText("id");
                select.add(label6);
                label6.setBounds(40, 20, 45, 25);

                //---- label7 ----
                label7.setText("\u59d3\u540d");
                select.add(label7);
                label7.setBounds(new Rectangle(new Point(275, 25), label7.getPreferredSize()));
                select.add(select_id);
                select_id.setBounds(105, 20, 65, select_id.getPreferredSize().height);

                //---- select_id_the ----
                select_id_the.setText("\u901a\u8fc7\u5b66\u53f7\u7cbe\u786e\u67e5\u627e");
                select.add(select_id_the);
                select_id_the.setBounds(new Rectangle(new Point(170, 80), select_id_the.getPreferredSize()));

                //---- select_id_quzzy ----
                select_id_quzzy.setText("\u901a\u8fc7\u5b66\u53f7\u6a21\u7cca\u67e5\u627e");
                select.add(select_id_quzzy);
                select_id_quzzy.setBounds(20, 80, 135, select_id_quzzy.getPreferredSize().height);

                //---- select_name_the ----
                select_name_the.setText("\u901a\u8fc7\u59d3\u540d\u7cbe\u786e\u67e5\u627e");
                select.add(select_name_the);
                select_name_the.setBounds(new Rectangle(new Point(310, 80), select_name_the.getPreferredSize()));

                //---- select_name_quzzy ----
                select_name_quzzy.setText("\u901a\u8fc7\u59d3\u540d\u6a21\u7cca\u67e5\u627e");
                select.add(select_name_quzzy);
                select_name_quzzy.setBounds(new Rectangle(new Point(450, 80), select_name_quzzy.getPreferredSize()));

                //======== scrollPane4 ========
                {
                    scrollPane4.setViewportView(students_part);
                }
                select.add(scrollPane4);
                scrollPane4.setBounds(35, 140, 530, 220);
                select.add(select_name);
                select_name.setBounds(365, 20, 80, select_name.getPreferredSize().height);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < select.getComponentCount(); i++) {
                        Rectangle bounds = select.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = select.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    select.setMinimumSize(preferredSize);
                    select.setPreferredSize(preferredSize);
                }
            }
            tabbedPane1.addTab("\u67e5\u627e\u5b66\u751f", select);

            //======== input ========
            {
                input.setLayout(null);

                //---- label1 ----
                label1.setText("id");
                input.add(label1);
                label1.setBounds(45, 30, 55, 30);

                //---- label2 ----
                label2.setText("\u59d3\u540d");
                input.add(label2);
                label2.setBounds(45, 105, 40, 25);

                //---- label3 ----
                label3.setText("\u9ad8\u6570");
                input.add(label3);
                label3.setBounds(45, 180, 35, 20);

                //---- label4 ----
                label4.setText("\u82f1\u8bed");
                input.add(label4);
                label4.setBounds(45, 260, 40, 30);

                //---- label5 ----
                label5.setText("\u79bb\u6563");
                input.add(label5);
                label5.setBounds(40, 340, 33, 27);
                input.add(input_id);
                input_id.setBounds(125, 35, 115, input_id.getPreferredSize().height);
                input.add(input_name);
                input_name.setBounds(125, 110, 115, input_name.getPreferredSize().height);
                input.add(input_math);
                input_math.setBounds(125, 175, 115, input_math.getPreferredSize().height);
                input.add(input_english);
                input_english.setBounds(125, 265, 115, input_english.getPreferredSize().height);
                input.add(input_discrete);
                input_discrete.setBounds(125, 345, 110, input_discrete.getPreferredSize().height);

                //---- input_button ----
                input_button.setText("\u63d0\u4ea4\u5f55\u5165\u4fe1\u606f");
                input.add(input_button);
                input_button.setBounds(new Rectangle(new Point(350, 40), input_button.getPreferredSize()));

                //---- search_imformation ----
                search_imformation.setText("\u9009\u62e9\u6587\u4ef6\u63d0\u4ea4(.txt)");
                input.add(search_imformation);
                search_imformation.setBounds(new Rectangle(new Point(340, 170), search_imformation.getPreferredSize()));

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(wrong_information);
                }
                input.add(scrollPane2);
                scrollPane2.setBounds(280, 230, 270, 135);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < input.getComponentCount(); i++) {
                        Rectangle bounds = input.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = input.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    input.setMinimumSize(preferredSize);
                    input.setPreferredSize(preferredSize);
                }
            }
            tabbedPane1.addTab("\u5f55\u5165\u5b66\u751f\u4fe1\u606f", input);

            //======== change ========
            {
                change.setLayout(null);

                //======== scrollPane3 ========
                {
                    scrollPane3.setViewportView(change_list);
                }
                change.add(scrollPane3);
                scrollPane3.setBounds(40, 125, 85, scrollPane3.getPreferredSize().height);

                //---- delete_button ----
                delete_button.setText("\u5220\u9664");
                change.add(delete_button);
                delete_button.setBounds(new Rectangle(new Point(455, 55), delete_button.getPreferredSize()));

                //---- change_button ----
                change_button.setText("\u4fee\u6539");
                change.add(change_button);
                change_button.setBounds(new Rectangle(new Point(455, 140), change_button.getPreferredSize()));

                //---- label10 ----
                label10.setText("\u59d3\u540d");
                change.add(label10);
                label10.setBounds(new Rectangle(new Point(200, 65), label10.getPreferredSize()));
                change.add(change_name);
                change_name.setBounds(270, 60, 90, change_name.getPreferredSize().height);

                //---- label11 ----
                label11.setText("\u9ad8\u6570");
                change.add(label11);
                label11.setBounds(200, 125, 35, 22);
                change.add(change_math);
                change_math.setBounds(270, 120, 95, change_math.getPreferredSize().height);

                //---- label12 ----
                label12.setText("\u82f1\u8bed");
                change.add(label12);
                label12.setBounds(195, 205, 25, label12.getPreferredSize().height);
                change.add(change_english);
                change_english.setBounds(270, 195, 95, change_english.getPreferredSize().height);

                //---- label13 ----
                label13.setText("\u79bb\u6563");
                change.add(label13);
                label13.setBounds(new Rectangle(new Point(195, 275), label13.getPreferredSize()));
                change.add(change_discrete);
                change_discrete.setBounds(270, 265, 90, change_discrete.getPreferredSize().height);

                //---- change_flush ----
                change_flush.setText("\u5237\u65b0");
                change.add(change_flush);
                change_flush.setBounds(new Rectangle(new Point(45, 35), change_flush.getPreferredSize()));

                //======== scrollPane5 ========
                {
                    scrollPane5.setViewportView(change_susses);
                }
                change.add(scrollPane5);
                scrollPane5.setBounds(440, 210, 115, 150);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < change.getComponentCount(); i++) {
                        Rectangle bounds = change.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = change.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    change.setMinimumSize(preferredSize);
                    change.setPreferredSize(preferredSize);
                }
            }
            tabbedPane1.addTab("\u4fee\u6539\u5b66\u751f\u4fe1\u606f", change);
        }
        contentPane.add(tabbedPane1);
        tabbedPane1.setBounds(0, 0, 590, 450);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JTabbedPane tabbedPane1;
    private JPanel show;
    private JButton show_id_up;
    private JButton show_id_down;
    private JButton flush_information;
    private JScrollPane scrollPane1;
    private JTable show_all;
    private JPanel select;
    private JLabel label6;
    private JLabel label7;
    private JTextField select_id;
    private JButton select_id_the;
    private JButton select_id_quzzy;
    private JButton select_name_the;
    private JButton select_name_quzzy;
    private JScrollPane scrollPane4;
    private JTextArea students_part;
    private JTextField select_name;
    private JPanel input;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JTextField input_id;
    private JTextField input_name;
    private JTextField input_math;
    private JTextField input_english;
    private JTextField input_discrete;
    private JButton input_button;
    private JButton search_imformation;
    private JScrollPane scrollPane2;
    private JTextArea wrong_information;
    private JPanel change;
    private JScrollPane scrollPane3;
    private JList change_list;
    private JButton delete_button;
    private JButton change_button;
    private JLabel label10;
    private JTextField change_name;
    private JLabel label11;
    private JTextField change_math;
    private JLabel label12;
    private JTextField change_english;
    private JLabel label13;
    private JTextField change_discrete;
    private JButton change_flush;
    private JScrollPane scrollPane5;
    private JTextArea change_susses;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
