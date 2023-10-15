import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 *
 * @author Kamilia Kamal Arifin and Jordyn Liegl
 *
 */
public class Set3a<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        /*
         * By default, it is false because if the height of tree is 0, then it
         * is not in the tree.
         */
        boolean result = false;

        /*
         * Check the height of the tree.
         */
        if (t.height() > 0) {
            /*
             * Disassemble the left and right leave of the tree and assign it to
             * a variable.
             */
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T root = t.disassemble(left, right);

            /*
             * Check if the root is equals given variable x.
             */
            if (root.equals(x)) {
                /*
                 * If the root is equals to x, then it returns true.
                 */
                result = true;
            } else {
                if (root.compareTo(x) > 0) {
                    /*
                     * If compared and it returns a value more than zero,
                     * recursively check from the left of tree.
                     */
                    result = isInTree(left, x);
                } else {
                    /*
                     * If compared and it returns a value less than zero,
                     * recursively check from the right of tree.
                     */
                    result = isInTree(right, x);
                }
            }
            t.assemble(root, left, right);
        }
        return result;
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();

        /*
         * Check the height of tree.
         */
        if (t.height() == 0) {
            /*
             * If tree is empty, just insert the tree, t.
             */
            t.assemble(x, left, right);
        } else {
            /*
             * If compared and the tree is not empty, disassemble the left and
             * right leave of the tree and assign it to a variable.
             */
            T root = t.disassemble(left, right);
            if (x.compareTo(root) > 0) {
                /*
                 * If compared and it returns a value more than zero,
                 * recursively insert in the right of tree.
                 */
                insertInTree(right, x);
            } else {
                /*
                 * If compared and it returns a value less than zero,
                 * recursively insert in the left of tree.
                 */
                insertInTree(left, x);
            }
            t.assemble(root, left, right);
        }

    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";
        assert t.size() > 0 : "Violation of: |t| > 0";

        /*
         * Disassemble the left and right leave of the tree and assign it to a
         * variable.
         */
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        T root = t.disassemble(left, right);
        T smallestElement = root;

        /*
         * Smallest element will be on the left, so check the height of left
         * leaf.
         */
        if (left.height() != 0) {
            /*
             * Recursively remove the the smallest tree from the left of tree
             * until it meets the left-most label.
             */
            smallestElement = removeSmallest(left);
            t.assemble(root, left, right);
        }
        return smallestElement;
    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        assert t.size() > 0 : "Violation of: x is in labels(t)";

        /*
         * Disassemble the left and right leave of the tree and assign it to a
         * variable.
         */
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();

        T root = t.disassemble(left, right);
        T removed = root;

        /*
         * Check if the root is equals given variable x.
         */
        if (!root.equals(x)) {
            if (root.compareTo(x) < 0) {
                /*
                 * If compared and it returns a value less than zero,
                 * recursively search and remove from the right of tree.
                 */
                removed = removeFromTree(right, x);
            } else {
                /*
                 * If compared and it returns a value more than zero,
                 * recursively search remove from the left of tree.
                 */
                removed = removeFromTree(left, x);
            }
            t.assemble(root, left, right);
        } else {
            /*
             * If compared and it returns a value equals to zero, check if the
             * size of right leaf.
             */
            if (right.size() > 0) {
                /*
                 * If compared and it returns a value more than zero, search and
                 * remove the smallest tree from the right leaf.
                 */
                root = removeSmallest(right);
                t.assemble(root, left, right);
            } else {
                /*
                 * If compared and it returns a value less than zero, search and
                 * transfer the tree from the left leaf.
                 */
                t.transferFrom(left);
            }
        }

        return removed;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.tree = new BinaryTree1<T>();

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3a() {

        this.createNewRep();

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3a<?> : ""
                + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3a<T> localSource = (Set3a<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";

        insertInTree(this.tree, x);

    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        return removeFromTree(this.tree, x);
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";

        return removeSmallest(this.tree);
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        return isInTree(this.tree, x);
    }

    @Override
    public final int size() {

        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}
