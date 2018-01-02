package com.ucloudlink.boot.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReader;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.ucloudlink.boot.domain.Department;
import com.ucloudlink.boot.domain.User;

@Component
public class XlsReader
{
	public static void main(String args[]) throws InvalidFormatException, IOException, SAXException
	{
		readData("department_data.xls","departments.xml");
	}
	
	public static List<User> readData(String fileName,String temple) throws IOException, SAXException, InvalidFormatException
	{

        //logger.info("Reading xml config file and constructing XLSReader");
        try(InputStream xmlInputStream = XlsReader.class.getResourceAsStream(temple)) {
            XLSReader reader = ReaderBuilder.buildFromXML(xmlInputStream);
            try (InputStream xlsInputStream = XlsReader.class.getResourceAsStream(fileName)) {
                List<User> users = new ArrayList<User>();
                Map<String, Object> beans = new HashMap<>();
                beans.put("users", users);
         
                reader.read(xlsInputStream, beans);
                
                System.out.println(users);
                
               return users;
            }
        }
    
		
		
		//return null;
	}

}
