package suep.yao.bighusework;

import org.junit.jupiter.api.Test;
import suep.yao.bighusework.entity.City;
import suep.yao.bighusework.vo.Tree.BinaryTree;
import suep.yao.bighusework.vo.Tree.treeNode;


public class test {
    public static void main(String[] args) throws Exception {
        City[] cities = new City[3];
        cities[0] = new City("上海");
        cities[1] = new City("北京");
        cities[2] = new City("深圳");


        BinaryTree binaryTree = new BinaryTree(cities);
        treeNode root = binaryTree.root;
        binaryTree.levelRoot();
        binaryTree.deleteByName("北京");
        System.out.println();
        binaryTree.levelRoot();

    }

    @Test
    public void so2() throws Exception {
        City[] cities = new City[10];
        cities[0] = new City("1");
        cities[1] = new City("2");
        cities[2] = new City("3");
        cities[3] = new City("4");
        cities[4] = new City("7");
        cities[5] = new City("6");
        cities[6] = new City("5");
        cities[7] = new City("8");
        cities[8] = new City("9");
        cities[9] = new City("10");
        BinaryTree binaryTree = new BinaryTree(cities);
        BinaryTree binaryTree1 = new BinaryTree(cities);
        treeNode root = binaryTree.root;
        treeNode root1= binaryTree1.root;
        binaryTree.levelRoot();
        binaryTree1.levelRoot();

    }
    @Test
    public void so() {
      /*  System.out.println("上海".compareTo("你好"));
        System.out.println("上海".compareTo("213"));
        System.out.println("上海".compareTo("阿斯顿"));
        System.out.println("上海".compareTo("北京"));
        System.out.println("上海".compareTo("北京萨达"));
        System.out.println("上海".compareTo("asd"));
        System.out.println("上海".compareTo("qwe"));
        System.out.println("上海".compareTo("玉林"));
        System.out.println("上海".compareTo("yul"));*/
        System.out.println("qwe".compareTo("玉林"));
        System.out.println("qwe".compareTo("123"));
        System.out.println("qwe".compareTo("yul"));
    }

}
