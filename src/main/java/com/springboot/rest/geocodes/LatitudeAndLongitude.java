package com.springboot.rest.geocodes;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Gets latitude and longitude for a given address or PIN code
 *
 */
public class LatitudeAndLongitude 
{
    private static final String GEO_CODE_SERVER = "http://maps.googleapis.com/maps/api/geocode/json?";

    public String getLocation(String code)
    {
        String address = buildUrl(code);

        String content = null;

        try
        {
            URL url = new URL(address);

            InputStream stream = url.openStream();

            try
            {
                int available = stream.available();

                byte[] bytes = new byte[available];

                stream.read(bytes);

                content = new String(bytes);
            }
            finally
            {
                stream.close();
            }

            return (String) content.toString();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    /**
     * Builds the url which contains google maps api with appened address
     * 
     */
    private static String buildUrl(String code)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(GEO_CODE_SERVER);

        builder.append("address=");
        builder.append(code.replaceAll(" ", "+"));
        builder.append("&sensor=false");

        return builder.toString();
    }

    /**
     * Gets latitue and longitude in a String array
     * 
     */
    public String[] parseLocation(String response)
    {
        String[] lines = response.split("\n");

        String lat = null;
        String lng = null;

        for (int i = 0; i < lines.length; i++)
        {
            if ("\"location\" : {".equals(lines[i].trim()))
            {
                lat = getOrdinate(lines[i+1]);
                lng = getOrdinate(lines[i+2]);
                break;
            }
        }

        return new String[] {lat, lng};
    }
    
    /**
     * Returns latitue or longitude alue
     * 
     */
    private static String getOrdinate(String s)
    {
        String[] split = s.trim().split(" ");

        if (split.length < 1)
        {
            return null;
        }

        String ord = split[split.length - 1];

        if (ord.endsWith(","))
        {
            ord = ord.substring(0, ord.length() - 1);
        }

        // Check that the result is a valid double
        Double.parseDouble(ord);

        return ord;
    }
}