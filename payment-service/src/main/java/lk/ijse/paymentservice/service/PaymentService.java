package lk.ijse.paymentservice.service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lk.ijse.paymentservice.entity.Payment;
import lk.ijse.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment processPayment(Payment payment) {
        if (payment.getCardNumber().length() != 16 || payment.getCvv().length() != 3) {
            payment.setStatus("FAILED");
        } else {
            payment.setStatus("SUCCESS");
            payment.setPaymentTime(LocalDateTime.now());
        }
        return repository.save(payment);
    }

    public List<Payment> getAll() {
        return repository.findAll();
    }

    public Optional<Payment> getById(Long id) {
        return repository.findById(id);
    }

    public List<Payment> getByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public Payment updateStatus(Long id, String newStatus) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(newStatus);
        return repository.save(payment);
    }

    public byte[] generateReceipt(Long id) {
        Payment payment = repository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Payment Receipt"));
            document.add(new Paragraph("Payment ID: " + payment.getId()));
            document.add(new Paragraph("User ID: " + payment.getUserId()));
            document.add(new Paragraph("Amount: $" + payment.getAmount()));
            document.add(new Paragraph("Status: " + payment.getStatus()));
            document.add(new Paragraph("Time: " + payment.getPaymentTime()));
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate receipt", e);
        }
    }
}
