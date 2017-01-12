package com.example;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by adaJQD on 2017/1/12.
 */
public class DimensCreator {
    private List<DimenValues> list;
    private final String xmlHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<resources>";
    private final String xmlFooter = "</resources>";
    private final String DIRTEMPLATE = "values-w%sdp";
    private final String dimenTemplate = "<dimen name=\"%s\">%sdp</dimen>";
    private final String dimenTemplateSp = "<dimen name=\"%s\">%ssp</dimen>";
    private final String DIRPath;

    public DimensCreator(String path, List<DimenValues> l) {
        DIRPath = path;
        list = l;
    }

    public void createAll() {
        for (int i : Config.supportDevices) {
            String[] strings = DIRPath.split("values");
            String dir = strings[0] + File.separator + String.format(DIRTEMPLATE, "" + i);
            File dirFile = new File(dir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            File file = new File(dirFile.getPath() + File.separator + "dimens.xml");
            float scale = (float) i / Config.defaultValue;
            createSingleFile(file, scale);
        }
    }


    private void createSingleFile(File file, float scale) {
        String data = xmlHeader;
        for (DimenValues values : list) {
            String itemValue = values.value;
            String itemData = null;
            if (values.value.contains("dp")) {
                float v = Float.parseFloat(values.value.replace("dp", "").trim());
                itemValue = String.valueOf(Math.round(v * scale));
                //itemValue = formatDimen(v * scale);
                 itemData = String.format(dimenTemplate, values.name, itemValue) + "\n";
            } else if (values.value.contains("sp")) {
                float v = Float.parseFloat(values.value.replace("sp", "").trim());
                itemValue = String.valueOf(Math.round(v * scale));
                 itemData = String.format(dimenTemplateSp, values.name, itemValue) + "\n";
            }
            data += itemData;
        }
        data += xmlFooter;

        FileOutputStream outputStream = null;
        ByteArrayInputStream inputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            inputStream = new ByteArrayInputStream(data.getBytes());
            byte[] buffer = new byte[inputStream.available()];
            while (inputStream.read(buffer) != -1) {
                outputStream.write(buffer);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private String formatDimen(float dimen) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(dimen);
    }
}
