package Players;

import java.io.*;


public class BlackjackPlayer implements Serializable
{
  
    private String name;
    
    public BlackjackPlayer()
    {
        
    }
    

    public BlackjackPlayer(String username)
    {
        setName(username);
    }
    

    public void setName(String name)
    {
        this.name = name;
    }
    

    public String getName()
    {
        return this.name;
    }
    
    public String toString()
    {
        return getName();
    }    
}