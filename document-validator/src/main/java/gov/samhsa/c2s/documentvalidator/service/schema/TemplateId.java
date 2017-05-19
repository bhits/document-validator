package gov.samhsa.c2s.documentvalidator.service.schema;

import java.util.Optional;

public class TemplateId {
    private final String root;
    private final Optional<String> extension;

    public TemplateId(String root, Optional<String> extension) {
        this.root = root;
        this.extension = extension;
    }

    public String getRoot() {
        return root;
    }

    public Optional<String> getExtension() {
        return extension;
    }

    @Override
    public int hashCode() {
        return new StringBuilder()
                .append(root)
                .append(":")
                .append(extension.orElse("NULL"))
                .toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
                .filter(TemplateId.class::isInstance)
                .map(TemplateId.class::cast)
                .map(other -> extension.isPresent() || other.getExtension().isPresent() ?
                        root.equals(other.getRoot()) && extension.filter(e -> e.equals(other.getExtension().orElse(null))).isPresent() :
                        root.equals(other.getRoot()))
                .orElse(Boolean.FALSE);
    }

    public static TemplateId of(String root, Optional<String> extension) {
        return new TemplateId(root, extension);
    }

    public static TemplateId of(String root, String extension) {
        return of(root, Optional.ofNullable(extension));
    }
}
