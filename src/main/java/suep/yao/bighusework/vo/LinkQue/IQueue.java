package suep.yao.bighusework.vo.LinkQue;

public interface IQueue<T> {
    public void clear();
    public boolean isEmpty();
    public int length();
    public T peek();
    public T delete() throws Exception;
    public void insert(T o) throws Exception;
    public void display();
}
