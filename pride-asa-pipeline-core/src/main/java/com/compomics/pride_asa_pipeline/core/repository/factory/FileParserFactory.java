/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.pride_asa_pipeline.core.repository.factory;

import com.compomics.pride_asa_pipeline.core.repository.FileParser;
import com.compomics.pride_asa_pipeline.core.repository.impl.MzIdentMlParser;
import com.compomics.pride_asa_pipeline.core.repository.impl.PrideXmlParser;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.mzxml_parser.MzXMLParsingException;

/**
 *
 * @author Kenneth Verheggen
 */
public class FileParserFactory {

    private static final Logger LOGGER = Logger.getLogger(FileParserFactory.class);
    private static File currentInputFile;
    private static FileParserType currentType;

    public static FileParser getFileParser(File inputFile) throws IOException, ClassNotFoundException, MzXMLParsingException, JMzReaderException {
        currentInputFile = inputFile;
        FileParser parser = null;
        String extension = FilenameUtils.getExtension(inputFile.getAbsolutePath()).toLowerCase();
        switch (extension.toLowerCase()) {
            case "mzid":
                LOGGER.info("Detected mzID file extension.");
                parser = new MzIdentMlParser();
                break;
            case "xml":
                LOGGER.info("Detected xml file extension.");
                parser = new PrideXmlParser();
                break;
            default:
                throw new IOException("Unsupported filetype for parameter-extraction : ." + extension);
        }
        parser.init(inputFile);
        return parser;
    }

    public static File getCurrentInputFile() {
        return currentInputFile;
    }

    public static void setCurrentInputFile(File currentInputFile) {
        FileParserFactory.currentInputFile = currentInputFile;
    }

    public static FileParserType getCurrentType() {
        return currentType;
    }

    public static void setCurrentType(FileParserType currentType) {
        FileParserFactory.currentType = currentType;
    }

}
