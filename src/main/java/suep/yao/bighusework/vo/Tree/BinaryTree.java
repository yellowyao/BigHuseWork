
package suep.yao.bighusework.vo.Tree;

import org.springframework.stereotype.Component;
import suep.yao.bighusework.entity.City;
import suep.yao.bighusework.vo.LinkQue.LinkQueue;

import java.util.List;

@Component
public class BinaryTree {
    public treeNode root;

    public BinaryTree(treeNode root) {
        this.root = root;
    }

    public BinaryTree() {
    }


    //平衡树的实现方法
    public BinaryTree(Object[] Citys) {
        root = new treeNode(Citys[0]);
        for (int i = 1; i < Citys.length; i++) {
            root = balanceTree(Citys[i], root);
        }

    }

    public void balanceInsert(Object data) {
        root = balanceTree(data, root);
    }

    //    ==================================================================================================================
    //改进后
    //新的平衡二叉树插入方法
    private treeNode balanceTree(Object data, treeNode root) {
        if (root != null) {
            //比较插入
            City CityA = (City) root.data;
            City CityB = (City) data;
//            插入值小，往左插入
            if (CityA.compareTo(CityB.getName()) > 0) {
//                左插入，返回父节点的左空节点
                treeNode treeNode = balanceTree(data, root.LChild);
                if (treeNode == null) {
                    root.LChild = new treeNode(data);
//                    判断新加入的节点是否会影响高度，因为他只有他自己跟一个孩子，所更新高度为2
                    if (root.RChild == null) {
                        root.height = 2;
                    }
                } else {
//                    此处与插入无关，这里是平衡纠正
//                    更新节点的孩子，目的是:若下一个节点进行了平衡纠正，则要更新纠正好以后的节点的父节点，相当于更新父节点的孩子，这里的节点为下一个节点的父节点
//                    正常来说，treeNode就是原来root的左孩子，但如果它的左孩子进行平衡纠正后，他的左孩子可能会变，所以可以更新左孩子
                    root.LChild = treeNode;
                }
            } else {
//            插入值大，往右插入，此下代码与上类似
                treeNode treeNode = balanceTree(data, root.RChild);
                if (treeNode == null) {
                    root.RChild = new treeNode(data);
                    if (root.LChild == null) {
                        root.height = 2;
                    }
                } else {
                    root.RChild = treeNode;
                }
            }
//            此下代码与插入无关，为平衡纠正
            //插入新节点后更新节点高度
            updateHeight(root);
            //判断是否平衡,若不平衡则旋转纠正
            if (root.balance != 1 && root.balance != -1 && root.balance != 0) {
//                    LL
                if (root.balance == 2 && root.LChild.balance == 1) {
//                    旋转后节点改变
                    root = LL(root);
//                    LR
                } else if (root.balance == 2 && root.LChild.balance == -1) {
                    root = LR(root);
//                    RR
                } else if (root.balance == -2 && root.RChild.balance == -1) {
                    root = RR(root);
//                    RL
                } else if (root.balance == -2 && root.RChild.balance == 1) {
                    root = RL(root);
                }
//                平衡后更新此节点及此节点以下的节点的平衡因子，此处为递归调用，与上面的updateHeight有区别，
                updateBalance(root);
            }
//            返回节点，以便更新父节点的孩子
            return root;
        } else {
            return null;
        }
    }

    //  LL类型旋转
    private treeNode LL(treeNode root) {
        treeNode temp;
        treeNode newroot = root.LChild;
        temp = newroot.RChild;
        newroot.RChild = root;
        root.LChild = temp;
        return newroot;
    }

    //LR类型旋转 返回为旋转后的根节点 注意:返回的新的根节点的父节点未更新，需要在递归返回到上一个节点后更新该节点的父节点
//    以下各个方法类似
    private treeNode LR(treeNode root) {
        treeNode tempR;
        treeNode tempL;
        treeNode newroot = root.LChild.RChild;
        tempR = newroot.RChild;
        tempL = newroot.LChild;
        newroot.RChild = root;
        newroot.LChild = root.LChild;
        root.LChild.RChild = tempL;
        root.LChild = tempR;
        return newroot;
    }

    //RR类型旋转
    private treeNode RR(treeNode root) {
        treeNode temp;
        treeNode newroot = root.RChild;
        temp = newroot.LChild;
        newroot.LChild = root;
        root.RChild = temp;
        return newroot;
    }

    //RL类型旋转
    private treeNode RL(treeNode root) {
        treeNode tempR;
        treeNode tempL;
        treeNode newroot = root.RChild.LChild;
        tempR = newroot.RChild;
        tempL = newroot.LChild;
        newroot.RChild = root.RChild;
        newroot.LChild = root;
        root.RChild.LChild = tempR;
        root.RChild = tempL;
        return newroot;
    }

