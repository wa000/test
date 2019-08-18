package test.paint;

import java.io.FileInputStream;
import java.io.IOException;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;

public class PrintTest2
{
    public void drawImage(String fileName, int count)
    {
        try
        {
            DocFlavor dof = null;
            if (fileName.endsWith(".gif"))
            {
                dof = DocFlavor.INPUT_STREAM.GIF;
            }
            else if (fileName.endsWith(".jpg"))
            {
                dof = DocFlavor.INPUT_STREAM.JPEG;
            }
            else if (fileName.endsWith(".png"))
            {
                dof = DocFlavor.INPUT_STREAM.PNG;
            }
            else
            {
                dof = DocFlavor.INPUT_STREAM.GIF;
            }
            PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            pras.add(OrientationRequested.PORTRAIT);
            pras.add(new Copies(count));
            pras.add(PrintQuality.HIGH);
            DocAttributeSet das = new HashDocAttributeSet();
            // 设置打印纸张的大小（以毫米为单位）
            das.add(new MediaPrintableArea(0, 0, 210, 296, MediaPrintableArea.MM));
            FileInputStream fin = new FileInputStream(fileName);
            Doc doc = new SimpleDoc(fin, dof, das);
            DocPrintJob job = ps.createPrintJob();
            job.print(doc, pras);
            fin.close();
        }
        catch (IOException ie)
        {
            ie.printStackTrace();
        }
        catch (PrintException pe)
        {
            pe.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new PrintTest2().drawImage(
                "C:/Users/Administrator/Desktop/tempTz.bmp", 1);
    }
}