package com.mvhs.personalcloud;

import java.util.Date;

/**
 * Created by tyler on 11/12/2016.
 */

public class CustomImage
{

    private long id;
    private String localPath;
    private Date date;

    public CustomImage(long _id, String _localPath, Date _date)
    {
        id = _id;
        localPath = _localPath;
        date = _date;
    }

    public long getImageId()
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

    public void setId(long id)
    {
        this.id = id;
    }

    public void setLocalPath(String localPath)
    {
        this.localPath = localPath;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
