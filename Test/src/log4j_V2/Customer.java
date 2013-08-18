/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package log4j_V2;

/**
 *
 * @author kamal64
 */
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;

/**
 *
 * @author Ruben Laguna <ruben.laguna at gmail.com>
 */
public class Customer {

    private static final Logger LOG = Logger.getLogger(Customer.class.getName());
    private static final String NOT_RETRIEVED_YET = "<not retrieved yet>";
    private int id = 0;
    public static final String PROP_ID = "id";
    protected String firstName = NOT_RETRIEVED_YET;
    public static final String PROP_FIRSTNAME = "firstName";
    protected String lastName = NOT_RETRIEVED_YET;
    public static final String PROP_LASTNAME = "lastName";
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    Customer(int j) {
        this.id = j;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        String oldFirstName = this.firstName;
        this.firstName = firstName;
        propertyChangeSupport.firePropertyChange(PROP_FIRSTNAME, oldFirstName, firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        String oldLastName = this.lastName;
        this.lastName = lastName;
        if (propertyChangeSupport.hasListeners(PROP_LASTNAME)) {
            LOG.info("notifying!!!");
        } else {
            LOG.info("there is no listeners for the property");
        }

        propertyChangeSupport.firePropertyChange(PROP_LASTNAME, oldLastName, lastName);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        LOG.info("add listener to customer " + this.id);
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return "id: " + id + " firstName: " + firstName;
    }
}