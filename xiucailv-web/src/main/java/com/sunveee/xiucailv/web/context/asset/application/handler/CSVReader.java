package com.sunveee.xiucailv.web.context.asset.application.handler;

import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetItem;
import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetSnapshot;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<AssetItem> readAssetItemFromCsv(InputStream inputStream) {
        List<AssetItem> result = new ArrayList<>();
        try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(inputStream));) {
            String headerLine = csvReader.readLine();
            String[] csvHeader = headerLine.split(",");
            try (CSVParser csvParser = CSVFormat.Builder.create()
                    .setHeader(csvHeader).setSkipHeaderRecord(false).setAllowDuplicateHeaderNames(false).setIgnoreEmptyLines(true)
                    .build().parse(csvReader);) {
                for (CSVRecord csvRecord : csvParser) {
                    AssetItem assetItem = AssetItem.fromCsvRecord(csvRecord);
                    result.add(assetItem);
                }
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static List<AssetSnapshot> readAssetSnapshotFromCsv(InputStream inputStream, String date) {
        List<AssetSnapshot> result = new ArrayList<>();
        try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(inputStream));) {
            String headerLine = csvReader.readLine();
            String[] csvHeader = headerLine.split(",");
            try (CSVParser csvParser = CSVFormat.Builder.create()
                    .setHeader(csvHeader).setSkipHeaderRecord(false).setAllowDuplicateHeaderNames(false).setIgnoreEmptyLines(true)
                    .build().parse(csvReader);) {
                for (CSVRecord csvRecord : csvParser) {
                    AssetSnapshot assetSnapshot = AssetSnapshot.fromCsvRecord(csvRecord, date);
                    result.add(assetSnapshot);
                }
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
