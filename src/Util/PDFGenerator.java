package Util;

/**
 *
 * @author Yieison
 */
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    public static void generarPDF(String archivoDestino, List<char[][]> table, List<String> movimientos) {
        try {
            PdfWriter writer = new PdfWriter(archivoDestino);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont fontRegular = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            document.add(new Paragraph("Reporte de Partida de Ajedrez: Peón vs Alfil").setFont(font).setFontSize(16));
            document.add(new Paragraph("Desarrollado por: Yieison Lizarazo - 1151938, Roberth Caicedo - 1151996").setFont(fontRegular).setFontSize(12));
            document.add(new Paragraph("\n"));

            StringBuilder seguimiento = new StringBuilder();

            for (int i = 0; i < table.size(); i++) {
                document.add(new Paragraph("Tablero " + (i + 1) + ":").setFont(font).setFontSize(14));
                document.add(new Paragraph("  0   1   2   3   4   5   6   7").setFont(font).setFontSize(12));

                char[][] matriz = table.get(i);
                for (int fila = 0; fila < matriz.length; fila++) {
                    StringBuilder row = new StringBuilder();
                    row.append(fila).append("  ");
                    for (int columna = 0; columna < matriz[fila].length; columna++) {
                        row.append(matriz[fila][columna]).append("   ");
                    }
                    document.add(new Paragraph(row.toString()).setFont(fontRegular).setFontSize(12));
                }
                document.add(new Paragraph("\n"));

                seguimiento.append(movimientos.get(i)).append(" ");

                document.add(new Paragraph("Movimientos: " + seguimiento.toString().trim()).setFont(fontRegular).setFontSize(12));
                document.add(new Paragraph("\n"));
            }

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
