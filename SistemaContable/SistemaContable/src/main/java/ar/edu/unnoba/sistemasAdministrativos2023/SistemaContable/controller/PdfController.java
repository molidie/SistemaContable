package ar.edu.unnoba.sistemasAdministrativos2023.SistemaContable.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class PdfController {

    @GetMapping("/generate-pdf")
    public byte[] generatePdf() throws IOException {
        // Obtén los datos del libro diario, por ejemplo, desde la base de datos
        // Debes tener una lógica similar a la que tienes en tu controlador existente

        // Crear un nuevo documento PDF
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Libro Diario");

            // Agrega más contenido al PDF según tus necesidades
            // Por ejemplo, puedes iterar sobre los datos y agregar tablas, texto, etc.
        }

        // Guarda el PDF en un arreglo de bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.save(byteArrayOutputStream);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
