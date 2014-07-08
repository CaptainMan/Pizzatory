/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package historyTab;

import java.io.Serializable;
import java.util.Date;

/**
 * An entry to the history about a specific sale of an item.
 *
 * @author Jomel Walker
 */
public class HistoryEntrySale implements Comparable<HistoryEntrySale>, Serializable {

    private final Date timestamp;
    private final String productName;
    private final Integer amountSold;

    /**
     * The constructor.
     *
     * @param productName The name of the product this entry refers to a sale of
     * @param amountSold The amount that was sold
     */
    public HistoryEntrySale(String productName, Integer amountSold) {
        this.timestamp = new Date(); //Current time
        this.productName = productName;
        this.amountSold = amountSold;
    }

    /**
     * Gets when this entry was made.
     *
     * @return A date object referring containing when this was made
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the name of the product this entry is referring to a sale of.
     *
     * @return A String containing the name of the product
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Gets the amount of the product that was sold in the entry this is
     * referring to.
     *
     * @return an Integer containing the number sold
     */
    public Integer getAmountSold() {
        return amountSold;
    }

    @Override
    public int compareTo(HistoryEntrySale o) {
        return timestamp.compareTo(o.getTimestamp());
    }

    @Override
    public String toString() {
        //System.out.println("Stringing together history sales");
        String s = timestamp.toString() + " " + productName + " " + amountSold.toString();
        //System.out.println("Success");
        return s;
    }

}
