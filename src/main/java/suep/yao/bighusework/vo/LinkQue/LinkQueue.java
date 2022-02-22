package suep.yao.bighusework.vo.LinkQue;


public class LinkQueue implements IQueue {
    private Node front;
    private Node rear;
    @Override
    public void clear() {
        front=rear=null;
    }

    @Override
    public boolean isEmpty() {
        return front==null;
    }

    @Override
    public int length() {
        int length=0;
        Node p=front;
        while (p!=null){
            length++;
            p=p.getNext();
        }
        return length;
    }

    @Override
    public Object peek() {
        return front.getData();
    }

    @Override
    public Object delete() throws Exception {
        if (front==null){
            throw new Exception("队列为空");
        }else if (front==rear){
            Object data = front.getData();
            front=rear=null;
            return data;
        }
        Object data = front.getData();
        front=front.getNext();
        return data;

    }

    @Override
    public void insert(Object o) throws Exception {
        if (front==null){
            front=new Node(o);
            rear=front;
        }else {
            Node node = new Node(o);
            rear.setNext(node);
            rear=rear.getNext();
        }
    }

    @Override
    public void display() {
        Node p=front;
        while (p!=null){
            System.out.print(p.getData()+" ");
            p=p.getNext();
        }
        System.out.println();
    }
}
