package com.example.BhavCopySpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.BhavCopySpringBoot.service.BhavcopyService;
import java.util.*;
import com.example.BhavCopySpringBoot.model.BhavcopyModel;
import com.example.BhavCopySpringBoot.model.Response;


@RestController
@RequestMapping("/api/bhavcopy")
public class Bhavcopy {

    @Autowired
    private BhavcopyService bhavcopyService;
    Map<String, Object> response;

    @PostMapping("/uploadcsvfile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile csvfile) {

        try {
            List<BhavcopyModel> list = bhavcopyService.storeDataIntoDb(csvfile);
            if (list == null || list.isEmpty()) {
                Response res = new Response("error", "Error occured While Uploading File", "File Not Found", "");
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Response res = new Response("success", "File Uploaded SuccessFully", null, "");
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response res = new Response("error", "Error occured While Uploading File", e.getMessage(), null);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/getInformationBySymbol/{symbol}")
    public ResponseEntity<?> getParticularSymbol(@PathVariable String symbol) {
        try {
            List<BhavcopyModel> list = bhavcopyService.
            getSymbolInfo(symbol.trim());
            if (symbol.trim().isEmpty() || symbol == null) {
                Response res = new Response("error", "Symbol can't be invalid ,null or empty", "Invalid Symbol", null);
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            if (list == null || list.isEmpty()) {
                Response res = new Response("success", "No Records Found for Given Symbol", "", "");
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Response res = new Response("success", "Symbol information fetched SuccessFully", null, list);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response res = new Response("error", "Error occured While Fetching Data", e.getMessage(), null);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getCountBySeries/{series}")
    public ResponseEntity<?> getCountForSeries(@PathVariable String series) {
        try {
            Integer count = bhavcopyService.getCountOfSeries(series.trim());
            if (series.trim().isEmpty() || series == null) {
                Response res = new Response("error", "Series can't be invalid ,null or empty", "Invalid Series", null);
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (count == null || count == 0) {
                Response res = new Response("success", "No Records Found for Given series", "", "");
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Response res = new Response("success", "Count for Given Series fetched SuccessFully", null, count);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Response res = new Response("error", "Error occured While Fetching Data", e.getMessage(), null);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getSymbolsByGain/{gain}")
    public ResponseEntity<?> getSymbolsByGain(@PathVariable Double gain) {
        try {

            List<String> count = bhavcopyService.getSymbolWithParticularGain(gain);
            if (gain == null) {
                Response res = new Response("error", "Gain can't be invalid ", "Invalid Gain", null);
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (count == null || count.isEmpty()) {
                Response res = new Response("success", "No Records Found for Given Gain", "", "");
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Response res = new Response("success", "Symbols for Given Gain fetched SuccessFully", null, count);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Response res = new Response("error", "Error occured While Fetching Data", e.getMessage(), null);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getSymbolsByTopBot/{gain}")
    public ResponseEntity<?> getSymbolsByTopBot(@PathVariable Double gain) {
        try {
            List<String> count = bhavcopyService.getSymbolWithParticularTopbot(gain);
            if (gain == null) {
                Response res = new Response("error", "TopBot can't be invalid ot null ", "Invalid TopBot", null);
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (count == null || count.isEmpty()) {
                Response res = new Response("success", "No Records Found for Given TopBot", "", "");
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Response res = new Response("success", "Symbols for TopBot Series fetched SuccessFully", null, count);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Response res = new Response("error", "Error occured While Fetching Data", e.getMessage(), null);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getStandardDevBySeries/{series}")
    public ResponseEntity<?> getSTDDEVBySeries(@PathVariable String series) {
        try {
            Double stddv = bhavcopyService.getSTDDEVbySeries(series.trim());
            if (series.trim().isEmpty() || series == null) {
                Response res = new Response("error", "Series can't be invalid ,null or empty", "Invalid Series", null);
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (stddv == null || stddv == 0) {
                Response res = new Response("success", "No Records Found for Given series", "", "");
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Response res = new Response("success", "STDDEV fetched SuccessFully", null, String.format("%.4f", stddv));
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Response res = new Response("error", "Error occured While Fetching Data", e.getMessage(), null);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getMaxGainersByCount/{count}")
    public ResponseEntity<?> getMaxGainersByCount(@PathVariable Integer count) {
        try {
            List<String> list = bhavcopyService.getSymbolWithMaxGain(count);
            if (count == 0 || count == null) {
                Response res = new Response("error", "count can't be invalid ,null or zero", "Zero CountInput", null);
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (list == null || list.isEmpty()) {
                Response res = new Response("success", "No Records Found", "", "");
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Response res = new Response("success", "Records fetched SuccessFully", null, list);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Response res = new Response("error", "Error occured While Fetching Data", e.getMessage(), null);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getMinGainersByCount/{count}")
    public ResponseEntity<?> getMinGainersByCount(@PathVariable Integer count) {
        try {
            List<String> list = bhavcopyService.getSymbolWithMinGain(count);
            if (count == 0 || count == null) {
                Response res = new Response("error", "count can't be invalid ,null or zero", "Zero CountInput", null);
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (list == null || list.isEmpty()) {
                Response res = new Response("success", "No Records Found", "", "");
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Response res = new Response("success", "Records fetched SuccessFully", null, list);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Response res = new Response("error", "Error occured While Fetching Data", e.getMessage(), null);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getMaxTradersByCount/{count}")
    public ResponseEntity<?> getMaxTradedByCount(@PathVariable Integer count) {
        try {
            List<String> list = bhavcopyService.getSymbolWithMaxTrades(count);
            if (count == 0 || count == null) {
                Response res = new Response("error", "count can't be invalid ,null or zero", "Zero CountInput", null);
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (list == null || list.isEmpty()) {
                Response res = new Response("success", "No Records Found", "", "");
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Response res = new Response("success", "Records with Maximum Trades fetched SuccessFully", null, list);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Response res = new Response("error", "Error occured While Fetching Data", e.getMessage(), null);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getMinTradersByCount/{count}")
    public ResponseEntity<?> getMinTradedByCount(@PathVariable Integer count) {
        try {
            List<String> list = bhavcopyService.getSymbolWithMinTrades(count);
            if (count == 0 || count == null) {
                Response res = new Response("error", "count can't be invalid ,null or zero", "Zero CountInput", null);
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (list == null || list.isEmpty()) {
                Response res = new Response("success", "No Records Found", "", "");
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Response res = new Response("success", "Records with Minimum Trades fetched SuccessFully", null, list);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Response res = new Response("error", "Error occured While Fetching Data", e.getMessage(), null);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getHighLowTradedBySeries/{series}")
    public ResponseEntity<?> getHighLowTradedBySeries(@PathVariable String series) {
        try {
            String highest = bhavcopyService.getSymbolWithMaxTradesBySeries(series.trim());
            String lowest = bhavcopyService.getSymbolWithMinTradesBySeries(series.trim());

            if (series.isEmpty() || series == null) {
                Response res = new Response("error", "count can't be invalid ,null or zero", "Zero CountInput", null);
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            if (highest == null || highest.isEmpty() || lowest == null || lowest.isEmpty()) {
                Response res = new Response("success", "No Records Found", "", "");
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Map<String, String> list = new HashMap<>();
            list.put("Highest", highest);
            list.put("Lowest", lowest);
            Response res = new Response("success",
                    "Records with Minimum Trades and Maximum Trades fetched SuccessFully", null, list);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Response res = new Response("error", "Error occured While Fetching Data", e.getMessage(), null);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
