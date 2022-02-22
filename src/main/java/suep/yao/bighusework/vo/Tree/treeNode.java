package suep.yao.bighusework.vo.Tree;

import suep.yao.bighusework.entity.City;

public class treeNode {
    public Object data;
    public treeNode LChild;
    public treeNode RChild;

//    平衡因子
    public int balance;
//    高度
    public int height;


    public treeNode(Object data, treeNode LChild, treeNode RChild) {
        this.data = data;
        this.LChild = LChild;
        this.RChild = RChild;
    }
    public treeNode() {
    }
    public treeNode(Object data) {
        this.data=data;
        this.LChild=null;
        this.RChild=null;
        this.balance=0;
        this.height=1;
    }

    @Override
    public String toString() {
        City data = (City) this.data;
        return data.getName();
    }
}
