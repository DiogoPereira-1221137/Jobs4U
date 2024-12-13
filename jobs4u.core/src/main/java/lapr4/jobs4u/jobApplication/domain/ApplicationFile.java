package lapr4.jobs4u.jobApplication.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.StringMixin;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lapr4.jobs4u.jobopening.domain.ContractType;
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * The type File path.
 */

@Embeddable

public class ApplicationFile implements ValueObject, Comparable<ApplicationFile> {
    private Type fileType;
    private String relativeFilePath;
    @Lob
    private byte[] fileContent;

    public ApplicationFile(Type type ,String relativeFilePath, File file) throws IOException {
        this.fileType = type;
        this.relativeFilePath = relativeFilePath;
        this.fileContent = Files.readAllBytes(file.toPath());
    }
    public ApplicationFile(Type type ,String relativeFilePath, String fileContent) throws IOException {
        this.fileType = type;
        this.relativeFilePath = relativeFilePath;
        this.fileContent = fileContent.getBytes(StandardCharsets.UTF_8);
    }
    public ApplicationFile(Type type ,String relativeFilePath, byte[] fileContent) throws IOException {
        this.fileType = type;
        this.relativeFilePath = relativeFilePath;
        this.fileContent = fileContent;
    }

    protected ApplicationFile() {

    }
    public Type type() {
        return fileType;
    }
    public String contentAsString(){
        return new String(this.fileContent, StandardCharsets.UTF_8);
    }
    public byte[] contentAsByte(){
        return this.fileContent;
    }

    public String filePath(){
        return relativeFilePath;
    }

    @Override
    public String toString() {
        return "\nrelativeFilePath= " + relativeFilePath;
    }

    @Override
    public int compareTo(ApplicationFile o) {
        return 0;
    }

    public String fileName() {
        String[] path = filePath().split("/");
        return path[path.length-1];
    }
}