    //更新高度  该方法为插入节点后更新节点的高度，同时计算，被插入的新节点所影响的节点的平衡因子
    private void updateHeight(treeNode root) {

        if (root.RChild != null && root.LChild != null) {
            root.height = 1 + (root.LChild.height > root.RChild.height ? root.LChild.height : root.RChild.height);
            root.balance = root.LChild.height - root.RChild.height;
        } else if (root.RChild == null && root.LChild != null) {
            root.height = root.LChild.height + 1;
            root.balance = root.LChild.height;
        } else if (root.RChild != null && root.LChild == null) {
            root.height = root.RChild.height + 1;
            root.balance = -root.RChild.height;
        }
    }

    //更新平衡因子  该方法为在旋转后，更新新的根节点及其根节点以下子节点的高度和平衡因子
    private int updateBalance(treeNode root) {
        if (root != null) {
            int Ldeep = updateBalance(root.LChild);
            int Rdeep = updateBalance(root.RChild);
            root.height = 1 + (Ldeep > Rdeep ? Ldeep : Rdeep);
            root.balance = Ldeep - Rdeep;
            return 1 + (Ldeep > Rdeep ? Ldeep : Rdeep);
        } else {
            return 0;
        }
    }

//    ==================================================================================================================

    public void levelRoot() throws Exception {
        LinkQueue linkQueue = new LinkQueue();
        linkQueue.insert(root);
        int len = 1;
        while (!linkQueue.isEmpty()) {
            while (len > 0) {
                treeNode newRoot = (treeNode) linkQueue.delete();
                System.out.print(newRoot + "  height:" + newRoot.height + "   balance:" + newRoot.balance + " |");
                if (newRoot.LChild != null) {
                    linkQueue.insert(newRoot.LChild);
                }
                if (newRoot.RChild != null) {
                    linkQueue.insert(newRoot.RChild);
                }
                len--;
            }
            System.out.println();
            len = linkQueue.length();

        }
    }

    //判断该节点是否有孩子,没有孩子返回 0,只有左孩子返回-1，只有右孩子返回1，两孩子返回2
    private int isLeaf(treeNode node) {
        if (node.LChild == null && node.RChild == null) {
            return 0;
        } else if (node.LChild == null) {
            return 1;
        } else if (node.RChild == null) {
            return -1;
        } else {
            return 2;
        }
    }

    public void inRoot() {
        inRoot(root);
    }

    private void inRoot(treeNode inRoot) {
        if (inRoot != null) {
            inRoot(inRoot.LChild);
            System.out.println(inRoot.data);
            inRoot(inRoot.RChild);
        }
    }


    public City findByName(String cityName) {
        return findByName(cityName, root) == null ? null : (City) findByName(cityName, root).data;
    }

    private treeNode findByName(String cityName, treeNode root) {
        if (root != null) {
            City City = (City) root.data;
            if (City.compareTo(cityName) == 0) {
                return root;
            } else if (City.compareTo(cityName) > 0) {
                return findByName(cityName, root.LChild);
            } else {
                return findByName(cityName, root.RChild);
            }
        } else {
            System.out.println("要找的值不存在");
            return null;
        }
    }


    public void deleteByName(String cityName) {
        City City = (City) root.data;
        if (City.getName().compareTo(cityName) == 0) {
            root = deleteTheRoot(root);
        } else {
            deleteByName(cityName, root);
        }
    }

