/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package historyTab;

import java.io.Serializable;
import java.util.Date;

/**
 * An entry to the history about a specific change in quantity of an item.
 *
 * @author Jomel Walker
 */
public class HistoryEntryInventory implements Comparable<HistoryEntryInventory>, Serializable {

    private final Date timestamp;
    private final String itemName;
    private final Integer amount;

    /**
     * constructs a new HistoryEntry.
     *
     * @param itemName The name of the item being modified
     * @param amount
     */
    public HistoryEntryInventory(String itemName, Integer amount) {
        timestamp = new Date(); //Current time
        this.itemName = itemName;
        this.amount = amount;
    }

    @Override
    public String toString() {
        String toReturn = timestamp.toString()
                + " " + itemName + " "
                + amount.toString();
        return toReturn;
    }

    /**
     * Gets the timestamp of the entry.
     *
     * @return A Date object of the timestamp.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the name of the item the entry is about.
     *
     * @return a String of the item name.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets the amount of the item as of the change being made.
     *
     * @return An integer of the amount.
     */
    public Integer getAmount() {
        return amount;
    }

    @Override
    public int compareTo(HistoryEntryInventory o) {
        return timestamp.compareTo(o.getTimestamp());
    }

}
