package client.logic;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import static client.Constants.*;

/**
 * Created by vvrud on 12.09.16.
 *
 * @author vvrud
 *         This class provides sending information from client to server.
 */
public class SenderClient extends Thread {

    private static void sendOptions(DataOutputStream dataOutput) throws IOException {
        HashMap<String, String> options = PrintingData.getOptions();

        dataOutput.writeUTF(options.getOrDefault("speed", String.valueOf(DEFAULT_SPEED)));
        dataOutput.writeUTF(options.getOrDefault("mode", DEFAULT_MODE));
        dataOutput.writeUTF(options.getOrDefault("intensity", String.valueOf(DEFAULT_INTENSITY)));

        dataOutput.flush();
    }

    @Override
    public void run() {
        boolean printingInterrupted = PrintingData.isPrintingInterrupted();

        DataOutputStream dataOutput = PrintingData.getDataOutput();

        if (!printingInterrupted) {
            try {
                dataOutput.writeBoolean(printingInterrupted);
                sendOptions(dataOutput);
                sendFile(dataOutput);
            } catch (IOException e) {
                System.out.println("Failed sending interruption false with file and options");
                e.printStackTrace();
            }
        } else {
            try {
                dataOutput.writeBoolean(printingInterrupted);
                System.out.println("interruption sent");
            } catch (IOException e) {
                System.out.println("Failed sending interruption true");
                e.printStackTrace();
            }

        }
    }

    private void sendFile(DataOutputStream dataOutput) throws IOException {
        File file = PrintingData.getFile();
        FileInputStream input = new FileInputStream(file);
        long size = file.length();
        byte[] buffer = new byte[(int) size];

        dataOutput.writeLong(size);
        dataOutput.writeUTF(file.getName());

        int in;

        while ((in = input.read(buffer)) != -1) {
            dataOutput.write(buffer, 0, in);
        }

//        while(true) {
//            int receivedBytes = input.read(buffer);
//            if (receivedBytes == -1) {
//                dataOutput.write(-1);
//                break;
//            }
//            if (receivedBytes >= 0) dataOutput.write(buffer, 0, receivedBytes);
//        }

        input.close();
        dataOutput.flush();
    }
}