    //删除节点
    private treeNode deleteByName(String cityName, treeNode root) {

        if (root != null) {
            City City = (City) root.data;

            if (City.getName().compareTo(cityName) == 0) {
                return root;
            } else if (City.getName().compareTo(cityName) > 0) {
                //往左子树查找
                treeNode child = deleteByName(cityName, root.LChild);
                City childCity = (City) child.data;
                //获得要删除的孩子
                if (childCity.getName().compareTo(cityName) == 0) {

                    int height = root.height;
                    int balance = root.balance;
                    //返回节点的孩子数，2为有两孩子，0为没有孩子，1为只有一个右孩子，-1为只有一个左孩子
                    int leaf = isLeaf(child);
                    //先考虑删除节点只有一个或没有节点的情况
                    if (leaf != 2) {
                        //要删除的孩子没有子节点
                        if (leaf == 0) {
                            root.LChild = null;
                        } else if (leaf == -1) {
                            //只有左孩子
                            root.LChild = child.LChild;
                        } else if (leaf == 1) {
                            //只有右孩子
                            root.LChild = child.RChild;
                        }
                        //更新平衡因子，因为以上情况删除的节点都不会有两个孩子，所以可以算出删除后父节点的平衡因子和高度
                        if (balance == 0) {
                            root.balance = -1;
                        } else if (balance == 1) {
                            root.balance = 0;
                            root.height = height - 1;
                        } else {
                            root.balance = -2;
                        }
                        //纠正平衡
                        root = balance(root);
                        //更新纠正后的平衡因子和高度
                        updateBalance(root);
                    } else {
                        //处理要删除的节点有两孩子的情况，返回的是左子树最右的节点，且已经进行过平衡纠正
                        child = getTheLRChild(child);
                        //更新节点
                        root.LChild = child;
                        //更新高度和平衡因子
                        root.height = Math.max(root.LChild.height, root.RChild.height) + 1;
                        root.balance = root.LChild.height - root.RChild.height;
                        root = balance(root);
                    }
                    return root;
                } else {
                    //不是要删除的节点,查看删除节点后是否影响父节点
                    if (child != root.LChild) {
                        //孩子已经不是原来的孩子
                        root.LChild = child;
                    }
                    updateHeight(root);
                }
                return root;
            } else {

                treeNode child = deleteByName(cityName, root.RChild);
                City childCity = (City) child.data;
                //获得要删除的孩子
                if (childCity.getName().compareTo(cityName) == 0) {

                    int height = root.height;
                    int balance = root.balance;
                    int leaf = isLeaf(child);
                    //先考虑删除节点只有一个或没有节点的情况

                    if (leaf != 2) {
                        //要删除的孩子没有子节点
                        if (leaf == 0) {
                            root.RChild = null;
                        } else if (leaf == -1) {
                            //只有左孩子
                            root.RChild = child.LChild;
                        } else if (leaf == 1) {
                            //只有右孩子
                            root.RChild = child.RChild;
                        }
                        //更新平衡因子
                        if (balance == 0) {
                            root.balance = 1;
                        } else if (balance == -1) {
                            root.balance = 0;
                            root.height = height - 1;
                        } else {
                            root.balance = 2;
                        }
                        //纠正平衡
                        root = balance(root);
                        //更新纠正后的平衡因子和高度
                        updateBalance(root);
                        return root;
                    } else {
                        //处理要删除的节点有两孩子的情况
                        child = getTheLRChild(child);
                        root.RChild = child;
                        root.height = Math.max(root.LChild.height, root.RChild.height);
                        root.balance = root.LChild.height - root.RChild.height;
                        root = balance(root);
                        return root;
                    }
                } else {
                    //不是要删除的节点,查看删除节点后是否影响父节点
                    if (child != root.RChild) {
                        //孩子已经不是原来的孩子
                        root.RChild = child;
                    }
                    updateHeight(root);
                }
                return root;
            }
        } else {
            return null;
        }
    }

    //平衡方法
    public treeNode balance(treeNode node) {
        if (node.balance == 2) {
            if (node.LChild.balance == 0 || node.LChild.balance == 1) {
                return LL(root);
            } else {
                return LR(root);
            }
        } else if (node.balance == -2) {
            if (node.RChild.balance == 0 || node.RChild.balance == -1) {
                return RR(root);
            } else {
                return RL(root);
            }
        } else {
            return node;
        }
    }


    public treeNode getTheLRChild(treeNode root) {
        treeNode temp = root;
        while (temp.RChild.RChild != null) {
            temp = temp.RChild;
        }
        treeNode rChild = temp.RChild;

        if (temp.RChild.LChild != null) {
            temp.RChild = rChild.LChild;
        } else {
            temp.RChild = null;
        }

        rChild.RChild = root.RChild;
        rChild.LChild = root.LChild;
        updateBalance(rChild);
        if (rChild.balance == 2 || rChild.balance == -2) {
            rChild = balance(rChild);
            updateBalance(rChild);
        }
        return rChild;
    }

    public treeNode deleteTheRoot(treeNode root) {
        treeNode temp = root.LChild;
        treeNode tempParent = root;

        if (root.LChild==null){
            treeNode rChild = root.RChild;
            root.RChild=null;
            return rChild;
        }

        while (temp.RChild != null) {
            tempParent = temp;
            temp = temp.RChild;
        }
        treeNode rChild = temp;

        if (tempParent==root){
            rChild.RChild=root.RChild;
            root.LChild=null;
            root.RChild=null;
            updateBalance(rChild);
            return rChild;

        }


        if (rChild.LChild != null) {
            tempParent.RChild = rChild.LChild;
        } else {
            tempParent.RChild = null;
        }

        rChild.RChild = root.RChild;
        rChild.LChild = root.LChild;
        updateBalance(rChild);
        if (rChild.balance == 2 || rChild.balance == -2) {
            rChild = balance(rChild);
            updateBalance(rChild);
        }
        return rChild;
    }

}
