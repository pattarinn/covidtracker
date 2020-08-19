package tracker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for loading information.
 * 
 * @author Pattarin Wongwaipanich
 */
public class DownloadInformation {
    public void download(File dataFile, String URLtoLoad) {
        // check if the information file is up-to-date.
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (!(dateFormat.format(today).equals(dateFormat.format(dataFile.lastModified())))) {
            try {
                URL url = new URL(URLtoLoad);
                // read data from URL
                ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
                // open output file
                FileOutputStream outputFile = new FileOutputStream(dataFile);
                // download data
                outputFile.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
                outputFile.close();
            } catch (IOException e) {
                System.out.println("Cannot download information, please check your path or your internet");
                System.exit(1);
            }
        }
    }
}