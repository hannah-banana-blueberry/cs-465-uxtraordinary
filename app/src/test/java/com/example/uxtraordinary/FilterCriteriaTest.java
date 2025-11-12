package com.example.uxtraordinary;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for FilterCriteria data class
 * Tests all getters, setters, constructors, and data integrity
 */
public class FilterCriteriaTest {

    private FilterCriteria filterCriteria;

    @Before
    public void setUp() {
        filterCriteria = new FilterCriteria();
    }

    /**
     * Test default constructor initializes with correct default values
     */
    @Test
    public void testDefaultConstructor() {
        FilterCriteria criteria = new FilterCriteria();

        assertEquals(0.5f, criteria.getDistanceMiles(), 0.001f);
        assertFalse(criteria.isOpenNow());
        assertFalse(criteria.isHasCafeFood());
        assertEquals("Thomas M. Siebel Center for Computer Science", criteria.getCurrentLocation());
    }

    /**
     * Test parameterized constructor sets all values correctly
     */
    @Test
    public void testParameterizedConstructor() {
        FilterCriteria criteria = new FilterCriteria(2.5f, true, true, "Grainger Library");

        assertEquals(2.5f, criteria.getDistanceMiles(), 0.001f);
        assertTrue(criteria.isOpenNow());
        assertTrue(criteria.isHasCafeFood());
        assertEquals("Grainger Library", criteria.getCurrentLocation());
    }

    /**
     * Test distance miles setter and getter
     */
    @Test
    public void testSetAndGetDistanceMiles() {
        filterCriteria.setDistanceMiles(3.5f);
        assertEquals(3.5f, filterCriteria.getDistanceMiles(), 0.001f);

        filterCriteria.setDistanceMiles(0.0f);
        assertEquals(0.0f, filterCriteria.getDistanceMiles(), 0.001f);

        filterCriteria.setDistanceMiles(5.0f);
        assertEquals(5.0f, filterCriteria.getDistanceMiles(), 0.001f);
    }

    /**
     * Test open now setter and getter
     */
    @Test
    public void testSetAndGetOpenNow() {
        filterCriteria.setOpenNow(true);
        assertTrue(filterCriteria.isOpenNow());

        filterCriteria.setOpenNow(false);
        assertFalse(filterCriteria.isOpenNow());
    }

    /**
     * Test cafe/food setter and getter
     */
    @Test
    public void testSetAndGetHasCafeFood() {
        filterCriteria.setHasCafeFood(true);
        assertTrue(filterCriteria.isHasCafeFood());

        filterCriteria.setHasCafeFood(false);
        assertFalse(filterCriteria.isHasCafeFood());
    }

    /**
     * Test current location setter and getter
     */
    @Test
    public void testSetAndGetCurrentLocation() {
        filterCriteria.setCurrentLocation("Grainger Engineering Library");
        assertEquals("Grainger Engineering Library", filterCriteria.getCurrentLocation());

        filterCriteria.setCurrentLocation("Undergraduate Library");
        assertEquals("Undergraduate Library", filterCriteria.getCurrentLocation());
    }

    /**
     * Test toString method contains all field values
     */
    @Test
    public void testToString() {
        FilterCriteria criteria = new FilterCriteria(1.5f, true, false, "Test Location");
        String result = criteria.toString();

        assertTrue(result.contains("1.5"));
        assertTrue(result.contains("true"));
        assertTrue(result.contains("false"));
        assertTrue(result.contains("Test Location"));
    }

    /**
     * Test boundary values for distance
     */
    @Test
    public void testDistanceBoundaryValues() {
        // Minimum distance
        filterCriteria.setDistanceMiles(0.0f);
        assertEquals(0.0f, filterCriteria.getDistanceMiles(), 0.001f);

        // Maximum distance
        filterCriteria.setDistanceMiles(5.0f);
        assertEquals(5.0f, filterCriteria.getDistanceMiles(), 0.001f);

        // Mid-range distances
        filterCriteria.setDistanceMiles(2.5f);
        assertEquals(2.5f, filterCriteria.getDistanceMiles(), 0.001f);
    }

    /**
     * Test all combinations of boolean filters
     */
    @Test
    public void testBooleanFilterCombinations() {
        // Both false
        filterCriteria.setOpenNow(false);
        filterCriteria.setHasCafeFood(false);
        assertFalse(filterCriteria.isOpenNow());
        assertFalse(filterCriteria.isHasCafeFood());

        // Open now true, cafe false
        filterCriteria.setOpenNow(true);
        filterCriteria.setHasCafeFood(false);
        assertTrue(filterCriteria.isOpenNow());
        assertFalse(filterCriteria.isHasCafeFood());

        // Open now false, cafe true
        filterCriteria.setOpenNow(false);
        filterCriteria.setHasCafeFood(true);
        assertFalse(filterCriteria.isOpenNow());
        assertTrue(filterCriteria.isHasCafeFood());

        // Both true
        filterCriteria.setOpenNow(true);
        filterCriteria.setHasCafeFood(true);
        assertTrue(filterCriteria.isOpenNow());
        assertTrue(filterCriteria.isHasCafeFood());
    }

    /**
     * Test setting null location
     */
    @Test
    public void testNullLocation() {
        filterCriteria.setCurrentLocation(null);
        assertNull(filterCriteria.getCurrentLocation());
    }

    /**
     * Test setting empty location
     */
    @Test
    public void testEmptyLocation() {
        filterCriteria.setCurrentLocation("");
        assertEquals("", filterCriteria.getCurrentLocation());
    }

    /**
     * Test immutability of one field doesn't affect others
     */
    @Test
    public void testFieldIndependence() {
        filterCriteria.setDistanceMiles(2.0f);
        filterCriteria.setOpenNow(true);
        filterCriteria.setHasCafeFood(true);
        filterCriteria.setCurrentLocation("Test Building");

        // Change one field
        filterCriteria.setDistanceMiles(4.0f);

        // Verify others remain unchanged
        assertTrue(filterCriteria.isOpenNow());
        assertTrue(filterCriteria.isHasCafeFood());
        assertEquals("Test Building", filterCriteria.getCurrentLocation());
    }
}