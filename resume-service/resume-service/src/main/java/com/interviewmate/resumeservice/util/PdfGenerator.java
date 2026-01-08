package com.interviewmate.resumeservice.util;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class PdfGenerator {

    public static byte[] convertHtmlToPdf(String htmlContent) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(outputStream);
            builder.run();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}

