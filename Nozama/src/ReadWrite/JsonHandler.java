package ReadWrite;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import ReadWrite.Json.JSONArray;
import ReadWrite.Json.parser.JSONParser;
import ReadWrite.Json.parser.ParseException;

public class JsonHandler
{
    JSONParser jsonParser = new JSONParser();

    /**
     * This method reads a JSON file and returns a JSONArray representing the data from the file
     * @return JSONArray representing JSON file
     * @author Jordan Diaz
     * */
    public JSONArray getJSONArrayFromJson(String filename)
    {
        try (FileReader reader = new FileReader(filename))
        {
            Object obj = jsonParser.parse(reader);

            JSONArray data = (JSONArray) obj;

            return data;
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method writes a JSONArray into to a given Json file completely overriding what was previously written
     * @author Jordan Diaz
     * */
    public void writeToJson(String filename, JSONArray data)
    {
        try (FileWriter file = new FileWriter(filename))
        {

            String stringData = data.toJSONString();

            String tabCount = "";
            for (int i = 0; i < data.toJSONString().length(); i++)
            {

                if (stringData.charAt(i) == '[')
                {
                    tabCount += "\t";
                    file.append(stringData.charAt(i) + "\n" + tabCount);


                }
                else if (stringData.charAt(i) == ']')
                {
                    file.append("\n" + stringData.charAt(i));
                    tabCount = tabCount.substring(0, tabCount.length() - 1);

                }
                else if (stringData.charAt(i) == '{')
                {
                    tabCount += "\t";
                    file.append(stringData.charAt(i) + "\n" + tabCount);


                }
                else if (stringData.charAt(i) == '}')
                {
                    file.append("\n" + tabCount + stringData.charAt(i));
                    tabCount = tabCount.substring(0, tabCount.length() - 1);
                }
                else if (stringData.charAt(i) == ',')
                {
                    file.append(stringData.charAt(i) + "\n" + tabCount);
                }
                else
                {
                    file.append(stringData.charAt(i));

                }
            }

            file.flush();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


}
