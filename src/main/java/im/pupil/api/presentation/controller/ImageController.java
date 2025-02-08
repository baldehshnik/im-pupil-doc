package im.pupil.api.presentation.controller;

import im.pupil.api.domain.image.storage.ImageWorker;
import im.pupil.api.domain.exception.storage.FailedStorageConnectionException;
import im.pupil.api.domain.exception.storage.response.StorageErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.UnexpectedException;

@RestController
@RequestMapping("/test")
public class ImageController {
    @Autowired
    ImageWorker imageWorker;

    @PostMapping("/save")
    public ResponseEntity<String> saveImage(
            @RequestPart("accountIcon")
            MultipartFile accountIcon
    ) throws UnexpectedException {
        String imageUrl = imageWorker.saveImage(accountIcon, ImageWorker.ImageType.ICON);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageUrl);
    }

    @ExceptionHandler
    private ResponseEntity<StorageErrorResponse> handleStorageConnectionException(FailedStorageConnectionException exception) {
        StorageErrorResponse storageErrorResponse = new StorageErrorResponse(exception.getMessage());

        return new ResponseEntity<>(storageErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<StorageErrorResponse> handleStorageConnectionException(UnexpectedException exception) {
        StorageErrorResponse storageErrorResponse = new StorageErrorResponse(exception.getMessage());

        return new ResponseEntity<>(storageErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
