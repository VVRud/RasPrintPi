package client.io;

import client.Client;
import client.data.PrintingData;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static client.data.Constants.BEZ_MODE;
import static client.data.Constants.TXT_MODE;

/**
 * Created by vvrud on 12.09.16.
 *
 * @author vvrud
 *         This class provides sending information from client to server.
 */
public class SenderClient extends Thread {

    private int mode = 1;

    @Override
    public void run() {

        boolean printingInterrupted = PrintingData.isPrintingInterrupted();

        if (mode == TXT_MODE) {

        } else if (mode == BEZ_MODE) {

        } else if (!printingInterrupted) {

        }

        DataOutputStream dataOutput = Client.getDataOutput();

        if (!printingInterrupted) {
            try {
                dataOutput.writeBoolean(printingInterrupted);
                sendFile(dataOutput);
            } catch (IOException e) {
                System.out.println("Failed sending interruption false with file");
                e.printStackTrace();
            }
        } else {
            try {
                dataOutput.writeBoolean(printingInterrupted);
                System.out.println("Interruption sent");
            } catch (IOException e) {
                System.out.println("Failed sending interruption true");
                e.printStackTrace();
            }

        }
    }

    private void sendFile(DataOutputStream dataOutput) throws IOException {
        File file = PrintingData.getJpgFile();
        if (file != null) {
            FileInputStream input = new FileInputStream(file);
            long size = file.length();
            byte[] buffer = new byte[(int) size];

            dataOutput.writeLong(size);
            dataOutput.writeUTF(file.getName());

            int in;

            while ((in = input.read(buffer)) != -1) {
                dataOutput.write(buffer, 0, in);
            }

            input.close();
            dataOutput.flush();
        } else throw new IOException("File is null");
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
