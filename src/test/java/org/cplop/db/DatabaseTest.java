package org.cplop.db;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.cplop.db.Database;

/**
 * Unit test for Database connection
 */
public class DatabaseTest
    extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DatabaseTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( DatabaseTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testDriverload()
        throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    /**
     * Rigourous Test :-)
     */
    public void testDatabase() {
        Database testDatabase;
        String url, user, pass;

        url = "jdbc:mysql://localhost/CPLOP";
        user = "root";
        pass = "Jeffrey";

        try {
            testDatabase = new Database(url, user, pass);
        } catch (Exception e) {
            assert(false);
        }
    }
}
