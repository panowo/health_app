package com.example.sportapp.placetool;

public class DiQu
{
    private String id;
    private String name;
    public DiQu()
    {
        super();

    }
    public DiQu(String id, String name)
    {
        super();
        this.id = id;
        this.name = name;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    @Override
    public String toString()
    {
        return name;
    }
}
