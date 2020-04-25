package com.gregorriegler.seamer.file;

import com.esotericsoftware.kryo.serializers.ClosureSerializer;
import com.gregorriegler.seamer.core.ProxySignature;
import com.gregorriegler.seamer.core.Signature;
import com.gregorriegler.seamer.core.SignatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Optional;

public class FileSignatureRepository<T> implements SignatureRepository<T> {

    private static final Logger LOG = LoggerFactory.getLogger(FileSignatureRepository.class);
    private final Serializer serializer;

    public FileSignatureRepository(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public void persist(String seamId, Signature<T> signature) {
        try {
            FileLocation.createSeamDir(seamId);
            File seamFile = FileLocation.seamFile(seamId);
            if (seamFile.exists()) return;
            LOG.info("persisting seam at {}", seamFile.getAbsolutePath());
            serializer.serialize(signature, new FileOutputStream(seamFile));
        } catch (FileNotFoundException e) {
            LOG.error("failed to persist seam", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<Signature<T>> byId(String seamId) {
        try {
            Object deserialize = serializer.deserialize(
                new FileInputStream(FileLocation.seamFile(seamId)),
                ClosureSerializer.Closure.class
            );
            return Optional.of((Signature<T>) deserialize);
        } catch (FileNotFoundException e) {
            LOG.error("failed to load seam", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ProxySignature<T>> proxyById(String seamId) {
        try {
            ProxySignature deserialize = serializer.deserialize(
                new FileInputStream(FileLocation.seamFile(seamId)),
                ProxySignature.class
            );
            return Optional.of(deserialize);
        } catch (FileNotFoundException e) {
            LOG.error("failed to load seam", e);
            return Optional.empty();
        }
    }

}