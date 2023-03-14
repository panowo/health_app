package com.example.sportapp.placetool;

import java.util.List;

public class City
{
    private String id;
    private String name;
    private List<DiQu> diQus;
    public City()
    {
        super();

    }
    public City(String id, String name, List<DiQu> diQus)
    {
        super();
        this.id = id;
        this.name = name;
        this.diQus = diQus;
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
    public List<DiQu> getDiQus()
    {
        return diQus;
    }
    public void setDiQus(List<DiQu> diQus)
    {
        this.diQus = diQus;
    }
    @Override
    public String toString()
    {
        return name;
    }

}
