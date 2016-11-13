package com.mvhs.personalcloud;

import java.util.Date;

/**
 * Created by tyler on 11/12/2016.
 */

public class CustomImage
{

    private int id;
    private String localPath;
    private Date date;

    public CustomImage(int _id, String _localPath, Date _date)
    {
        id = _id;
        localPath = _localPath;
        date = _date;
    }

    public int getImageId()
    {
        return(id);
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
