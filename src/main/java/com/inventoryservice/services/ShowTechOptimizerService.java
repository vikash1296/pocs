package com.inventoryservice.services;

import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

@Service
public class ShowTechOptimizerService {

    private static final String SLB_L4 = "SLB L4 Switching Stats:";
    private static final String FW_RESOURCE = "============= FW Resource usuage";

    public File optimizeFile(File inputFile) throws IOException {
        System.out.println("Inside optimizeFile()............");
        validateInputFile(inputFile);
        File optimizedFile = new File(inputFile.getParentFile(), "optimized_" + inputFile.getName());

        boolean proxyInfoFound = false;
        boolean slbSwitchingFound = false;
        boolean skipPu2 = false;
        boolean removeNextBlankLine = false;

        try (BufferedReader reader = Files.newBufferedReader(inputFile.toPath(), StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(optimizedFile.toPath(), StandardCharsets.UTF_8,
                     StandardOpenOption.CREATE,
                     StandardOpenOption.TRUNCATE_EXISTING)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();

                // Handle blank line removal after PU1 header or SLB lines
                if (removeNextBlankLine) {
                    if (trimmed.isEmpty()) {
                        removeNextBlankLine = false;
                        continue;
                    }
                    removeNextBlankLine = false;
                }

                // Step 1 — Detect Proxy Info section start
                if (trimmed.startsWith("============= Proxy Info")) {
                    proxyInfoFound = true;
                    slbSwitchingFound = false;
                    skipPu2 = false;
                    writer.write(line);
                    writer.newLine();
                    continue;
                }

                // Step 2 — Detect SLB Switching Stats — only inside Proxy Info section
                if (proxyInfoFound && !slbSwitchingFound && trimmed.equals("SLB Switching Stats:")) {
                    slbSwitchingFound = true;
                    writer.write(line);
                    writer.newLine();
                    removeNextBlankLine = true;
                    continue;
                }

                // Only apply PU logic when BOTH markers have been seen
                if (proxyInfoFound && slbSwitchingFound) {
                    if (isPU1Header(trimmed)) {
                        removeNextBlankLine = true;
                        continue;
                    }

                    // Start skipping from PU2 header
                    if (!skipPu2 && isPU2Header(trimmed)) {
                        skipPu2 = true;
                        continue;
                    }

                    // Inside PU2 skip block
                    if (skipPu2) {
                        if (trimmed.equals(SLB_L4)) {
                            skipPu2 = false;
                            slbSwitchingFound = false;
                            proxyInfoFound = false;
                            writer.write(line);
                            writer.newLine();
                        }
                        // skip everything else in PU2 block
                        continue;
                    }
                }
                writer.write(line);
                writer.newLine();
            }

        } catch (Exception ex) {
            Files.deleteIfExists(optimizedFile.toPath());
            throw ex;
        }
        return optimizedFile;
    }

    private boolean isPU1Header(String trimmed) {
        return trimmed.equals("Processing-Unit : 1")  || trimmed.equals("PU: 1");
    }

    private boolean isPU2Header(String trimmed) {
        return trimmed.equals("Processing-Unit : 2")  || trimmed.equals("PU: 2");
    }

    private void validateInputFile(File file) {
        if (file == null) {
            throw new IllegalArgumentException("Input file is null");
        }
        if (!file.exists()) {
            throw new IllegalArgumentException(
                    "File does not exist: " + file.getAbsolutePath());
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException(
                    "Provided path is not a file");
        }
        if (!file.canRead()) {
            throw new IllegalArgumentException(
                    "File cannot be read");
        }
    }


 public File optimizeFWResource(File inputFile) throws IOException {
        validateInputFile(inputFile);
        File optimizedFile = new File(inputFile.getParentFile(), "optimized_" + inputFile.getName());
        boolean fwResourceFound = false;
        boolean skipPu2 = false;
        boolean removeNextBlankLine = false;

        try (BufferedReader reader = Files.newBufferedReader(inputFile.toPath(), StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(optimizedFile.toPath(), StandardCharsets.UTF_8,
                     StandardOpenOption.CREATE,
                     StandardOpenOption.TRUNCATE_EXISTING)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (removeNextBlankLine) {
                    removeNextBlankLine = false;
                }

                if (trimmed.startsWith(FW_RESOURCE)) {
                    fwResourceFound = true;
                    skipPu2 = false;
                    writer.write(line);
                    writer.newLine();
                    continue;
                }

                if (fwResourceFound) {
                    // Remove PU1 header line only — keep data below it
                    if (isPU1Header(trimmed)) {
                        removeNextBlankLine = true;
                        continue;
                    }

                    // Start skipping from PU2 header
                    if (!skipPu2 && isPU2Header(trimmed)) {
                        skipPu2 = true;
                        continue;
                    }

                    // Inside PU2 skip block
                    if (skipPu2) {
                        if (trimmed.startsWith("============= FW Active Policy")) {
                            skipPu2 = false;
                            writer.write(line);
                            writer.newLine();
                            fwResourceFound = false;
                        }
                        continue;
                    }
                }

                // Write everything else
                writer.write(line);
                writer.newLine();
            }

        } catch (Exception ex) {
            Files.deleteIfExists(optimizedFile.toPath());
            throw ex;
        }

        return optimizedFile;
    }
}