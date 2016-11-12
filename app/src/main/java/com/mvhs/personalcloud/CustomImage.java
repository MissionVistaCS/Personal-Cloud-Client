package com.mvhs.personalcloud;

import java.util.Date;

/**
 * Created by tyler on 11/12/2016.
 */

public class CustomImage {

    static int numImages;
    int id;
    String name;
    String localPath;
    Date date;

    CustomImage(int _id, String _name, String _localPath, Date _date)
    {
        id = _id;
        name = _name;
        localPath = _localPath;
        date = _date;
        numImages++;
    }

    public int getImageId()
    {
        return(id);
    }
    public String getImageName()
    {
        return(name);
    }
    public String getImagePath()
    {
        return(localPath);
    }
    public Date getImageDate()
    {
        return(date);
    }

}
