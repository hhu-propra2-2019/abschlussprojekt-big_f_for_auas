package mops.infrastructure.adapters.webflow.datepoll;

import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.UploadDto;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Service
public final class UploadAdapter {

    public UploadDto initializeDto() {
        return new UploadDto();
    }

    public boolean processPollFile(MultipartFile pollFile, MessageContext context) {
        System.out.println(pollFile.getOriginalFilename());
        try (InputStream fileStream = pollFile.getInputStream()) {
            System.out.println(fileStream.readNBytes(10));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
