package client.io;

import client.Client;
import client.data.PrintingData;

import java.io.*;

import static client.data.Constants.*;

/**
 * Created by vvrud on 12.09.16.
 *
 * @author vvrud
 *         This class provides sending information from client to server.
 */
public class SenderClient extends Thread {

    private boolean sent = false;
    private int mode = 1;

    public SenderClient(int mode) {
        this.mode = mode;
    }

    private static void sendOptions(ObjectOutputStream objectOutput) throws IOException {
        objectOutput.writeObject(PrintingData.getOptions());
        objectOutput.flush();
    }

    @Override
    public void run() {
        boolean printingInterrupted = PrintingData.isPrintingInterrupted();

        DataOutputStream dataOutput = Client.getDataOutput();
        ObjectOutputStream objectOutput = Client.getObjectOutput();
        if (printingInterrupted) {
            try {
                dataOutput.writeInt(INTERRUPTION);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (mode == JPG_MODE) {
            try {
                dataOutput.writeInt(TXT);
                sendOptions(objectOutput);
                sendFile(dataOutput);
            } catch (IOException e) {
                System.out.println("Failed sending file or options!");
                e.printStackTrace();
            }
        } else if (mode == BEZ_MODE) {
            try {
                dataOutput.writeInt(XML);
                sendFile(dataOutput);
            } catch (IOException e) {
                System.out.println("Failed sending xml");
                e.printStackTrace();
            }
        }


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
