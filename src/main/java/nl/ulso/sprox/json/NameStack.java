package nl.ulso.sprox.json;

import java.util.Stack;

/**
 * Keeps a stack of names in the JSON structure. Every nested object increases the depth of the stack by one.
 */
final class NameStack {

    private final int maxDepth;
    private final Stack<Name> stack;

    public NameStack(String rootNodeName, int maximumStackDepth) {
        stack = new Stack<>();
        maxDepth = maximumStackDepth;
        push(rootNodeName);
    }

    void push(String name) {
        if (stack.size() == maxDepth) {
            throw new IndexOutOfBoundsException("Maximum stack depth reached: " + maxDepth);
        }
        stack.push(new Name(name));
    }

    String peek() {
        return stack.peek().value;
    }

    /*
     * Pops the name from the top of the stack, unless the name denotes a JSON array. In that case the actual pop is
     * postponed.
     */
    String conditionalPop() {
        final Name name = stack.peek();
        if (!name.isArray) {
            stack.pop();
        }
        return name.value;
    }

    String forcePop() {
        return stack.pop().value;
    }

    void markAsArray() {
        stack.peek().isArray = true;
    }

    private static final class Name {
        private final String value;
        private boolean isArray;

        Name(String name) {
            this.value = name;
            this.isArray = false;
        }
    }
}
