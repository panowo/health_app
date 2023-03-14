package com.example.sportapp.placetool;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.sportapp.placetool.City;
import com.example.sportapp.placetool.DiQu;
import com.example.sportapp.placetool.Province;

public class ParserTool
{
    public static List<Province> parserProvince(InputStream in) throws XmlPullParserException, IOException
    {

        List<Province> provs = null;

        Province prov =null;
        List<City> citys = null;

        City city =null;
        List<DiQu> diQus =null;

        DiQu diQu=null;
        String tagName=null;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();

        parser.setInput(in,"utf-8");

        int event = parser.getEventType();
        while(event!=XmlPullParser.END_DOCUMENT)
        {
            switch (event)
            {
                case XmlPullParser.START_DOCUMENT:
                    provs = new ArrayList<Province>();
                    break;
                case XmlPullParser.START_TAG:
                    tagName=parser.getName();
                    if("p".equals(tagName))
                    {
                        prov=new Province();
                        citys = new ArrayList<City>();
                        int count =parser.getAttributeCount();
                        for(int i=0;i<count;i++)
                        {
                            String attName =parser.getAttributeName(i);
                            String attValue = parser.getAttributeValue(i);
                            if("p_id".equals(attName))
                            {
                                prov.setId(attValue);
                            }
                        }
                    }
                    else if("pn".equals(tagName))
                    {
                        prov.setName(parser.nextText());
                    }
                    else if("c".equals(tagName))
                    {
                        city = new City();
                        diQus = new ArrayList<DiQu>();
                        int count =parser.getAttributeCount();
                        for(int i=0;i<count;i++)
                        {
                            String attName =parser.getAttributeName(i);
                            String attValue = parser.getAttributeValue(i);
                            if("c_id".equals(attName))
                            {
                                city.setId(attValue);
                            }
                        }
                    }
                    else if("cn".equals(tagName))
                    {
                        city.setName(parser.nextText());
                    }
                    else if("d".equals(tagName))
                    {
                        diQu = new DiQu();
                        int count =parser.getAttributeCount();
                        for(int i=0;i<count;i++)
                        {
                            String attName =parser.getAttributeName(i);
                            String attValue = parser.getAttributeValue(i);
                            if("d_id".equals(attName))
                            {
                                diQu.setId(attValue);
                            }
                        }
                        diQu.setName(parser.nextText());
                        diQus.add(diQu);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    tagName =parser.getName();
                    if("c".endsWith(tagName))
                    {
                        city.setDiQus(diQus);
                        citys.add(city);
                    }
                    else if("p".equals(tagName))
                    {
                        prov.setCitys(citys);
                        provs.add(prov);
                    }
                    break;
                default:
                    break;
            }
            event = parser.next();
        }
        return provs;
    }
}
