import java.util.LinkedList;

//b树的节点 通过b树 实现操作
public class B_node {
    int level; // 树的阶数
    int keyNum; // 关键字数量
    int childNum;
    Students[] key; // 关键字
    B_node[] child; // 子节点
    B_node parent; // 父节点
    boolean isLeaf; // 是否为叶子节点
    public B_node(int level) {
        this.level = level;
        keyNum = 0;
        childNum = 0;
        key = new Students[level];
        child = new B_node[level + 1];
        parent = null;
        isLeaf = true;
    }
    public B_node(int level, B_node parent) {
        this.level = level;
        keyNum = 0;
        childNum = 0;
        key = new Students[level];
        child = new B_node[level + 1];
        this.parent = parent;
        isLeaf = true;
    }
    public void insert(Students students) {
        if (isLeaf) {
            if (keyNum == 0) {
                key[0] = students;
                keyNum++;
            } else {
                int i = keyNum - 1;
                while (i >= 0 && key[i].cmp(students) > 0) {
                    key[i + 1] = key[i];
                    i--;
                }
                key[i + 1] = students;
                keyNum++;
            }
        } else {
            int i = keyNum - 1;
            while (i >= 0 && key[i].cmp(students) > 0) {
                i--;
            }
            if (child[i + 1].keyNum == level) {
                split(i + 1, child[i + 1]);
                if (key[i + 1].cmp(students) < 0) {
                    i++;
                }
            }
            child[i + 1].insert(students);
        }
    }
    public void split(int i, B_node bNode) {
        B_node newNode = new B_node(level, bNode.parent);
        newNode.isLeaf = bNode.isLeaf;
        newNode.keyNum = level / 2;
        for (int j = 0; j < level / 2; j++) {
            newNode.key[j] = bNode.key[j + level / 2 + 1];
        }
        if (!bNode.isLeaf) {
            newNode.childNum = level / 2 + 1;
            for (int j = 0; j < level / 2 + 1; j++) {
                newNode.child[j] = bNode.child[j + level / 2 + 1];
            }
        }
        bNode.keyNum = level / 2;
        bNode.childNum = level / 2 + 1;
        for (int j = keyNum; j > i; j--) {
            child[j + 1] = child[j];
        }
        child[i + 1] = newNode;
        for (int j = keyNum - 1; j >= i; j--) {
            key[j + 1] = key[j];
        }
        key[i] = bNode.key[level / 2];
        keyNum++;
    }

   void merge(int i){
        B_node left = child[i];
        B_node right = child[i+1];
        left.keyNum = level;
        left.key[level/2] = key[i];
        for(int j = 0;j<level/2;j++){
            left.key[j+level/2+1] = right.key[j];
        }
        if(!left.isLeaf){
            left.childNum = level+1;
            for(int j = 0;j<level/2+1;j++){
                left.child[j+level/2+1] = right.child[j];
            }
        }
        for(int j = i;j<keyNum-1;j++){
            key[j] = key[j+1];
        }
        for(int j = i+1;j<childNum-1;j++){
            child[j] = child[j+1];
        }
        keyNum--;
        childNum--;
    }
    void borrowFromLeft(int i){
        B_node left = child[i-1];
        B_node right = child[i];
        for(int j = right.keyNum;j>0;j--){
            right.key[j] = right.key[j-1];
        }
        right.key[0] = key[i-1];
        if(!right.isLeaf){
            for(int j = right.childNum;j>0;j--){
                right.child[j] = right.child[j-1];
            }
            right.child[0] = left.child[left.childNum-1];
        }
        key[i-1] = left.key[left.keyNum-1];
        left.keyNum--;
        right.keyNum++;
        if(!right.isLeaf){
            left.childNum--;
            right.childNum++;
        }
    }
    void borrowFromRight(int i){
        B_node left = child[i];
        B_node right = child[i+1];
        left.key[left.keyNum] = key[i];
        if(!left.isLeaf){
            left.child[left.childNum] = right.child[0];
            for(int j = 0;j<right.childNum-1;j++){
                right.child[j] = right.child[j+1];
            }
            left.childNum++;
            right.childNum--;
        }
        for(int j = 0;j<right.keyNum-1;j++){
            right.key[j] = right.key[j+1];
        }
        key[i] = right.key[0];
        left.keyNum++;
        right.keyNum--;
    }

