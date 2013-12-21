package org.howsun.relocated;

import java.util.Map;

/** A default implementation of {@link java.util.Map.Entry}
  *
  * @since 1.0
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @author <a href="mailto:mas@apache.org">Michael A. Smith</a>
  */
  
public class DefaultMapEntry implements Map.Entry {
    
    private Object key;
    private Object value;
    
    /**
     *  Constructs a new <Code>DefaultMapEntry</Code> with a null key
     *  and null value.
     */
    public DefaultMapEntry() {
    }

    /**
     *  Constructs a new <Code>DefaultMapEntry</Code> with the given
     *  key and given value.
     *
     *  @param key  the key for the entry, may be null
     *  @param value  the value for the entyr, may be null
     */
    public DefaultMapEntry(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    /**
     *  Implemented per API documentation of 
     *  {@link java.util.Map.Entry#equals(Object)}
     **/
    public boolean equals(Object o) {
        if( o == null ) return false;
        if( o == this ) return true;        

        if ( ! (o instanceof Map.Entry ) )
            return false;
        Map.Entry e2 = (Map.Entry)o;    
        return ((getKey() == null ?
                 e2.getKey() == null : getKey().equals(e2.getKey())) &&
                (getValue() == null ?
                 e2.getValue() == null : getValue().equals(e2.getValue())));
    }
     
     
    /**
     *  Implemented per API documentation of 
     *  {@link java.util.Map.Entry#hashCode()}
     **/
    public int hashCode() {
        return ( ( getKey() == null ? 0 : getKey().hashCode() ) ^
                 ( getValue() == null ? 0 : getValue().hashCode() ) ); 
    }
    


    // Map.Entry interface
    //-------------------------------------------------------------------------

    /**
     *  Returns the key.
     *
     *  @return the key 
     */
    public Object getKey() {
        return key;
    }


    /**
     *  Returns the value.
     *
     *  @return the value
     */
    public Object getValue() {
        return value;
    }

    // Properties
    //-------------------------------------------------------------------------    

    /**
     *  Sets the key.  This method does not modify any map.
     *
     *  @param key  the new key
     */
    public void setKey(Object key) {
        this.key = key;
    }
    
    /** Note that this method only sets the local reference inside this object and
      * does not modify the original Map.
      *
      * @return the old value of the value
      * @param value the new value
      */
    public Object setValue(Object value) {
        Object answer = this.value;
        this.value = value;
        return answer;
    }

}