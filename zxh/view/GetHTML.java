package view;


import java.io.*;

public class GetHTML extends Builder{

    @Override
    public StringBuffer buildhtml() {
        StringBuffer data=new StringBuffer();
        try {

            String s=null;
            File file = new File("zxh/view/test.html");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while((s=reader.readLine())!=null){
                data.append(s).append("\r\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    @Override
    public StringBuffer createhtml() {
        return buildhtml();
    }
}
