package time;

/**
 * Represents a quarter that should be used to extract data from the SEC - data-
 * set webpage.
 *
 * @author Fabian
 */
public final class CountabaleQuarter extends Quarter {

    public CountabaleQuarter() {
        year = 2009;
        q = 4;
    }

    public void increment() {
        q++;
        if (q == 5) {
            q = 1;
            year++;
        }
    }

    public void decrement() {
        q--;
        if (q == 0) {
            q = 4;
            year--;
        }
    }

    /**
     * Gets the quarter in the form of "year" + "q" eg. 2019q2
     *
     * @return a textual representation of a quarter.
     */
    public String getQuarter() {
        return year + "q" + q;
    }

    public String getQuarterIncrement() {
        String q = getQuarter();
        increment();
        return q;
    }

    public void resetQuarterCounter() {
        q = 1;
        year = 2009;
    }

    @Override
    public String toString() {
        return "Quarter{" + "q=" + q + ", year=" + year + '}';
    }

}
