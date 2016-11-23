package rbtree;


/**
 * Created by linyo_000 on 2016/11/21.
 */
public class RBTree<T extends Comparable<T>> {
    private RBTNode<T> mRoot; // 根节点

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public class RBTNode<T extends Comparable<T>> {
        boolean color;
        T key;
        RBTNode<T> left;
        RBTNode<T> right;
        RBTNode<T> parent;

        public RBTNode(boolean color, T key, RBTNode<T> left, RBTNode<T> right, RBTNode<T> parent) {
            this.color = color;
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public T getKey() {
            return key;
        }

        @Override
        public String toString() {
            return "RBTNode{" +
                    "color=" + color +
                    ", key=" + key +
                    ", left=" + left.getKey() +
                    ", right=" + right.getKey() +
                    ", parent=" + parent.getKey() +
                    '}';
        }
    }

    public RBTree() {
        mRoot = null;
    }

    private RBTNode<T> parentOf(RBTNode<T> node) {
        return node!=null ? node.parent : null;
    }
    private boolean colorOf(RBTNode<T> node) {
        return node!=null ? node.color : BLACK;
    }
    private boolean isRed(RBTNode<T> node) {
        return ((node!=null)&&(node.color==RED)) ? true : false;
    }
    private boolean isBlack(RBTNode<T> node) {
        return !isRed(node);
    }
    private void setBlack(RBTNode<T> node) {
        if (node!=null)
            node.color = BLACK;
    }
    private void setRed(RBTNode<T> node) {
        if (node!=null)
            node.color = RED;
    }
    private void setParent(RBTNode<T> node, RBTNode<T> parent) {
        if (node!=null)
            node.parent = parent;
    }
    private void setColor(RBTNode<T> node, boolean color) {
        if (node!=null)
            node.color = color;
    }

    /**
     * 前序遍历
     */
    private void preOrder(RBTNode<T> tree) {
        if (tree != null) {
            System.out.print(tree.key + " ");
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }

    public void preOrder() {
        preOrder(mRoot);
    }

    /**
     * 中序遍历
     */
    private void midOrder(RBTNode<T> tree) {
        if (tree != null) {
            midOrder(tree.left);
            System.out.print(tree.key + " ");
            midOrder(tree.right);
        }
    }

    public void midOrder() {
        midOrder(mRoot);
    }

    /**
     * 后序遍历
     */
    private void postOrder(RBTNode<T> tree) {
        if (tree != null) {
            postOrder(tree.left);
            postOrder(tree.right);
            System.out.print(tree.key + " ");
        }
    }

    public void postOrder() {
        postOrder(mRoot);
    }

    /*
     * (递归实现)查找"红黑树x"中键值为key的节点
     */
    private RBTNode<T> search(RBTNode<T> x, T key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return search(x.left, key);
        } else if (cmp > 0) {
            return search(x.right, key);
        } else {
            return x;
        }
    }

    public RBTNode<T> search(T key) {
        return search(mRoot, key);
    }

    /**
     * (非递归实现)查找"红黑树x"中键值为key的节点
     */
    private RBTNode<T> iteraticeSearch(RBTNode<T> x, T key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                return x;
            }
        }
        return null;
    }

    public RBTNode<T> iteraticeSearch(T key) {
        return iteraticeSearch(mRoot, key);
    }

    /**
     * 查找最小结点：返回tree为根结点的红黑树的最小结点。
     */
    private RBTNode<T> minimum(RBTNode<T> tree) {
        if (tree == null) {
            return null;
        }
        while (tree.left != null) {
            tree = tree.left;
        }
        return tree;
    }

    public T minimum() {
        RBTNode<T> p = minimum(mRoot);
        if (p != null)
            return p.key;

        return null;
    }

    /*
     * 查找最大结点：返回tree为根结点的红黑树的最大结点。
     */
    private RBTNode<T> maximum(RBTNode<T> tree) {
        if (tree == null) {
            return null;
        }

        while (tree.right != null) {
            tree = tree.right;
        }

        return tree;
    }

    public T maximum() {
        RBTNode<T> p = maximum(mRoot);
        if (p != null)
            return p.key;

        return null;
    }



    /*
     * 找结点(x)的后继结点。即，查找"红黑树中数据值大于该结点"的"最小结点"。
     *
     *                            100(*)
     *                            /
     *                           10
     *                             \
     *                              70(y)
     *                                \
     *                                 90(x)
     *
     */
    public RBTNode<T> successor(RBTNode<T> x) {
        // 如果x存在右孩子，则"x的后继结点"为 "以其右孩子为根的子树的最小结点"。
        if (x.right != null) {
            return minimum(x.right);
        }

        // 如果x没有右孩子。则x有以下两种可能：
        // (01) x是"一个左孩子"，则"x的后继结点"为 "它的父结点"。
        // (02) x是"一个右孩子"，则查找"x的最低的父结点，并且该父结点要具有左孩子"，找到的这个"最低的父结点"就是"x的后继结点"。
        RBTNode<T> y = x.parent;
        while (y != null && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    /*
     * 找结点(x)的前驱结点。即，查找"红黑树中数据值小于该结点"的"最大结点"。
     *                             100(*)
     *                                \
     *                                150
     *                                 /
     *                               120
     *                               /
     *                             110(y)
     *                             /
     *                          109(x)
     *
     *
     */
    public RBTNode<T> predecessor(RBTNode<T> x) {
        // 如果x存在左孩子，则"x的前驱结点"为 "以其左孩子为根的子树的最大结点"。
        if (x.left != null) {
            return maximum(x.left);
        }

        // 如果x没有左孩子。则x有以下两种可能：
        // (01) x是"一个右孩子"，则"x的前驱结点"为 "它的父结点"。
        // (01) x是"一个左孩子"，则查找"x的最低的父结点，并且该父结点要具有右孩子"，找到的这个"最低的父结点"就是"x的前驱结点"。
        RBTNode<T> y = x.parent;
        while (y != null && x == y.left) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    /*
     * 对红黑树的节点(x)进行左旋转
     *
     * 左旋示意图(对节点x进行左旋)：
     *      px                              px
     *     /                               /
     *    x                               y
     *   /  \      --(左旋)->            / \
     *  lx   y                          x  ry
     *     /   \                       /\
     *    ly   ry                     lx ly
     *
     *
     */
     private void leftRotate(RBTNode<T> x) {
         // 设置x的右孩子为y
         RBTNode<T> y = x.right;

         // 将 “y的左孩子” 设为 “x的右孩子”；
         // 如果y的左孩子非空，将 “x” 设为 “y的左孩子的父亲”
         x.right = y.left;
         if (y.left != null) {
             y.left.parent = x;
         }

         // 将 “x的父亲” 设为 “y的父亲”
         y.parent = x.parent;

         if (x.parent == null) {        // 如果 “x的父亲” 是空节点，则将y设为根节点
             this.mRoot = y;
         } else {
             if (x.parent.left == x) {  // 如果x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
                x.parent.left = y;
             } else {                   // 如果x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
                x.parent.right = y;
             }
         }

         y.left = x;
         x.parent = y;
    }

    /*
     * 对红黑树的节点(y)进行右旋转
     *
     * 右旋示意图(对节点y进行左旋)：
     *            py                               py
     *           /                                /
     *          y                                x
     *         /  \      --(右旋)->             /  \
     *        x   ry                           lx   y
     *       / \                                   / \
     *      lx  rx                                rx  ry
     *
     */
    private void rightRotate(RBTNode<T> y) {
        // 设置x是当前节点的左孩子。
        RBTNode<T> x = y.left;

        // 将 “x的右孩子” 设为 “y的左孩子”；
        // 如果"x的右孩子"不为空的话，将 “y” 设为 “x的右孩子的父亲”
        y.left = x.right;
        if (x.right != null) {
            x.right.parent = y;
        }

        // 将 “y的父亲” 设为 “x的父亲”
        x.parent = y.parent;
        if (y.parent == null) {         // 如果 “y的父亲” 是空节点，则将x设为根节点
            this.mRoot = x;
        } else {
            if (y.parent.left == y) {  // (y是它父节点的左孩子) 将x设为“x的父节点的左孩子”
                y.parent.left = x;
            } else { // 如果 y是它父节点的右孩子，则将x设为“y的父节点的右孩子”
                y.parent.right = x;
            }
        }
        // 将 “y” 设为 “x的右孩子”
        x.right = y;
        // 将 “y的父节点” 设为 “x”
        y.parent = x;
    }

     /**
      * 红黑树插入修正函数
      *
      * 在向红黑树中插入节点之后(失去平衡)，再调用该函数；
      * 目的是将它重新塑造成一颗红黑树。
      *
      * 参数说明：
      *     node 插入的结点        // 对应《算法导论》中的z
      */
    private void insertFixup(RBTNode<T> node) {
        RBTNode<T> parent, gparent; // 父亲节点
        while ((parent =  parentOf(node)) != null && isRed(parent)) { // 当前节点的父亲节点为红色
            gparent = parentOf(parent); // 祖父节点

            if (parent == gparent.left) { // 父亲节点为祖父节点的左孩子
                RBTNode<T> uncle = gparent.right; // 蜀黍节点

                if (uncle != null && isRed(uncle)) { // 蜀黍节点为红色,则父亲和蜀黍都设置为的黑色,祖父设为红色并且设置为当前节点
                    // uncle is red
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    node = gparent;
                    continue;
                } else {
                    // uncle is black
                    if (parent.right == node) { // 蜀黍节点为黑色而且当前节点为父亲节点的右孩子,则以父亲为轴做一次左旋
                        node = parent;
                        leftRotate(node);
                        continue;
                    } else if (parent.left == node) { // 蜀黍节点为黑色而且当前节点为父亲节点的左孩子,则将父亲设置为黑色,将祖父设置为红色,并且以祖父为轴做一次右旋
                        setBlack(parent);
                        setRed(gparent);
                        rightRotate(gparent);
                    }
                }
            } else {  // 父亲节点为祖父的右孩子
                RBTNode<T> uncle = gparent.left;

                // 蜀黍节点为红色, 则父亲蜀黍都设置为黑色,祖父设置为红色且设置为当前节点
                if (uncle != null && isRed(uncle)) {
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    node = gparent;
                    continue;
                } else { // 蜀黍节点为黑色或为空
                    if (node == parent.left) {  // 当前节点是左孩子, 则右旋一次
                        node = parent;
                        rightRotate(node);
                    } else if (node == parent.right) { // 当前节点为右孩子, 则将父亲节点设置为黑色,祖父节点设置为红色,以祖父为轴做一次左旋
                        setBlack(parent);
                        setRed(gparent);
                        leftRotate(gparent);
                    }
                }
            }
        }

        //将根节点设置为黑色
        setBlack(this.mRoot);
    }

    /**
     * 将结点插入到红黑树中
     *
     * 参数说明： node 插入的结点        // 对应《算法导论》中的node
     */
    private void insert(RBTNode<T> node) {
        int cmp;
        RBTNode<T> y = null;
        RBTNode<T> x = this.mRoot;

        // 找到相应的插入的位置y
        while (x != null) {
            y = x;
            cmp = node.key.compareTo(x.key);
            if (cmp < 0) {
               x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;

        // 确定插在y的左孩子还是右孩子上
        if (y != null) {
            cmp = node.key.compareTo(y.key);
            if (cmp > 0) {
                y.right = node;
            } else {
                y.left = node;
            }
        } else {
            this.mRoot = node;
        }

        // 标记为红色
        node.color = RED;

        // 修正红黑树
        insertFixup(node);
    }

    public void insert(T key) {
        RBTNode<T> node = new RBTNode<T>(BLACK, key, null, null, null);

        if (node != null) {
            insert(node);
        }
    }

    /*
     * 删除结点(node)
     *
     * 参数说明：
     *     node 删除的结点
     */
    private void remove(RBTNode<T> node) {
        RBTNode<T> x,y;  // x指操作节点的孩子, y指操作节点
        if (node.left == null || node.right == null) { // 若左孩子或右孩子为空, y指向当前要删除节点
            y = node;
        } else {
            y = successor(node); // 左右孩子都为空则y取后继节点
        }

        if (y.left != null) {  // 若y左孩子不为空则x取左孩子
            x = y.left;
        } else { // x取右孩子
            x = y.right;
        }

        if (x != null) { // 若x不为空则x的父亲指向y的父亲
            x.parent = y.parent;
        }

        if (y.parent == null) { // y的父亲为空则说明x为新的根节点
            this.mRoot = x;
        } else {
            if (y == y.parent.left) {// 若y是左孩子,y的父亲的左孩子设为x
                y.parent.left = x;
            } else { // 若y是右孩子,y的父亲的右孩子设为x
                y.parent.right = x;
            }
        }

        if (y != node) {
             node.key = y.key;
        }

        if (isBlack(y)) {  // y为黑色的时候,就是说删了一个黑色的节点,则要修正红黑树
            deleteFixup(x, x.parent); // 修正的node是用x,即时一开始要删除的点y的孩子x(可能为空)
        }
    }

    public void remove(T key) {
        RBTNode<T> node;
        if ((node = search(mRoot, key)) != null) {
            remove(node);
        }
    }

    private void deleteFixup(RBTNode<T> node, RBTNode<T> parent) {
        RBTNode<T> brother;
        // 若 节点为空 或者 黑色 (空节点即为黑色) 而且不是根的话 循环
        while (((node == null || isBlack(node)) && (node != this.mRoot))) {
            if (parent.left == node) {  // 当前节点为左孩子
                brother = parent.right;  //兄弟节点
                if (isRed(brother)) {  // 兄弟节点为红色,则兄弟为黑,父亲为红,以父亲为轴左旋,重设置兄弟节点
                    setBlack(brother);
                    setRed(parent);
                    leftRotate(parent);
                    brother = parent.right;
                }
                // 前提 兄弟节点为黑色
                if ((brother.left == null || isBlack(brother.left)) &&
                        (brother.right == null || isBlack(brother.right))) { // 兄弟节点左右孩子都为空或者黑色,则兄弟节点设置为红色,当前节点指向父亲节点,并且更新parent
                    setRed(brother);
                    node = parent;
                    parent = parentOf(node);
                } else {
                    if (brother.right == null || isBlack(brother.right)) {  // 兄弟节点在右则此时先看右边,右边为空或者黑色,则兄弟节点的左孩子设置为黑色,
                                                                            // 兄弟节点设置为红色,以兄弟节点为轴右旋一次,重新设置兄弟节点
                        setBlack(brother.left);
                        setRed(brother);
                        rightRotate(brother);
                        brother = parent.right;
                    }
                    // 兄弟节点的右孩子为红色,左孩子颜色随意,
                    // 则将父亲节点的颜色赋给兄弟节点,父亲节点设置为黑色,兄第节点的右孩子设置为黑色,
                    // 以父亲节点为轴左旋,并且当前节点指向根节点!!! (下一次循环就退出了)
                    setColor(brother, colorOf(parent));
                    setBlack(parent);
                    setBlack(brother.right);
                    leftRotate(parent);
                    node = this.mRoot;
                    break;
                }
            } else {// 当前节点为右孩子
                brother = parent.left; //兄弟节点
                if (isRed(brother)) { // 兄弟节点为红色,则兄弟为黑,父亲为红,以父亲为轴右旋,重设置兄弟节点
                    setBlack(brother);
                    setRed(parent);
                    rightRotate(parent);
                    brother = parent.left;
                }

                if ((brother.left == null || isBlack(brother.left)) &&
                        (brother.right == null || isBlack(brother.right))) { // 兄弟节点左右孩子都为空或者黑色,则兄弟节点设置为红色,当前节点指向父亲节点,并且更新parent
                    setRed(brother);
                    node = parent;
                    parent = parentOf(node);
                } else {
                    if (brother.left == null || isBlack(brother.left)) { // 兄弟节点在左则此时先看左边,左边为空或者黑色,则兄弟节点的右孩子设置为黑色,
                                                                         // 兄弟节点设置为红色,以兄弟节点为轴左旋一次,重新设置兄弟节点
                        setBlack(brother.right);
                        setRed(brother);
                        leftRotate(brother);
                        brother = parent.left;
                    }
                    // 兄弟节点的左孩子为红色,右孩子颜色随意,
                    // 则将父亲节点的颜色赋给兄弟节点,父亲节点设置为黑色,兄第节点的左孩子设置为黑色,
                    // 以父亲节点为轴右旋,并且当前节点指向根节点!!! (下一次循环就退出了)
                    setColor(brother, colorOf(parent));
                    setBlack(parent);
                    setBlack(brother.left);
                    rightRotate(parent);
                    node = this.mRoot;
                    break;
                }
            }
        }
        // 如果当前节点不为空,则设置为黑色
        if (node != null) {
            setBlack(node);
        }
    }

    private void destory(RBTNode<T> tree) {
        if (tree == null) {
            return;
        }
        if (tree.left != null) {
            destory(tree.left);
        }

        if (tree.right != null) {
            destory(tree.right);
        }

        tree = null;
    }

    public void clear() {
        destory(mRoot);
        mRoot = null;
    }

    private void print(RBTNode<T> tree, T key, int direction) {
        if (tree != null) {
            if (direction == 0) {
                System.out.printf("%2d(B) is root\n", tree.key);
            } else {
                System.out.printf("%2d(%s) is %2d's %6s child\n",
                        tree.key,
                        isRed(tree) ? "R" : "B",
                        key,
                        direction == 1 ? "right" : "left");
            }
            print(tree.left, tree.key, -1);
            print(tree.right,tree.key, 1);
        }
    }

    public void print() {
        if (mRoot != null) {
            print(mRoot, mRoot.key, 0);
        }
    }
}

