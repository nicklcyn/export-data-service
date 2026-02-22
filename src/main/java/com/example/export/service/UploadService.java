package com.example.export.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface UploadService {

    public void uploadBatch(List<File> files) throws IOException ;

    public void recover(IOException e, List<File> files);

}
