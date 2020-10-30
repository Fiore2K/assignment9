package edu.uml.kfiore.model;

/**
 *  A simple container class used to capture form data.
 */
public class Symbol {

    private Boolean apple;
    private Boolean google;
    private Boolean amazon;

    /**
     * Java been pattern needs a default constructor
     */
    public Symbol() {
    }

    public Symbol(Boolean apple, Boolean google, Boolean amazon) {
        this.apple = apple;
        this.google = google;
        this.amazon = amazon;
    }

    public Boolean getApple() {
        return apple;
    }

    public void setApple(Boolean apple) {
        this.apple = apple;
    }

    public Boolean getGoogle() {
        return google;
    }

    public void setGoogle(Boolean google) {
        this.google = google;
    }

    public Boolean getAmazon() { return amazon; }

    public void setAmazon(Boolean amazon) { this.amazon = amazon; }

    @Override
    public String toString() {
        return "Symbol{" +
                "Apple=" + apple +
                ", Google=" + google +
                ", Amazon=" + amazon +
                '}';
    }
}