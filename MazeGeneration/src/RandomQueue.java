import java.util.LinkedList;
import java.util.Random;

public class RandomQueue<E> {
    private LinkedList<E> queue;
    private Random rnd;
    
    public RandomQueue(){
        queue = new LinkedList<>();
        rnd = new Random();
    }
    
    public void add(E element){
        if (rnd.nextInt(2) == 0)
            queue.addFirst(element);
        else 
            queue.addLast(element);
    }
    
    public boolean isEmpty(){
        return queue.isEmpty();
    }
    
    public E remove(){
        if (queue.isEmpty())
            throw new IllegalArgumentException("Empty queue");
        
        if (rnd.nextInt(2) == 0)
            return queue.removeFirst();
        else
            return queue.removeLast();
    }
    
    public int size(){
        return queue.size();
    }
}