package com.compomics.pridesearchparameterextractor.extraction.impl;

import com.compomics.pride_asa_pipeline.core.data.extractor.FileParameterExtractor;
import com.compomics.pride_asa_pipeline.model.MGFExtractionException;
import com.compomics.pride_asa_pipeline.model.ParameterExtractionException;
import com.compomics.pridesearchparameterextractor.extraction.PrideParameterExtractor;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

/**
 *
 * @author Kenneth Verheggen
 */
public class PrideMzIDParameterExtractor implements PrideParameterExtractor{

    private static final Logger LOGGER = Logger.getLogger(PrideMzIDParameterExtractor.class);

    /**
     * The input mzID file
     */
    private final File inputFile;
    /**
     * A list of peakfiles belonging to this mzID
     */
    private final List<File> peakFiles;

    //Save the output of Ids
    private Boolean saveIDs = true;
    /**
     * The folder to store the resulting file(s)
     */
    private File outputFolder = null;

    private String outputFile = null;
    /**
     * Boolean indicating if the mgf file should be exported as well
     */
    private boolean saveMGF = false;

    /**
     * Constructor for a parameter extractor
     *
     * @param inputFile The input prideXML file
     * @param peakFiles A list of peakfiles belonging to this mzID
     * @param outputFolder The folder to store the resulting file(s)
     * @param saveMGF Boolean indicating if the mgf file should be exported as
     * well
     * @throws ParameterExtractionException if the parameters could not be
     * extracted correctly
     */
    public PrideMzIDParameterExtractor(File inputFile, List<File> peakFiles, File outputFolder, boolean saveMGF) throws ParameterExtractionException {
        this.inputFile = inputFile;
        this.peakFiles = peakFiles;
        this.outputFolder = outputFolder;
        this.saveMGF = saveMGF;
    }

    /**
     * Constructor for a parameter extractor
     *
     * @param inputFile The input prideXML file
     * @param peakFiles A list of peakfiles belonging to this mzID
     * @param outputFolder The folder to store the resulting file(s)
     * @throws ParameterExtractionException if the parameters could not be
     * extracted correctly
     */
    public PrideMzIDParameterExtractor(File inputFile, List<File> peakFiles, File outputFolder) throws ParameterExtractionException {
        this.inputFile = inputFile;
        this.peakFiles = peakFiles;
        this.outputFolder = outputFolder;
    }

    public PrideMzIDParameterExtractor(File inputFile, List<File> peakFiles, String fileName, Boolean saveMGF, Boolean saveIds) {
        this.inputFile = inputFile;
        this.peakFiles = peakFiles;
        this.outputFile = fileName;
        this.saveMGF = saveMGF;
        this.saveIDs = saveIds;
    }


    /**
     * Analyzes the provided input and generates the parameter file at the
     * provided outputfolder
     *
     * @return a boolean indicating the success of the extraction
     */
    @Override
    public boolean analyze() throws ParameterExtractionException, ExecutionException, TimeoutException, MGFExtractionException, InterruptedException {
        boolean succeeded = false;
        FileParameterExtractor extractor = null;
        try {
            if(inputFile != null)
                 extractor = new FileParameterExtractor(outputFile, saveMGF, saveIDs);
            else if (outputFolder != null)
                 extractor = new FileParameterExtractor(outputFolder, saveMGF, saveIDs);
            else {
                LOGGER.error("OutputFolder or FileName not defined -- File not converted");
                return false;
            }
            extractor.analyzeMzID(inputFile,peakFiles, inputFile.getName());
            succeeded = true;
        } catch (IOException ex) {
            LOGGER.error(ex);
        }
        return succeeded;
    }

}
