/*
 * AddressFile class 
 * Represents the random access address data file
 * presents interace to address data file
 */
package addressbook;

import java.io.RandomAccessFile;
import java.io.IOException;
/**
 *
 * @author Nic Smith
 */
public class AddressFile extends RandomAccessFile
{
    // size of 32, 32, 20, 2, 5
    public final static int NAME_LENGTH = 32;               // number of chars in name
    public final static int NAME_SIZE = 2 * NAME_LENGTH;    // number of bytes in name
    public final static int STREET_LENGTH = 32;             // number of chars in street
    public final static int STREET_SIZE = 2 * STREET_LENGTH;// number of bytes in street
    public final static int CITY_LENGTH = 20;               // number of chars in city
    public final static int CITY_SIZE = 2 * CITY_LENGTH;    // number of bytes in city
    public final static int STATE_LENGTH = 2;               // number of chars in state
    public final static int STATE_SIZE = 2 * STATE_LENGTH;  // number of bytes in state
    public final static int ZIP_LENGTH = 5;                 // number of chars in zip
    public final static int ZIP_SIZE = 2 * ZIP_LENGTH;      // number of bytes in zip
    public final static int RECORD_SIZE = NAME_SIZE + STREET_SIZE + CITY_SIZE + STATE_SIZE + ZIP_SIZE;
    
    public AddressFile() throws IOException
    {
        // open or create the address file on instantiation
        super("address.dat","rw");   
    }
    
    public Address readAddress(long position) throws IOException {
        Address rec = new Address();
        
        seek(position);  // locate and read record
        rec.setName(readFixedLengthString(NAME_LENGTH));
        rec.setStreet(readFixedLengthString(STREET_LENGTH));
        rec.setCity(readFixedLengthString(CITY_LENGTH));
        rec.setState(readFixedLengthString(STATE_LENGTH));
        rec.setZip(readFixedLengthString(ZIP_LENGTH));
        
        return rec;
    }
    
    public void writeAddress(long position, Address aItem) {
        try {
            seek(position);  // set position in file
            writeFixedLengthString(aItem.getName(),NAME_LENGTH);
            writeFixedLengthString(aItem.getName(),STREET_LENGTH);
            writeFixedLengthString(aItem.getName(),CITY_LENGTH);
            writeFixedLengthString(aItem.getName(),STATE_LENGTH);
            writeFixedLengthString(aItem.getName(),ZIP_LENGTH);
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void addAddress(Address item) {
        try {
            // add new record to end of file
            writeAddress(length(), item);
        } catch (IOException ex) { }
    }
    
    public Address firstAddress() {
        // return first record in file
        Address item = null;
        try {
            if (length() > 0) item = readAddress(0);            
        } catch (IOException ex) {
            item = null;
        }
        return item;
    }
    
    public Address lastAddress() {
        Address item = null;
        try {
            if (length() > 0)  {
                item = readAddress(length() - RECORD_SIZE);   
            }
            else return null;
        } catch (IOException ex) {
            item = null;
        }
        return item;
    }
    
    public Address nextAddress() {
        Address item = null;
        try {
            long currentPosition = getFilePointer();
            if (currentPosition < length()) {
                item = readAddress(currentPosition);
            }
        } catch (IOException ex) {
            item = null;
        }
        return item;
    }
    
    public Address previousAddress() {
        Address item = null;
        try {
            long currentPosition = getFilePointer();
            if (currentPosition - RECORD_SIZE > 0)
            {
                item = readAddress(currentPosition - 2 * RECORD_SIZE);
            } else {
                item = readAddress(0);
            }
        } catch (IOException ex) {
            item = null;
        }
        return item;
    }
    
    public void updateAddress(Address item) {
        try {
            writeAddress(getFilePointer() - RECORD_SIZE, item);
        } catch (IOException ex) { }        
    }
    
    // utility methods for dealing with fixed length strings
    
    private String readFixedLengthString(int length) throws IOException {
        char[] chars = new char[length];   // fixed length char buffer
        // read string in a character at a time
        for (int i = 0; i < length; i++) chars[i] = readChar();
        return new String(chars);
    }
    
    private void writeFixedLengthString(String s, int length) throws IOException {
        char[] chars = new char[length];  // fixed length char buffer
        // load String into char array
        s.getChars(0, Math.min(s.length(), length),  chars, 0);
        // fill in blank chars in rest of array
        for (int i = Math.min(s.length(), length); i < chars.length; i++) {
            chars[i] = ' ';
        }
        // write fixed length char array to file
        writeChars(new String(chars));
    }
    
}
