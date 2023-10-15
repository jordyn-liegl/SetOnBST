import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Kamilia Kamal Arifin and Jordyn Liegl
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Test the constructor.
     */

    @Test
    public void testConstructorNoArgument() {
        /*
         * Set up the variables and call the method under test.
         */
        Set<String> set = this.constructorRef();
        Set<String> setExpected = this.constructorTest();

        /*
         * Assert of the values of the variables match expectations.
         */
        assertEquals(setExpected, set);
    }

    /**
     * Test the kernel method add.
     */

    @Test
    public void testAdd_Boundary() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef();
        Set<String> setExpected = this.createFromArgsTest("1", "2", "3");

        /*
         * Call method under test.
         */
        set.add("1");
        set.add("2");
        set.add("3");

        /*
         * Assert the values of the variables match expectations
         */
        assertEquals(setExpected, set);

    }

    @Test
    public void testAdd_Routine() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef("hi", "hello", "hey");
        Set<String> setExpected = this.createFromArgsTest("hi", "hello", "hey",
                "greetings");

        /*
         * Call method under test.
         */
        set.add("greetings");

        /*
         * Assert the values of the variables match expectations
         */
        assertEquals(setExpected, set);

    }

    @Test
    public void testAdd_Challenging() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef("a", "b", "c");
        Set<String> setExpected = this.createFromArgsTest("a", "b", "c", "ab",
                "bc", "ac");

        /*
         * Call method under test.
         */
        set.add("ab");
        set.add("bc");
        set.add("ac");

        /*
         * Assert the values of the variables match expectations
         */
        assertEquals(setExpected, set);

    }

    /**
     * Test the kernel method remove.
     */

    @Test
    public void testRemove_Boundary() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef("1", "2", "3");
        Set<String> setExpected = this.createFromArgsTest();

        /*
         * Call method under test.
         */
        set.remove("1");
        set.remove("2");
        set.remove("3");

        /*
         * Assert the values of the variables match expectations
         */
        assertEquals(setExpected, set);

    }

    @Test
    public void testRemove_Routine() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef("a", "b", "c", "ab", "bc",
                "ac");
        Set<String> setExpected = this.createFromArgsTest("a", "b", "ab", "ac");

        /*
         * Call method under test.
         */
        set.remove("c");
        set.remove("bc");

        /*
         * Assert the values of the variables match expectations
         */
        assertEquals(setExpected, set);

    }

    @Test
    public void testRemove_Challenging() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef("1", "11", "111", "1111",
                "11111");
        Set<String> setExpected = this.createFromArgsTest("11", "1111");

        /*
         * Call method under test.
         */
        set.remove("1");
        set.remove("111");
        set.remove("11111");

        /*
         * Assert the values of the variables match expectations
         */
        assertEquals(setExpected, set);

    }

    /**
     * Test the kernel method removeAny.
     */

    @Test
    public void testRemoveAny_Boundary() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef("a");
        Set<String> setExpected = this.createFromArgsTest();

        /*
         * Call method under test.
         */
        String removedVal = set.removeAny();

        /*
         * Assert the values of the variables match expectations
         */
        assertEquals(setExpected, set);

    }

    @Test
    public void testRemoveAny_Routine() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef("a", "b", "c", "ab", "bc",
                "abc");
        Set<String> setExpected = this.createFromArgsTest("a", "b", "c", "ab",
                "bc", "abc");

        /*
         * Call method under test.
         */
        String removedVal = set.removeAny();

        /*
         * Assert the values of the variables match expectations
         */
        assertTrue(setExpected.contains(removedVal)
                && set.size() == setExpected.size() - 1);

    }

    @Test
    public void testRemoveAny_Challenging() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef("1", "11", "111", "1111",
                "11111");
        Set<String> setExpected = this.createFromArgsTest("1", "11", "111",
                "1111", "11111");

        /*
         * Call method under test.
         */
        String removedVal = set.removeAny();

        /*
         * Assert the values of the variables match expectations
         */
        assertTrue(setExpected.contains(removedVal)
                && set.size() == setExpected.size() - 1);

    }

    /**
     * Test the kernel method contains.
     */

    @Test
    public void testContains_Boundary() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef("1");
        Set<String> setExpected = this.createFromArgsTest("1");

        /*
         * Call method under test and assert the values of the variables match
         * expectations
         */
        assertTrue(setExpected.contains("1") == set.contains("1"));

    }

    @Test
    public void testContains_Routine() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef("1", "2", "3", "4");
        Set<String> setExpected = this.createFromArgsTest("1", "2", "3", "4");

        /*
         * Call method under test and assert the values of the variables match
         * expectations
         */
        assertTrue(setExpected.contains("5") == set.contains("5"));

    }

    @Test
    public void testContains_Challenging() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef("1", "11", "111", "1111");
        Set<String> setExpected = this.createFromArgsTest("1", "11", "111",
                "1111");

        /*
         * Call method under test and assert the values of the variables match
         * expectations
         */
        assertTrue(setExpected.contains("11111") == set.contains("11111"));

    }

    /**
     * Test the kernel method size.
     */
    @Test
    public void testSize_Boundary() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef("1");
        Set<String> setExpected = this.createFromArgsTest("1");

        /*
         * Call method under test and assert the values of the variables match
         * expectations
         */
        assertEquals(setExpected, set);
        assertEquals(1, set.size());

    }

    @Test
    public void testSize_Routine() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef("1", "2", "3", "4");
        Set<String> setExpected = this.createFromArgsTest("1", "2", "3", "4");

        /*
         * Call method under test and assert the values of the variables match
         * expectations
         */
        assertEquals(setExpected, set);
        assertEquals(4, set.size());

    }

    @Test
    public void testSize_Challenging() {
        /*
         * Set up the variables.
         */
        Set<String> set = this.createFromArgsRef("", " ", "  ");
        Set<String> setExpected = this.createFromArgsTest("", " ", "  ");

        /*
         * Call method under test and assert the values of the variables match
         * expectations
         */
        assertEquals(setExpected, set);
        assertEquals(3, set.size());

    }

}
