package com.example.BhavCopySpringBoot.service;

import java.io.*;
import java.util.*;

import com.example.BhavCopySpringBoot.model.BhavcopyModel;
import com.example.BhavCopySpringBoot.repository.BhavcopyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BhavcopyService {

    @Autowired
    private BhavcopyRepository bhavcopyRepository;
    private static final Logger logger = LoggerFactory.getLogger(BhavcopyService.class);

    public List<BhavcopyModel> storeDataIntoDb(MultipartFile file) {

        List<BhavcopyModel> datalist = new ArrayList<>();
        try {
            logger.debug("storeDataIntoDb started");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 13) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }
                try {
                    BhavcopyModel copyData = new BhavcopyModel(
                            fields[0].trim(),
                            fields[1].trim(),
                            parseIntoDouble(fields[2].trim()),
                            parseIntoDouble(fields[3].trim()),
                            parseIntoDouble(fields[4].trim()),
                            parseIntoDouble(fields[5].trim()),
                            parseIntoDouble(fields[6].trim()),
                            parseIntoDouble(fields[7].trim()),
                            parseIntoLong(fields[8].trim()),
                            parseIntoDouble(fields[9].trim()),
                            fields[10].trim(),
                            parseIntoInt(fields[11].trim()),
                            fields[12].trim());

                    datalist.add(copyData);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            reader.close();
            if (!datalist.isEmpty()) {
                bhavcopyRepository.saveAll(datalist);
                bhavcopyRepository.flush();
            }
        } catch (Exception e) {
            System.out.println("Error While Reading Data From BhavCopy CSV..");
        }
        return datalist;
    }

    public Integer parseIntoInt(String data) {
        logger.debug("parseIntoInt started");

        if (data == null || data.isEmpty()) {
            return null;
        }
        try {
            int value = Integer.parseInt(data);
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public Double parseIntoDouble(String data) {
        logger.debug("parseIntoDouble started");

        if (data == null || data.isEmpty()) {
            return null;
        }
        try {
            Double value = Double.parseDouble(data);
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public Long parseIntoLong(String data) {
        logger.debug("parseIntoDouble started");

        if (data == null || data.isEmpty()) {
            return null;
        }
        try {
            Long value = Long.parseLong(data);
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public List<BhavcopyModel> getSymbolInfo(String symbol) {
        logger.debug("getSymbolInfo started");

        return bhavcopyRepository.findBySymbolFunc(symbol);
    }

    public Integer getCountOfSeries(String series) {
        logger.debug("getCountOfSeries started");

        return bhavcopyRepository.getCountBySeriesFunc(series);
    }

    public List<String> getSymbolWithParticularGain(Double gain) {
        logger.debug("getSymbolWithParticularGain started");

        return bhavcopyRepository.getSymbolWithGainFunc(gain);
    }

    public List<String> getSymbolWithParticularTopbot(Double gain) {
        logger.debug("getSymbolWithParticularTopbot started");

        return bhavcopyRepository.getSymbolWithTopBotFunc(gain);
    }

    public Double getSTDDEVbySeries(String series) {
        logger.debug("getSTDDEVbySeries started");

        return bhavcopyRepository.getSTDevWthSeriesFunc(series);
    }

    public List<String> getSymbolWithMaxGain(Integer count) {
        logger.debug("getSymbolWithMaxGain started");

        return bhavcopyRepository.getSymbolWithMaxGainFunc(count);
    }

    public List<String> getSymbolWithMinGain(Integer count) {
        logger.debug("getSymbolWithMinGain started");

        return bhavcopyRepository.getSymbolWithMinGainFunc(count);
    }

    public List<String> getSymbolWithMaxTrades(Integer count) {
        logger.debug("getSymbolWithMaxTrades started");

        return bhavcopyRepository.getSymbolWithMaxTradedFunc(count);
    }

    public List<String> getSymbolWithMinTrades(Integer count) {
        logger.debug("getSymbolWithMinTrades started");

        return bhavcopyRepository.getSymbolWithMinTradedFunc(count);
    }

    public String getSymbolWithMaxTradesBySeries(String series) {
        logger.debug("getSymbolWithMaxTradesBySeries started");

        return bhavcopyRepository.getSymbolWithMaxTradedBySeriesFunc(series);
    }

    public String getSymbolWithMinTradesBySeries(String series) {
        logger.debug("getSymbolWithMinTradesBySeries started");

        return bhavcopyRepository.getSymbolWithMinTradedBySeriesFunc(series);
    }
}
