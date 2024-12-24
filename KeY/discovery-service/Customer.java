public class Customer {
    private long id;
    
    /*@ public normal_behavior
      @ ensures \result == id;
      @*/
    public long getId() {
        return id;
    }
    
    /*@ public normal_behavior
      @ ensures id == newId;
      @ assignable this.id;
      @*/
    public void setId(long newId) {
        this.id = newId;
    }
}