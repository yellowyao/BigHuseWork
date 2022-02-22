package suep.yao.bighusework.vo.SqLink;



public class SqLink implements ILink {
    public Object listElem[];//数组
    public int curLen;//表长

    public SqLink(Object[] listElem, int curLen) {
        this.listElem = listElem;
        this.curLen = curLen;
    }

    public SqLink(int maxSize) {
        listElem = new Object[maxSize];
        curLen = 0;
    }

    public SqLink(Object[] listElem) {
        this.listElem = listElem;
        curLen = listElem.length;
    }

    @Override
    public void clear() {
        curLen = 0;
    }

    @Override
    public boolean isEmpty() {
        return curLen == 0;
    }

    @Override
    public int length() {
        return curLen;
    }

    @Override
    public Object get(int i) throws Exception {
        if (i < 0 || i > curLen - 1) {
            throw new Exception("第" + i + "个元素不存在");
        }
        return listElem[i];
    }

    @Override
    public void insert(int i, Object x) throws Exception {
        if (curLen == listElem.length) {
            throw new Exception("该表已满，不可插入");
        }
        if (i < 0 || i > curLen) {
            throw new Exception("插入位置不合理");
        } else {

            for (int i1 = curLen; i1 > i; i1--) {
                listElem[i1] = listElem[i1 - 1];
            }
            listElem[i] = x;
            curLen++;
        }
    }

    @Override
    public void remove(int i) throws Exception {
        if (i < 0 || i > curLen) {
            throw new Exception("删除位置不合理");
        } else {
            for (int i1 = i; i1 < curLen - 1; i1++) {
                listElem[i1] = listElem[i1 + 1];
            }
            curLen--;
        }
    }

    @Override
    public int indexOf(Object x) {
        for (int i = 0; i < curLen; i++) {
            if (listElem[i].equals(x)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void reverse() {
        Object temp;
        for (int i = 0, j = curLen - 1; i < j; i++, j--) {
            temp = listElem[i];
            listElem[i] = listElem[j];
            listElem[j] = temp;
        }
    }

    @Override
    public void display() {
        for (int i = 0; i < curLen; i++) {
            System.out.print(listElem[i] + "  ");
        }
        System.out.println();
    }
}