    public void delete(Students students) {
        int i = 0;
        while (i < keyNum && key[i].cmp(students) < 0) {
            i++;
        }
        if (i < keyNum && key[i].cmp(students) == 0) {
            if (isLeaf) {
                for (int j = i; j < keyNum - 1; j++) {
                    key[j] = key[j + 1];
                }
                keyNum--;
            } else {
                if (child[i].keyNum >= level / 2 + 1) {
                    B_node bNode = child[i];
                    while (!bNode.isLeaf) {
                        bNode = bNode.child[bNode.childNum - 1];
                    }
                    key[i] = bNode.key[bNode.keyNum - 1];
                    child[i].delete(bNode.key[bNode.keyNum - 1]);
                } else if (child[i + 1].keyNum >= level / 2 + 1) {
                    B_node bNode = child[i + 1];
                    while (!bNode.isLeaf) {
                        bNode = bNode.child[0];
                    }
                    key[i] = bNode.key[0];
                    child[i + 1].delete(bNode.key[0]);
                } else {
                    merge(i);
                    child[i].delete(students);
                }
            }
        } else {
            if (isLeaf) {
                System.out.println("没有找到要删除的关键字");
            } else {
                if (child[i].keyNum == level / 2) {
                    if (i > 0 && child[i - 1].keyNum >= level / 2 + 1) {
                        borrowFromPrev(i);
                    } else if (i < keyNum && child[i + 1].keyNum >= level / 2 + 1) {
                        borrowFromNext(i);
                    } else {
                        if (i != keyNum) {
                            merge(i);
                        } else {
                            merge(i - 1);
                        }
                    }
                }
                child[i].delete(students);
            }
        }
    }

    private void borrowFromNext(int i) {
        B_node left = child[i];
        B_node right = child[i + 1];
        left.key[left.keyNum] = key[i];
        if (!left.isLeaf) {
            left.child[left.childNum] = right.child[0];
            for (int j = 0; j < right.childNum - 1; j++) {
                right.child[j] = right.child[j + 1];
            }
            left.childNum++;
            right.childNum--;
        }
        for (int j = 0; j < right.keyNum - 1; j++) {
            right.key[j] = right.key[j + 1];
        }
        key[i] = right.key[0];
        left.keyNum++;
        right.keyNum--;
    }

    private void borrowFromPrev(int i) {
        B_node left = child[i - 1];
        B_node right = child[i];
        for (int j = right.keyNum; j > 0; j--) {
            right.key[j] = right.key[j - 1];
        }
        right.key[0] = key[i - 1];
        if (!right.isLeaf) {
            for (int j = right.childNum; j > 0; j--) {
                right.child[j] = right.child[j - 1];
            }
            right.child[0] = left.child[left.childNum - 1];
        }
        key[i - 1] = left.key[left.keyNum - 1];
        left.keyNum--;
        right.keyNum++;
        if (!right.isLeaf) {
            left.childNum--;
            right.childNum++;
        }
    }

    public void search(Students students) {
        int i = 0;
        while (i < keyNum && key[i].cmp(students) < 0) {
            i++;
        }
        if (i < keyNum && key[i].cmp(students) == 0) {
            System.out.println("找到了");
        } else {
            if (isLeaf) {
                System.out.println("没有找到");
            } else {
                child[i].search(students);
            }
        }
    }

    public void print(LinkedList<Students> queue) {
        for (int i = 0; i < keyNum; i++) {
            if (!isLeaf) {
                child[i].print(queue);
            }
            queue.add(key[i]);
        }
        if (!isLeaf) {
            child[keyNum].print(queue);
        }
    }

//   查找某人是否存在
    public boolean occu(Students students) {
        int i = 0;
        while (i < keyNum && key[i].cmp(students) < 0) {
            i++;
        }
        if (i < keyNum && key[i].cmp(students) == 0) {
            return true;
        } else {
            if (isLeaf) {
                return false;
            } else {
                return child[i].occu(students);
            }
        }
    }

    public Students searchById(String id) {
        int i = 0;
        while (i < keyNum && key[i].getId().compareTo(id)< 0) {
            i++;
        }
        if (i < keyNum && key[i].getId() == id) {
            return key[i];
        } else {
            if (isLeaf) {
                return null;
            } else {
                return child[i].searchById(id);
            }
        }
    }
    public void change(Students p,Students T){
        int i = 0;
        while (i < keyNum && key[i].cmp(p) < 0) {
            i++;
        }
        if (i < keyNum && key[i].cmp(p) == 0) {
            key[i]=T;
        } else {
            if (isLeaf) {
                System.out.println("没有找到");
            } else {
                child[i].change(p,T);
            }
        }
    }
//倒序输出
    public void print_d(LinkedList<Students> queue) {
        for (int i = keyNum-1; i >=0; i--) {
            if (!isLeaf) {
                child[i].print_d(queue);
            }
            queue.add(key[i]);
        }
        if (!isLeaf) {
            child[keyNum].print_d(queue);
        }
    }
}
