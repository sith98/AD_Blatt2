import java.util.Arrays;

public final class Aufgabe3 {
    
    
    static final class IntStack {
        private int[] stack = new int[10];
        private int size;
        
        final boolean isEmpty() {
            return this.size == 0;
        }
        
        
        final void push(int element) {
            if (this.size >= this.stack.length) {
                this.stack = Arrays.copyOf(this.stack, this.stack.length * 2);
            }
            
            this.stack[this.size] = element;
            this.size++;
        }
        
        final int peek() {
            return this.stack[this.size - 1];
        }
        
        final int pop() {
            int result = this.stack[this.size - 1];
            this.size--;
            return result;
        }
    }
    
    private static int f(int n, int m) {
        if (n == 0) return m + 1;
        else if (m == 0) return f(n - 1, 1);
        else return f(n - 1, f(n, m - 1));
    }
    
    private static int itF(int paramN, int paramM) {
        IntStack stack = new IntStack();
        stack.push(paramN);
        stack.push(paramM);
        
        while (true) {
            int m = stack.pop();
            int n = stack.pop();
            
            if (n == 0) {
                int result = m + 1;
                if (stack.isEmpty()) {
                    return result;
                }
                
                stack.push(result);
            } else if (m == 0) {
                stack.push(n - 1);
                stack.push(1);
            } else {
                stack.push(n - 1);
                stack.push(n);
                stack.push(m - 1);
            }
        }
    }
    
    public static void main(String[] args) {
        for (int i = 0; i <= 20; i++) {
            System.out.println(f(3, i));
        }
        
    }
}
