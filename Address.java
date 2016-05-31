/*
 * Address class - Represents an item in the AddressBook inventory
 */
package addressbook;

/**
 *
 * @author Nic Smith
 */
public class Address 
{
    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    
    public Address() { this("","","", "", ""); }
    
    public Address( String name, String street, String city, String state, String zip )
    {
        setName(name);
        setStreet(street);
        setCity(city);
        setState(state);
        setZip(zip);
    }
    
    public String getName() { return name; }
    public void setName( String name ) { this.name = name; }
    public String getStreet() { return street; }
    public void setStreet( String street ) { this.name = street; }
    public String getCity() { return city; }
    public void setCity( String city ) { this.city = city; }
    public String getState() { return state; }
    public void setState( String state ) { this.state = state; }
    public String getZip() { return zip; }
    public void setZip( String zip ) { this.zip = zip; }
    
}
