package rbtree;

import sun.misc.IOUtils;

/**
 * Created by linyo_000 on 2016/11/21.
 */
public class TestRBTree {
    public static void main(String[] strings) {
        RBTree<Integer> tree = new RBTree<Integer>();

        Integer[] a = new Integer[]{543,206,85,850,879,471,807,590,350};

        System.out.print("-----a:");
        for (int i = 0; i < a.length; i++) {
            System.out.printf("%d ", a[i].intValue());
        }
        System.out.print("\n");

        for (int i = 0; i < a.length; i++) {
            tree.insert(a[i]);

            System.out.printf(" == addedNode: %d\n", a[i]);
            System.out.print(" == tree info :\n ");
            tree.print();
            System.out.print("\n");
        }

        System.out.printf("== ǰ�����: ");
        tree.preOrder();

        System.out.printf("\n== �������: ");
        tree.midOrder();

        System.out.printf("\n== �������: ");
        tree.postOrder();
        System.out.printf("\n");

        System.out.printf("== ��Сֵ: %s\n", tree.minimum());
        System.out.printf("== ���ֵ: %s\n", tree.maximum());
        System.out.printf("== ������ϸ��Ϣ: \n");
        tree.print();
        System.out.printf("\n");

        Integer[] b = new Integer[]{206,471,590,350,850,879};

        for (int i = 0; i < b.length; i++) {
            tree.remove(b[i]);

            System.out.printf("== ɾ���ڵ�: %d\n", b[i]);
            System.out.printf("== ������ϸ��Ϣ: \n");
            System.out.printf("\n");
        }


        tree.clear();
    }
}
