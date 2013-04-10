/*
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package enclosing.application.node;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

/**
 * @author Administrator
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MuseigenText {
    private String[] contentArr = null;
    
    
    public String[] getContentArr() {
        return contentArr;
    }
    public void setContentArr(String[] contentArr) {
        this.contentArr = contentArr;
    }
    public MuseigenText(){
        try {
                URL url = new URL("http://www.enclosing.net/museigentext/onlyheader.php");
                URLConnection connection = url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"EUC-JP"));
                String line = reader.readLine();
                Vector buffer = new Vector();
                while(line != null){
                    buffer.add(line);
                    line = reader.readLine();
                }
                reader.close();
                if(buffer.size() <1){
                    this.contentArr=  new String[]{"museigen box is broken maybe. contact toukubo to fix it."};
                }else{
                    this.contentArr = (String[]) buffer.toArray(new String[buffer.size()]);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
